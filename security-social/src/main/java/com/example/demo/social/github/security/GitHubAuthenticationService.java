package com.example.demo.social.github.security;

import com.example.demo.social.github.api.GitHub;
import com.example.demo.social.github.connect.GitHubConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.security.provider.OAuth2AuthenticationService;

public class GitHubAuthenticationService extends OAuth2AuthenticationService<GitHub> {

    public GitHubAuthenticationService(String apiKey, String apiSecret) {
        super(new GitHubConnectionFactory(apiKey, apiSecret));
    }

}
