package com.example.demo.mode1.github.connect;

import com.example.demo.mode1.github.api.GitHub;
import com.example.demo.mode1.github.api.GitHubImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

public class GitHubServiceProvider extends AbstractOAuth2ServiceProvider<GitHub> {

    public GitHubServiceProvider(String clientId, String clientSecret) {
        super(createOAuth2Template(clientId, clientSecret));
    }

    private static OAuth2Template createOAuth2Template(String clientId, String clientSecret) {
        OAuth2Template oAuth2Template = new OAuth2Template(
                clientId, clientSecret,
                "https://github.com/login/oauth/authorize",
                "https://github.com/login/oauth/access_token");
        oAuth2Template.setUseParametersForClientAuthentication(true);
        return oAuth2Template;
    }


    @Override
    public GitHub getApi(String accessToken) {
        return new GitHubImpl(accessToken);
    }
}
