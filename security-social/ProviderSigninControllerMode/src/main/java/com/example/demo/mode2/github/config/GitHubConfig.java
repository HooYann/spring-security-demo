package com.example.demo.mode2.github.config;

import com.example.demo.mode2.config.SocialAutoConfigurerAdapter;
import com.example.demo.mode2.github.connect.GitHubConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;

@Configuration
public class GitHubConfig extends SocialAutoConfigurerAdapter {
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new GitHubConnectionFactory("5a3d6eb9901ccd9bd90d", "876164bc653ebe2d327f797627efd83bde5d8f2d");
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(connectionFactoryLocator);
        return repository;
    }
}
