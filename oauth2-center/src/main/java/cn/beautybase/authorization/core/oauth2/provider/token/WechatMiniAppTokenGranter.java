package cn.beautybase.authorization.core.oauth2.provider.token;

import cn.beautybase.authorization.biz.base.ErrorCode;
import cn.beautybase.authorization.biz.user.service.UserService;
import cn.beautybase.authorization.biz.user.service.UserSocialService;
import cn.beautybase.authorization.core.security.authentication.smscode.SmsCodeAuthenticationToken;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.crypt.WxMaCryptUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class WechatMiniAppTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "wechat_miniapp";
    private final AuthenticationManager authenticationManager;
    private WxMaService wxMaService;
    private UserService userService;
    private UserSocialService userSocialService;

    public WechatMiniAppTokenGranter(WxMaService wxMaService, AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(wxMaService, authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    public WechatMiniAppTokenGranter(WxMaService wxMaService, AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.wxMaService = wxMaService;
        this.authenticationManager = authenticationManager;
    }

    public OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        String code = (String)parameters.get("code");
        String encryptedData = (String)parameters.get("encryptedData");
        String iv = (String)parameters.get("iv");

        if(!StringUtils.hasText(code)
                || !StringUtils.hasText(encryptedData)
                || !StringUtils.hasText(iv)) {
            throw new InvalidRequestException("Missing parameters, code=" + code + ", encryptData=" + encryptedData + ", iv=" + iv);
        }

        try{
            WxMaJscode2SessionResult session = this.wxMaService.getUserService().getSessionInfo(code);
            if(session != null) {
                WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(session.getSessionKey(), encryptedData, iv);
                wxMaUserInfo.getOpenId();
                //user_social
            }
        } catch(Exception e) {
            logger.error("getOAuth2Authentication", e);
            throw new RuntimeException(e);
        }

        Authentication userAuth = new SmsCodeAuthenticationToken("", "");
        ((AbstractAuthenticationToken)userAuth).setDetails(parameters);

        Authentication authentication;
        try {
            authentication = this.authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException var8) {
            throw new InvalidGrantException(var8.getMessage());
        } catch (BadCredentialsException var9) {
            throw new InvalidGrantException(var9.getMessage());
        }

        if (authentication != null && authentication.isAuthenticated()) {
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, authentication);
        } else {
            throw new InvalidGrantException("Could not authenticate user: " + "");
        }
    }
}
