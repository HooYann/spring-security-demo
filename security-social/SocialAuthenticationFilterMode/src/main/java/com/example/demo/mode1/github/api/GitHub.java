package com.example.demo.mode1.github.api;

import org.springframework.social.ApiBinding;

public interface GitHub extends ApiBinding {

    GitHubUser getUser();

}
