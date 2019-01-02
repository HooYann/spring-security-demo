package com.example.demo.oauth2.resourceserver.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws  Exception {
        http
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .antMatchers("/api/**").hasAuthority("SCOPE_store_manager");
    }
}
