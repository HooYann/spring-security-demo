package com.example.demo.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class SpringSocialConfig extends SpringSocialConfigurer {
    private String filterProcessUrl;

    public SpringSocialConfig () {
        this.filterProcessUrl = "/auth";
    }

    public SpringSocialConfig (String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(filterProcessUrl);
        //filter.setSignupUrl("/register");
        return (T)filter;
    }

}
