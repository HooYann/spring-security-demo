package cn.beautybase.authorization.core.security.authentication.social;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class SocialAuthenticationProvider implements AuthenticationProvider {

    public boolean supports(Class<? extends Object> authentication) {
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new SocialAuthenticationToken(userDetails, "N/A", userDetails.getAuthorities());
    }


}
