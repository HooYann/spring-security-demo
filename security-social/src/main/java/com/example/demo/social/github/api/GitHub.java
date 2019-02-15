package com.example.demo.social.github.api;

import org.springframework.social.ApiBinding;

public interface GitHub extends ApiBinding {

    GitHubUser getUser();

}
