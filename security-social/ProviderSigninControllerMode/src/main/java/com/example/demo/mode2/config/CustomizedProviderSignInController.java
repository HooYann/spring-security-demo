package com.example.demo.mode2.config;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.GenericTypeResolver;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.*;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping({"/signin"})
public class CustomizedProviderSignInController implements InitializingBean {

    private static final Log logger = LogFactory.getLog(CustomizedProviderSignInController.class);

    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final UsersConnectionRepository usersConnectionRepository;
    private final MultiValueMap<Class<?>, ProviderSignInInterceptor<?>> signInInterceptors = new LinkedMultiValueMap();
    private final SignInAdapter signInAdapter;
    private String applicationUrl;
    private String signInUrl = "/signin";
    private String signUpUrl = "/signup";
    private String postSignInUrl = "/";
    private ConnectSupport connectSupport;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Inject
    public CustomizedProviderSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository, SignInAdapter signInAdapter) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.usersConnectionRepository = usersConnectionRepository;
        this.signInAdapter = signInAdapter;
    }

    public void setSignInInterceptors(List<ProviderSignInInterceptor<?>> interceptors) {
        Iterator var2 = interceptors.iterator();

        while(var2.hasNext()) {
            ProviderSignInInterceptor<?> interceptor = (ProviderSignInInterceptor)var2.next();
            this.addSignInInterceptor(interceptor);
        }

    }

    public void setSignInUrl(String signInUrl) {
        this.signInUrl = signInUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public void setPostSignInUrl(String postSignInUrl) {
        this.postSignInUrl = postSignInUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public void addSignInInterceptor(ProviderSignInInterceptor<?> interceptor) {
        Class<?> serviceApiType = GenericTypeResolver.resolveTypeArgument(interceptor.getClass(), ProviderSignInInterceptor.class);
        this.signInInterceptors.add(serviceApiType, interceptor);
    }

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.POST}
    )
    public RedirectView signIn(@PathVariable String providerId, NativeWebRequest request) {
        try {
            ConnectionFactory<?> connectionFactory = this.connectionFactoryLocator.getConnectionFactory(providerId);
            MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
            this.preSignIn(connectionFactory, parameters, request);
            return new RedirectView(this.connectSupport.buildOAuthUrl(connectionFactory, request, parameters));
        } catch (Exception var5) {
            logger.error("Exception while building authorization URL: ", var5);
            return this.redirect(URIBuilder.fromUri(this.signInUrl).queryParam("error", "provider").build().toString());
        }
    }

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.GET},
            params = {"oauth_token"}
    )
    public RedirectView oauth1Callback(@PathVariable String providerId, NativeWebRequest request) {
        try {
            OAuth1ConnectionFactory<?> connectionFactory = (OAuth1ConnectionFactory)this.connectionFactoryLocator.getConnectionFactory(providerId);
            Connection<?> connection = this.connectSupport.completeConnection(connectionFactory, request);
            return this.handleSignIn(connection, connectionFactory, request);
        } catch (Exception var5) {
            logger.error("Exception while completing OAuth 1.0(a) connection: ", var5);
            return this.redirect(URIBuilder.fromUri(this.signInUrl).queryParam("error", "provider").build().toString());
        }
    }

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.GET},
            params = {"code"}
    )
    public RedirectView oauth2Callback(@PathVariable String providerId, @RequestParam("code") String code, NativeWebRequest request) {
        try {
            OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory)this.connectionFactoryLocator.getConnectionFactory(providerId);
            Connection<?> connection = this.connectSupport.completeConnection(connectionFactory, request);
            return this.handleSignIn(connection, connectionFactory, request);
        } catch (Exception var6) {
            logger.error("Exception while completing OAuth 2 connection: ", var6);
            return this.redirect(URIBuilder.fromUri(this.signInUrl).queryParam("error", "provider").build().toString());
        }
    }

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.GET},
            params = {"error"}
    )
    public RedirectView oauth2ErrorCallback(@PathVariable String providerId, @RequestParam("error") String error, @RequestParam(value = "error_description",required = false) String errorDescription, @RequestParam(value = "error_uri",required = false) String errorUri, NativeWebRequest request) {
        logger.warn("Error during authorization: " + error);
        URIBuilder uriBuilder = URIBuilder.fromUri(this.signInUrl).queryParam("error", error);
        if (errorDescription != null) {
            uriBuilder.queryParam("error_description", errorDescription);
        }

        if (errorUri != null) {
            uriBuilder.queryParam("error_uri", errorUri);
        }

        return this.redirect(uriBuilder.build().toString());
    }

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.GET}
    )
    public RedirectView canceledAuthorizationCallback() {
        return this.redirect(this.signInUrl);
    }

    public void afterPropertiesSet() throws Exception {
        this.connectSupport = new ConnectSupport(this.sessionStrategy);
        this.connectSupport.setUseAuthenticateUrl(true);
        if (this.applicationUrl != null) {
            this.connectSupport.setApplicationUrl(this.applicationUrl);
        }

    }

    private RedirectView handleSignIn(Connection<?> connection, ConnectionFactory<?> connectionFactory, NativeWebRequest request) {
        List<String> userIds = this.usersConnectionRepository.findUserIdsWithConnection(connection);
        if (userIds.size() == 0) {
            ProviderSignInAttempt signInAttempt = new ProviderSignInAttempt(connection);
            this.sessionStrategy.setAttribute(request, ProviderSignInAttempt.SESSION_ATTRIBUTE, signInAttempt);
            return this.redirect(this.signUpUrl);
        } else if (userIds.size() == 1) {
            this.usersConnectionRepository.createConnectionRepository((String)userIds.get(0)).updateConnection(connection);
            String originalUrl = this.signInAdapter.signIn((String)userIds.get(0), connection, request);
            this.postSignIn(connectionFactory, connection, request);
            return originalUrl != null ? this.redirect(originalUrl) : this.redirect(this.postSignInUrl);
        } else {
            return this.redirect(URIBuilder.fromUri(this.signInUrl).queryParam("error", "multiple_users").build().toString());
        }
    }

    private RedirectView redirect(String url) {
        return new RedirectView(url, true);
    }

    private void preSignIn(ConnectionFactory<?> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {
        Iterator var4 = this.interceptingSignInTo(connectionFactory).iterator();

        while(var4.hasNext()) {
            ProviderSignInInterceptor interceptor = (ProviderSignInInterceptor)var4.next();
            interceptor.preSignIn(connectionFactory, parameters, request);
        }

    }

    private void postSignIn(ConnectionFactory<?> connectionFactory, Connection<?> connection, WebRequest request) {
        Iterator var4 = this.interceptingSignInTo(connectionFactory).iterator();

        while(var4.hasNext()) {
            ProviderSignInInterceptor interceptor = (ProviderSignInInterceptor)var4.next();
            interceptor.postSignIn(connection, request);
        }

    }

    private List<ProviderSignInInterceptor<?>> interceptingSignInTo(ConnectionFactory<?> connectionFactory) {
        Class<?> serviceType = GenericTypeResolver.resolveTypeArgument(connectionFactory.getClass(), ConnectionFactory.class);
        List<ProviderSignInInterceptor<?>> typedInterceptors = (List)this.signInInterceptors.get(serviceType);
        if (typedInterceptors == null) {
            typedInterceptors = Collections.emptyList();
        }

        return typedInterceptors;
    }
}
