package cn.beautybase.authorization.core.oauth2.social.wechat;

import cn.beautybase.authorization.biz.base.BaseController;
import cn.beautybase.authorization.biz.base.ErrorCode;
import cn.beautybase.authorization.biz.base.Result;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.crypt.WxMaCryptUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 前后端分离，采用jwt，并不像标准的spring social流程
 * 获取code操作交给前端
 */
@ConditionalOnProperty(name = "wechat.miniapp.appid")
@RestController
@RequestMapping
public class WechatMiniAppSignInController extends BaseController {

    @Autowired
    private WxMaService wxMaService;

    @RequestMapping(
            value = {"/signin/wechatminiapp"},
            method = {RequestMethod.POST}
    )
    public Result<Object> oauth2Callback(@RequestBody WechatMiniAppCodeData codeData) {
        try {
            /*OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory)this.connectionFactoryLocator.getConnectionFactory(providerId);
            Connection<?> connection = this.connectSupport.completeConnection(connectionFactory, request);
            return this.handleSignIn(connection, connectionFactory, request);*/

            if(!StringUtils.hasText(codeData.getCode())
                    || !StringUtils.hasText(codeData.getEncryptedData())
                    || !StringUtils.hasText(codeData.getIv())) {
                this.fail(ErrorCode.INTERNAL_SERVER_ERROR.value(), "登录失败，缺少参数");
            }


            WxMaJscode2SessionResult session = this.wxMaService.getUserService().getSessionInfo(codeData.getCode());


            String userInfoResult = WxMaCryptUtils.decrypt(
                    session.getSessionKey(),
                    codeData.getEncryptedData(),
                    codeData.getIv());
            //logger.info("onApplicationEvent,userInfoResult=" + userInfoResult);
            WxMaUserInfo wxMaUserInfo = WxMaUserInfo.fromJson(userInfoResult);


            return this.succeed(null, null);
        } catch (Exception e) {
            log.info("wechatMiniApp,oauth2Callback,code={},encrypt={},iv={}",
                    codeData.getCode(),
                    codeData.getEncryptedData(),
                    codeData.getIv());
            log.error("wechatMiniApp,oauth2Callback: ", e);
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
