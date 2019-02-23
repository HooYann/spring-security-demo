package cn.beautybase.authorization.core.oauth2.provider.token;

import cn.beautybase.authorization.biz.user.entity.User;
import cn.beautybase.authorization.core.security.SimpleUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 参考DefaultUserAuthenticationConverter
 */
public class CustomizedUserAuthenticationConverter implements UserAuthenticationConverter {
    private Collection<? extends GrantedAuthority> defaultAuthorities;

    public CustomizedUserAuthenticationConverter() {
    }

    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.arrayToCommaDelimitedString(defaultAuthorities));
    }

    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap();
        response.put("user_name", authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        //追加用户ID信息
        response.put("user_id", ((User)authentication.getPrincipal()).getId());

        return response;
    }

    public Authentication extractAuthentication(Map<String, ?> map) {
        SimpleUser currentUser = new SimpleUser();
        if(!map.containsKey("user_id")) {
            return null;
        }
        if(!map.containsKey("user_name")) {
            return null;
        }
        currentUser.setId(((Number)(map.get("user_id"))).longValue());
        currentUser.setUsername((String)map.get("user_name"));
        Collection<? extends GrantedAuthority> authorities = this.getAuthorities(map);
        return new UsernamePasswordAuthenticationToken(currentUser, "N/A", authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey("authorities")) {
            return this.defaultAuthorities;
        } else {
            Object authorities = map.get("authorities");
            if (authorities instanceof String) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList((String)authorities);
            } else if (authorities instanceof Collection) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection)authorities));
            } else {
                throw new IllegalArgumentException("Authorities must be either a String or a Collection");
            }
        }
    }
}
