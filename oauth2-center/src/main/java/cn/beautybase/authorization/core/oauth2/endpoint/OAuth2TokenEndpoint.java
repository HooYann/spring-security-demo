package cn.beautybase.authorization.core.oauth2.endpoint;

import cn.beautybase.authorization.biz.base.BaseController;
import cn.beautybase.authorization.biz.base.Result;
import cn.beautybase.authorization.biz.base.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class OAuth2TokenEndpoint extends BaseController {

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private TokenGranter tokenGranter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private OAuth2RequestValidator oAuth2RequestValidator = new DefaultOAuth2RequestValidator();

    private Set<HttpMethod> allowedRequestMethods = new HashSet<HttpMethod>(Arrays.asList(HttpMethod.POST));

    @PostMapping(value = "/signin")
    public Result<OAuth2AccessToken> postAccessToken(@RequestBody Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        String clientId = (String)parameters.get("client_id");
        String clientSecret = (String)parameters.get("client_secret");

        if (!StringUtils.hasText(clientId)) {
            throw new BadCredentialsException("No client credentials presented");
        }
        if (clientSecret == null) {
            clientSecret = "";
        }
        clientId = clientId.trim();

        ClientDetails client = clientDetailsService.loadClientByClientId(clientId);
        if(client == null) {
            throw new ServiceException("客户端未配置");
        }

        authenticateClient(client, clientSecret);

        TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(parameters, client);


        oAuth2RequestValidator.validateScope(tokenRequest, client);

        if (!StringUtils.hasText(tokenRequest.getGrantType())) {
            throw new InvalidRequestException("Missing grant type");
        }
        if (tokenRequest.getGrantType().equals("implicit")) {
            throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
        }

        if (isAuthCodeRequest(parameters)) {
            // The scope was requested or determined during the authorization step
            if (!tokenRequest.getScope().isEmpty()) {
                log.debug("Clearing scope of incoming token request");
                tokenRequest.setScope(Collections.<String> emptySet());
            }
        }

        if (isRefreshTokenRequest(parameters)) {
            // A refresh token has its own default scopes, so we should ignore any added by the factory here.
            tokenRequest.setScope(OAuth2Utils.parseParameterList(parameters.get(OAuth2Utils.SCOPE)));
        }

        OAuth2AccessToken token = tokenGranter.grant(tokenRequest.getGrantType(), tokenRequest);
        if (token == null) {
            throw new UnsupportedGrantTypeException("Unsupported grant type: " + tokenRequest.getGrantType());
        }

        return this.succeed(token);

    }

    private void authenticateClient(ClientDetails client, String clientSecret) {
        if (!this.passwordEncoder.matches(clientSecret, client.getClientSecret())) {
            log.error("authenticateClient", "Authentication failed: password does not match stored value");
            throw new ServiceException("客户端密码错误");
        }
    }

    private OAuth2RequestFactory getOAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    /*@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<OAuth2Exception> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        }
        return getExceptionTranslator().translate(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
        if (logger.isWarnEnabled()) {
            logger.warn("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        }
        return getExceptionTranslator().translate(e);
    }

    @ExceptionHandler(ClientRegistrationException.class)
    public ResponseEntity<OAuth2Exception> handleClientRegistrationException(Exception e) throws Exception {
        if (logger.isWarnEnabled()) {
            logger.warn("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        }
        return getExceptionTranslator().translate(new BadClientCredentialsException());
    }

    @ExceptionHandler(OAuth2Exception.class)
    public ResponseEntity<OAuth2Exception> handleException(OAuth2Exception e) throws Exception {
        if (logger.isWarnEnabled()) {
            logger.warn("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
        }
        return getExceptionTranslator().translate(e);
    }*/

    /*private ResponseEntity<OAuth2AccessToken> getResponse(OAuth2AccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<OAuth2AccessToken>(accessToken, headers, HttpStatus.OK);
    }

    public void setOAuth2RequestValidator(OAuth2RequestValidator oAuth2RequestValidator) {
        this.oAuth2RequestValidator = oAuth2RequestValidator;
    }

    public void setAllowedRequestMethods(Set<HttpMethod> allowedRequestMethods) {
        this.allowedRequestMethods = allowedRequestMethods;
    }*/

    private boolean isRefreshTokenRequest(Map<String, String> parameters) {
        return "refresh_token".equals(parameters.get("grant_type")) && parameters.get("refresh_token") != null;
    }

    private boolean isAuthCodeRequest(Map<String, String> parameters) {
        return "authorization_code".equals(parameters.get("grant_type")) && parameters.get("code") != null;
    }
}
