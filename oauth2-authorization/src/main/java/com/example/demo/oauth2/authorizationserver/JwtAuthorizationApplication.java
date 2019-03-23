package com.example.demo.oauth2.authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;

@SpringBootApplication
public class JwtAuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthorizationApplication.class, args);
	}

	//demo

	AuthorizationEndpoint authorizationEndpoint;

	TokenEndpoint tokenEndpoint;

	ClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter;

	ClientDetailsUserDetailsService clientDetailsUserDetailsService;


}
