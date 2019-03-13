package cn.beautybase.authorization.core.oauth2.config;

import cn.beautybase.authorization.core.oauth2.handler.CustomizedAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * security oauth2 资源服务器配置
 * 此项目虽然作为认证服务中心，同时也充当资源服务器（提供用户基本信息接口）
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "authorization";

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(HttpSecurity http) throws  Exception {
        http
                .authorizeRequests()
                    .antMatchers("/public/**", "/signin/**", "/signup/**").permitAll()
                    .antMatchers("/api/user/signup/social").hasAnyAuthority("social_user")
                    .antMatchers("/api/**").hasAuthority("user")
                    .anyRequest().authenticated()
                    .and()
                .exceptionHandling().accessDeniedHandler(new CustomizedAccessDeniedHandler());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resourceConfig) throws Exception {
        resourceConfig.resourceId(RESOURCE_ID).stateless(true);
        //resourceConfig.tokenExtractor();
        //resourceConfig.tokenStore(tokenStore());
        resourceConfig.tokenStore(tokenStore);
    }

    /*@Bean
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
    }*/


    /*@Bean
    public JwtDecoder jwtDecoder() {
        return new NimbusJwtDecoderJwkSupport("http://localhost:8087/.well-known/jwks.json");
    }*/

}
