package cn.beautybase.authorization.core.oauth2.provider;

import cn.beautybase.authorization.core.security.authentication.smscode.SmsCodeAuthenticationToken;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 参考ResourceOwnerPasswordTokenGranter
 */
public class ResourceOwnerSmsCodeTokenGranter  extends AbstractTokenGranter {
    private static final String GRANT_TYPE = "sms_code";
    private final AuthenticationManager authenticationManager;

    public ResourceOwnerSmsCodeTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, "sms_code");
    }

    public ResourceOwnerSmsCodeTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    public OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
        String username = (String)parameters.get("username");
        String password = (String)parameters.get("password");
        parameters.remove("password");
        Authentication userAuth = new SmsCodeAuthenticationToken(username, password);
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
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }
    }

}
