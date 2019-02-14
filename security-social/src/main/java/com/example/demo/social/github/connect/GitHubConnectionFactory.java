package com.example.demo.social.github.connect;

import com.example.demo.social.github.api.GitHub;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

public class GitHubConnectionFactory extends OAuth2ConnectionFactory<GitHub> {

    public GitHubConnectionFactory(String clientId, String clientSecret) {
        super("github", new GitHubServiceProvider(clientId, clientSecret), new GitHubAdapter());
    }
}
