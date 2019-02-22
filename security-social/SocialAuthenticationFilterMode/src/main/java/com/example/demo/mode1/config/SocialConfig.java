package com.example.demo.mode1.config;

import com.example.demo.mode1.biz.user.service.UserSocialService;
import com.example.demo.mode1.connect.CustomizedConnectionSignUp;
import com.example.demo.mode1.connect.CustomizedUsersConnectRepository;
import com.example.demo.mode1.github.connect.GitHubConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

@Configuration
@EnableSocial
@EnableConfigurationProperties(SocialProperties.class)
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private SocialProperties socialProperties;

    /**
     * 社交登录类
     */
    @Bean
    public SpringSocialConfigurer springSocialConfigurer() {
        return new SpringSocialConfig();
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer, Environment environment) {
        socialProperties.getProviders();
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

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        //return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        CustomizedUsersConnectRepository repository = new CustomizedUsersConnectRepository();
        repository.setConnectionFactoryLocator(connectionFactoryLocator);
        repository.setConnectionSignUp(connectionSignUp());
        return repository;
    }

    @Bean
    public ConnectionSignUp connectionSignUp() {
        return new CustomizedConnectionSignUp();
    }

}
