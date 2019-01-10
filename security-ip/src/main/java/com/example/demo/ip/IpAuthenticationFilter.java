package com.example.demo.ip;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IpAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public IpAuthenticationFilter() {
        super(new AntPathRequestMatcher("/ipverify"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String ip = request.getRemoteHost();
        IpAuthenticationToken authRequest = new IpAuthenticationToken(ip);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
