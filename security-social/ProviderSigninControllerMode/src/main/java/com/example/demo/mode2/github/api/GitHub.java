package com.example.demo.mode2.github.api;

import org.springframework.social.ApiBinding;

public interface GitHub extends ApiBinding {

    GitHubUser getUser();

}
