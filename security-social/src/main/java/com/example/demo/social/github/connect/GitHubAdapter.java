package com.example.demo.social.github.connect;

import com.example.demo.social.github.api.GitHub;
import com.example.demo.social.github.api.GitHubUserProfile;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.web.client.HttpClientErrorException;

public class GitHubAdapter implements ApiAdapter<GitHub> {
    @Override
    public boolean test(GitHub api) {
        try{
            api.getUserProfile();
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    @Override
    public void setConnectionValues(GitHub api, ConnectionValues values) {
        GitHubUserProfile profile = api.getUserProfile();
        values.setProviderUserId(String.valueOf(profile.getId()));
        values.setDisplayName(profile.getLogin());
        values.setProfileUrl("https://github.com/" + profile.getLogin());
        values.setImageUrl(profile.getAvatarUrl());
    }

    @Override
    public UserProfile fetchUserProfile(GitHub api) {
        GitHubUserProfile profile = api.getUserProfile();
        return new UserProfileBuilder().setName(profile.getName()).setEmail(profile.getEmail()).setUsername(profile.getLogin()).build();
    }

    @Override
    public void updateStatus(GitHub gitHub, String s) {
        //
    }
}
