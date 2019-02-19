package com.example.demo.mode1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class SpringSocialConfig extends SpringSocialConfigurer {

    private String filterProcessUrl;

    public SpringSocialConfig () {
        this.filterProcessUrl = "/signin";
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessUrl);
        filter.setSignupUrl("/signup");
        return (T)filter;
    }


}
