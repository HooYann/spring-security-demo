package cn.beautybase.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;

@SpringBootApplication
public class CenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CenterApplication.class);
    }

    private OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter;
}
