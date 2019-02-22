package cn.beautybase.authorization.core.oauth2.social;

import cn.beautybase.authorization.biz.base.BaseController;
import cn.beautybase.authorization.biz.base.ErrorCode;
import cn.beautybase.authorization.biz.base.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 前后端分离，采用jwt，并不像标准的spring social流程
 * 获取code操作交给前端
 */
@RestController
@RequestMapping({"/signin"})
public abstract class SocialSignInController extends BaseController {

    private static final Log logger = LogFactory.getLog(SocialSignInController.class);

    //社会

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.POST}
    )
    public Result<Object> oauth2Callback(@PathVariable String providerId, @RequestBody OAuth2CodeData codeData, NativeWebRequest request) {
        try {
            /*OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory)this.connectionFactoryLocator.getConnectionFactory(providerId);
            Connection<?> connection = this.connectSupport.completeConnection(connectionFactory, request);
            return this.handleSignIn(connection, connectionFactory, request);*/

            if("wechatMiniProgram".equals(providerId)) {

            }

            return this.succeed(null, null);
        } catch (Exception e) {
            logger.error("oauth2Callback: ", e);
            return this.fail(ErrorCode.INTERNAL_SERVER_ERROR.value(), "登录失败");
        }
    }

    /*private RedirectView handleSignIn(Connection<?> connection, ConnectionFactory<?> connectionFactory, NativeWebRequest request) {
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
    }*/

    private RedirectView redirect(String url) {
        return new RedirectView(url, true);
    }

    /*private void postSignIn(ConnectionFactory<?> connectionFactory, Connection<?> connection, WebRequest request) {
        Iterator var4 = this.interceptingSignInTo(connectionFactory).iterator();

        while(var4.hasNext()) {
            ProviderSignInInterceptor interceptor = (ProviderSignInInterceptor)var4.next();
            interceptor.postSignIn(connection, request);
        }

    }*/


}
