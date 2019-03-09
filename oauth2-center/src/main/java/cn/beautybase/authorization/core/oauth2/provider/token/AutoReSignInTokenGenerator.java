package cn.beautybase.authorization.core.oauth2.provider.token;

import cn.beautybase.authorization.biz.base.ServiceException;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.*;

/**
 * 重新登录token产生器
 */
public class AutoReSignInTokenGenerator {

    public final Log logger = LogFactory.getLog(this.getClass());

    @Setter
    private AuthorizationServerTokenServices tokenServices;

    public OAuth2AccessToken getAccessToken(ClientDetails client, String scopes, String grantType, Authentication authentication) {
        OAuth2Authentication oAuth2Authentication = this.getOAuth2Authentication(client, scopes, grantType, authentication);
        return this.tokenServices.createAccessToken(oAuth2Authentication);
    }

    public OAuth2Authentication getOAuth2Authentication(ClientDetails client, String scopes, String grantType, Authentication authentication) {
        try{
            OAuth2Request storedOAuth2Request = this.createOAuth2Request(client, scopes, grantType);
            return new OAuth2Authentication(storedOAuth2Request, authentication);
        } catch(Exception e) {
            logger.error("getOAuth2Authentication", e);
            throw new ServiceException("重新登录失败");
        }
    }

    public OAuth2Request createOAuth2Request(ClientDetails client, String scopes, String grantType) {
        Map<String, String> requestParameters = new HashMap<>();
        HashMap<String, String> modifiable = new HashMap(requestParameters);
        //modifiable.remove("password");
        //modifiable.remove("client_secret");
        modifiable.put("grant_type", grantType);
        return new OAuth2Request(modifiable, client.getClientId(), client.getAuthorities(), true, this.parseParameterList(scopes), client.getResourceIds(), (String)null, (Set)null, (Map)null);
    }

    public static Set<String> parseParameterList(String values) {
        Set<String> result = new TreeSet();
        if (values != null && values.trim().length() > 0) {
            String[] tokens = values.split("[\\s+]");
            result.addAll(Arrays.asList(tokens));
        }

        return result;
    }
}
