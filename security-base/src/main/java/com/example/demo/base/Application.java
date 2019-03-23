package com.example.demo.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);

    }

    SecurityContextHolder context;

    Authentication authentication;

    AuthenticationManager authenticationManager;

    ProviderManager providerManager;

    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
}
