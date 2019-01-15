package com.example.demo.oauth2.resourceserver.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID_TEST = "resource_id_test";

    @Override
    public void configure(HttpSecurity http) throws  Exception {
        http
                .authorizeRequests()
                    .antMatchers("/api/**").hasAuthority("employee")
                    .anyRequest().authenticated()
                    .and();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resourceConfig) throws Exception {
        resourceConfig.resourceId(RESOURCE_ID_TEST).stateless(true);
        //resourceConfig.tokenExtractor();
        resourceConfig.tokenStore(tokenStore());
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //密钥
        //converter.setSigningKey("9527");
        //公钥
        //converter.setVerifier(new RsaVerifier("-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjvBtqsGCOmnYzwe/+HvgOqlKk6HPiLEzS6uCCcnVkFXrhnkPMZ+uQXTR0u+7ZklF0XC7+AMW8FQDOJS1T7IyJpCyeU4lS8RIf/Z8RX51gPGnQWkRvNw61RfiSuSA45LR5NrFTAAGoXUca/lZnbqnl0td+6hBDVeHYkkpAsSck1NPhlcsn+Pvc2Vleui/Iy1U2mzZCM1Vx6Dy7x9IeP/rTNtDhULDMFbB/JYs+Dg6Zd5Ounb3mP57tBGhLYN7zJkN1AAaBYkElsc4GUsGsUWKqgteQSXZorpf6HdSJsQMZBDd7xG8zDDJ28hGjJSgWBndRGSzQEYU09Xbtzk+8khPuwIDAQAB\n-----END PUBLIC KEY-----"));
        converter.setVerifierKey("-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjvBtqsGCOmnYzwe/+HvgOqlKk6HPiLEzS6uCCcnVkFXrhnkPMZ+uQXTR0u+7ZklF0XC7+AMW8FQDOJS1T7IyJpCyeU4lS8RIf/Z8RX51gPGnQWkRvNw61RfiSuSA45LR5NrFTAAGoXUca/lZnbqnl0td+6hBDVeHYkkpAsSck1NPhlcsn+Pvc2Vleui/Iy1U2mzZCM1Vx6Dy7x9IeP/rTNtDhULDMFbB/JYs+Dg6Zd5Ounb3mP57tBGhLYN7zJkN1AAaBYkElsc4GUsGsUWKqgteQSXZorpf6HdSJsQMZBDd7xG8zDDJ28hGjJSgWBndRGSzQEYU09Xbtzk+8khPuwIDAQAB\n-----END PUBLIC KEY-----");
        return converter;
    }


    /*@Bean
    public JwtDecoder jwtDecoder() {
        return new NimbusJwtDecoderJwkSupport("http://localhost:8087/.well-known/jwks.json");
    }*/
}
