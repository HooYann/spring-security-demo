package cn.beautybase.authorization.core.security.authentication.social;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class SocialAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 510L;
    private final Object principal;
    private String credentials;

    public SocialAuthenticationToken(Object principal, String credentials) {
        this(principal, credentials, false);
    }

    public SocialAuthenticationToken(Object principal, String credentials, boolean authenticated) {
        super(authenticated ? AuthorityUtils.commaSeparatedStringToAuthorityList("user") : AuthorityUtils.NO_AUTHORITIES);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(authenticated);
    }

    /*public SocialAuthenticationToken(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }*/

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}
