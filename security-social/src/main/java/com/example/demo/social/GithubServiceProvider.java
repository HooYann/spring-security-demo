package com.example.demo.social;

import org.springframework.social.ServiceProvider;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

public class GithubServiceProvider extends AbstractOAuth2ServiceProvider<GitHub> {

    public GithubServiceProvider(String clientId, String clientSecret) {
        super(createOAuth2Template(clientId, clientSecret));
    }

    private static OAuth2Template createOAuth2Template(String clientId, String clientSecret) {
        OAuth2Template oAuth2Template = new OAuth2Template(clientId, clientSecret, "https://github.com/login/oauth/authorize", "https://github.com/login/oauth/access_token");
        oAuth2Template.setUseParametersForClientAuthentication(true);
        return oAuth2Template;
    }


    @Override
    public GitHub getApi(String accessToken) {
        return new GithubTemplate(accessToken);
    }
}
