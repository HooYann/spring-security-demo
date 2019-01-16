package com.example.demo.oauth2.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.xml.ws.http.HTTPBinding;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception{

        /*http
                .authorizeRequests()
                    .antMatchers("/oauth/**", "/login/**", "/logout/**").permitAll()
                    .mvcMatchers("/.well-know/jwks.json").permitAll()
                    .anyRequest().authenticated();*/

        http.
                requestMatchers()
                    // /oauth/authorize link org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
                    // 必须登录过的用户才可以进行 oauth2 的授权码申请
                    .antMatchers("/", "/home", "/login", "/oauth/authorize")
                    .and()
                .authorizeRequests()
                    .anyRequest().authenticated()//permitAll()
                    .and()
                .formLogin()
                    //.loginPage("/login") //自己设计了页面
                    .and()
                //.anonymous()//匿名登录
                //    .and()
                .httpBasic()
                    .disable()
                //.rememberMe().and()
                .logout()
                    //.deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/login?authorization_error=true")
                    .and()
                // TODO: put CSRF protection back into this endpoint
                .csrf()
                    .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                    .disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().mvcMatchers("/oauth/check_token");
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("Yann")
                //.password(new BCryptPasswordEncoder().encode("123456"))
                .password("123456")
                .authorities("employee").build());
        return manager;
        //return new UserDetailsServiceImpl();
    }
}
