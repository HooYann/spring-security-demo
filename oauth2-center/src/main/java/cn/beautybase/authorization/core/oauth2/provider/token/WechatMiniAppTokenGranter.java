package cn.beautybase.authorization.core.oauth2.provider.token;

import cn.beautybase.authorization.biz.base.ErrorCode;
import cn.beautybase.authorization.biz.base.ServiceException;
import cn.beautybase.authorization.biz.user.constants.UserSex;
import cn.beautybase.authorization.biz.user.entity.User;
import cn.beautybase.authorization.biz.user.entity.UserSocial;
import cn.beautybase.authorization.biz.user.service.UserService;
import cn.beautybase.authorization.biz.user.service.UserSocialService;
import cn.beautybase.authorization.core.security.authentication.smscode.SmsCodeAuthenticationToken;
import cn.beautybase.authorization.core.security.authentication.social.SocialAuthenticationToken;
import cn.beautybase.authorization.core.security.userdetails.CustomizedSocialUserDetailsService;
import cn.beautybase.authorization.core.security.userdetails.SocialUserDetailsService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.crypt.WxMaCryptUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Data
public class WechatMiniAppTokenGranter implements TokenGranter  {

    public final Log logger = LogFactory.getLog(this.getClass());

    private static final String GRANT_TYPE = "wechat_miniapp";
    private static final String PROVIDER_ID = GRANT_TYPE;

    private SocialUserDetailsService socialUserDetailsService;
    private AuthenticationManager authenticationManager;
    private AuthorizationServerTokenServices tokenServices;
    private ClientDetailsService clientDetailsService;
    private OAuth2RequestFactory requestFactory;

    private final Random r = new Random();

    @Autowired
    @Lazy
    private WxMaService wxMaService;

    public WechatMiniAppTokenGranter(SocialUserDetailsService socialUserDetailsService, AuthenticationManager authenticationManager) {
        this.socialUserDetailsService = socialUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        try{

            Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
            String code = (String)parameters.get("code");
            String encryptedData = (String)parameters.get("encryptedData");
            String iv = (String)parameters.get("iv");

            if(!StringUtils.hasText(code)
                    || !StringUtils.hasText(encryptedData)
                    || !StringUtils.hasText(iv)) {
                throw new InvalidRequestException("Missing parameters, code=" + code + ", encryptData=" + encryptedData + ", iv=" + iv);
            }

            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);

            if(session == null) {
                throw new ServiceException("微信授权失败");
            }

            WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(session.getSessionKey(), encryptedData, iv);
            UserSocial userSocial = socialUserDetailsService.get(PROVIDER_ID, wxMaUserInfo.getOpenId());
            if(userSocial == null) {
                User user = new User();
                user.setUsername(allocateUsername());
                user.setNickname(wxMaUserInfo.getNickName());
                user.setSex(getSex(wxMaUserInfo.getGender()));

                JSONObject socialData = new JSONObject();
                socialData.put("unionid", wxMaUserInfo.getUnionId());
                socialData.put("country", wxMaUserInfo.getCountry());
                socialData.put("province", wxMaUserInfo.getProvince());
                socialData.put("city", wxMaUserInfo.getCity());
                socialData.put("language", wxMaUserInfo.getLanguage());
                socialData.put("watermark", wxMaUserInfo.getWatermark());

                userSocial = socialUserDetailsService.add(PROVIDER_ID, wxMaUserInfo.getOpenId(), user, true, socialData);
            }

            Authentication userAuth = new SocialAuthenticationToken(userSocial.getUser(), "");
            Authentication authentication = this.authenticationManager.authenticate(userAuth);

            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, authentication);

        } catch(Exception e) {
            logger.error("getOAuth2Authentication", e);
            throw new ServiceException("登录失败");
        }
    }

    private String allocateUsername() {
        return "tmp" + System.currentTimeMillis() + String.format("%03d", r.nextInt(999) + 1);
    }

    private String getSex(String gender) {
        String sex = UserSex.UNKNOWN;
        if (gender != null) {
            if ("1".equals(gender)) {
                sex = UserSex.MALE;
            } else if ("2".equals(gender)) {
                sex = UserSex.FEMALE;
            }
        }
        return sex;
    }

    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
        if (!GRANT_TYPE.equals(grantType)) {
            return null;
        } else {
            String clientId = tokenRequest.getClientId();
            ClientDetails client = this.clientDetailsService.loadClientByClientId(clientId);
            this.validateGrantType(grantType, client);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Getting access token for: " + clientId);
            }

            return this.getAccessToken(client, tokenRequest);
        }
    }

    protected OAuth2AccessToken getAccessToken(ClientDetails client, TokenRequest tokenRequest) {
        return this.tokenServices.createAccessToken(this.getOAuth2Authentication(client, tokenRequest));
    }


    protected void validateGrantType(String grantType, ClientDetails clientDetails) {
        Collection<String> authorizedGrantTypes = clientDetails.getAuthorizedGrantTypes();
        if (authorizedGrantTypes != null && !authorizedGrantTypes.isEmpty() && !authorizedGrantTypes.contains(grantType)) {
            throw new InvalidClientException("Unauthorized grant type: " + grantType);
        }
    }

    protected AuthorizationServerTokenServices getTokenServices() {
        return this.tokenServices;
    }

    protected OAuth2RequestFactory getRequestFactory() {
        return this.requestFactory;
    }
}
