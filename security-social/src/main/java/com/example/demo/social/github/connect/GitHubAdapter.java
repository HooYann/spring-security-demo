package com.example.demo.social.github.connect;

import com.example.demo.social.github.api.GitHub;
import com.example.demo.social.github.api.GitHubUser;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.web.client.HttpClientErrorException;

public class GitHubAdapter implements ApiAdapter<GitHub> {
    @Override
    public boolean test(GitHub api) {
        try{
            api.getUser();
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }

    @Override
    public void setConnectionValues(GitHub api, ConnectionValues values) {
        GitHubUser user = api.getUser();
        values.setProviderUserId(String.valueOf(user.getId()));
        values.setDisplayName(user.getLogin());
        values.setProfileUrl("https://github.com/" + user.getLogin());
        values.setImageUrl(user.getAvatarUrl());
    }

    @Override
    public UserProfile fetchUserProfile(GitHub api) {
        GitHubUser user = api.getUser();
        return new UserProfileBuilder().setName(user.getName()).setEmail(user.getEmail()).setUsername(user.getLogin()).build();
    }

    @Override
    public void updateStatus(GitHub gitHub, String s) {
        //
    }
}
