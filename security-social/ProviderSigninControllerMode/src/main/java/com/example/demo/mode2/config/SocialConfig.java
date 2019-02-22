package com.example.demo.mode2.config;

import com.example.demo.mode2.github.connect.GitHubConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        configurer.addConnectionFactory(new GitHubConnectionFactory("5a3d6eb9901ccd9bd90d", "876164bc653ebe2d327f797627efd83bde5d8f2d"));
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

    /*@Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository, SimpleSignInAdapter simpleSignInAdapter) {
        ((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp((Connection<?> connection) -> connection.getKey().getProviderUserId());
        ProviderSignInController controller = new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, simpleSignInAdapter);
        //controller.setApplicationUrl("http://wx.ng.beautybase.cn");
        return controller;
    }*/

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        return repository;
    }

}
