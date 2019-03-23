package com.example.demo.base;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class KeyClass {

    SecurityContextHolder context;

    Authentication authentication;

    AuthenticationManager authenticationManager;

    ProviderManager providerManager;

    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

}
