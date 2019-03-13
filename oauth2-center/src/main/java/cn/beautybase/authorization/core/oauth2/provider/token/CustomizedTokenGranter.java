package cn.beautybase.authorization.core.oauth2.provider.token;

import lombok.Data;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;

@Data
public class CustomizedTokenGranter implements TokenGranter {

    private CompositeTokenGranter delegate;

    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
        //if(delegate == null) {
        //    throw new ServiceException("请实例化后，设置delegate属性");
        //}
        return this.delegate.grant(grantType, tokenRequest);
    }

}
