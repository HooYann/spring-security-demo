package com.example.demo.social.github.api;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;

public class GitHubImpl extends AbstractOAuth2ApiBinding implements GitHub {
    // GitHub API v3
    private static final String API_URL_BASE = "https://api.github.com/";

    public GitHubImpl(String accessToken) {
        super(accessToken);
    }

    @Override
    public GitHubUserProfile getUserProfile() {
        return getRestTemplate().getForObject(buildUri("user"), GitHubUserProfile.class);
    }

    public String buildUri(String path) {
        return API_URL_BASE + path;
    }

}
