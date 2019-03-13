package cn.beautybase.authorization.core.oauth2.provider.token;

import cn.beautybase.authorization.biz.base.ServiceException;
import cn.beautybase.authorization.biz.user.constants.UserSex;
import cn.beautybase.authorization.biz.user.entity.User;
import cn.beautybase.authorization.biz.user.entity.UserSocial;
import cn.beautybase.authorization.core.security.authentication.social.SocialAuthenticationToken;
import cn.beautybase.authorization.core.security.userdetails.SocialUserDetailsService;
import cn.beautybase.authorization.third.wechat.api.WechatMiniappService;
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

@Data
public class WechatMiniappTokenGranter implements TokenGranter  {

    public final Log logger = LogFactory.getLog(this.getClass());

    private static final String GRANT_TYPE = "wechat_miniapp";
    private static final String PROVIDER_ID = GRANT_TYPE;

    private SocialUserDetailsService socialUserDetailsService;
    private AuthenticationManager authenticationManager;
    private AuthorizationServerTokenServices tokenServices;
    private ClientDetailsService clientDetailsService;
    private OAuth2RequestFactory requestFactory;

    //private final Random r = new Random();

    @Autowired
    @Lazy
    private WechatMiniappService wechatMiniappService;

    public WechatMiniappTokenGranter(SocialUserDetailsService socialUserDetailsService, AuthenticationManager authenticationManager) {
        this.socialUserDetailsService = socialUserDetailsService;
        this.authenticationManager = authenticationManager;
    }

    public OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        try{
            Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
            String jsCode = (String)parameters.get("js_code");
            String encryptedData = (String)parameters.get("encrypted_data");
            String iv = (String)parameters.get("iv");

            if(!StringUtils.hasText(jsCode)
                    || !StringUtils.hasText(encryptedData)
                    || !StringUtils.hasText(iv)) {
                throw new InvalidRequestException("Missing parameters, js_code=" + jsCode + ", encrypted_data=" + encryptedData + ", iv=" + iv);
            }

            JSONObject wxMaUserInfo = wechatMiniappService.getUserInfo(jsCode, encryptedData, iv);
            UserSocial userSocial = socialUserDetailsService.get(PROVIDER_ID, wxMaUserInfo.getString("openId"));
            if(userSocial == null) {
                User user = new User();
                //user.setUsername(allocateUsername());
                user.setNickname(wxMaUserInfo.getString("nickName"));
                user.setSex(getSex(wxMaUserInfo.getString("gender")));
                user.setAvatar(wxMaUserInfo.getString("avatarUrl"));
                userSocial = socialUserDetailsService.add(PROVIDER_ID, wxMaUserInfo.getString("openId"), user, true, wxMaUserInfo);
            }

            Authentication userAuth = new SocialAuthenticationToken(userSocial.getUser(), "N/A");
            Authentication authentication = this.authenticationManager.authenticate(userAuth);

            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, authentication);

        } catch(Exception e) {
            logger.error("getOAuth2Authentication", e);
            throw new ServiceException("登录失败");
        }
    }

    //private String allocateUsername() {
    //    return "tmp" + System.currentTimeMillis() + String.format("%03d", r.nextInt(999) + 1);
    //}

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
