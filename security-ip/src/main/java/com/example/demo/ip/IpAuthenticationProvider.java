package com.example.demo.ip;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IpAuthenticationProvider implements AuthenticationProvider {

    private final static Map<String, SimpleGrantedAuthority> authoritiesMap = new ConcurrentHashMap<>();
    static {
        authoritiesMap.put("127.0.0.1", new SimpleGrantedAuthority("ADMIN"));
        authoritiesMap.put("113.98.115.194", new SimpleGrantedAuthority("FRIEND"));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        IpAuthenticationToken ipAuthenticationToken = (IpAuthenticationToken)authentication;
        String ip = ipAuthenticationToken.getIp();
        SimpleGrantedAuthority authority = authoritiesMap.get(ip);
        if(authority == null) {
            return null;
        } else {
            return new IpAuthenticationToken(ip, Arrays.asList(authority));
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return IpAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
