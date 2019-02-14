package com.example.demo.social;

import com.example.demo.social.github.signin.SimpleSignInAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
    /**
     * 社交登录类
     */
    @Bean
    public SpringSocialConfigurer springSocialConfigurer() {
        return new SpringSocialConfig();
    }

    /**
     * 处理注册流程工具类
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator factoryLocator) {
        return new ProviderSignInUtils(factoryLocator, getUsersConnectionRepository(factoryLocator));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository, SimpleSignInAdapter simpleSignInAdapter) {
        ((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp((Connection<?> connection) -> connection.getKey().getProviderUserId());
        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, simpleSignInAdapter);
    }
}
