package com.example.demo.oauth2.authorizationserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.demo.oauth2.authorizationserver.dao")
public class JwtAuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthorizationApplication.class, args);
	}

}
