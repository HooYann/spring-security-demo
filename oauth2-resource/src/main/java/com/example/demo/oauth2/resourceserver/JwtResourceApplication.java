package com.example.demo.oauth2.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;

@SpringBootApplication
public class JwtResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtResourceApplication.class, args);
	}

	OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter;

			OAuth2AuthenticationManager oAuth2AuthenticationManager;


}
