package cn.beautybase.authorization.core.security.config;

import cn.beautybase.authorization.biz.common.service.SmsCodeService;
import cn.beautybase.authorization.core.security.authentication.smscode.SmsCodeAuthenticationProvider;
import cn.beautybase.authorization.core.security.authentication.social.SocialAuthenticationProvider;
import cn.beautybase.authorization.core.security.userdetails.CustomizedSocialUserDetailsService;
import cn.beautybase.authorization.core.security.userdetails.CustomizedUserDetailsService;
import cn.beautybase.authorization.core.security.userdetails.SocialUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security基础
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SmsCodeService smsCodeService;

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .requestMatchers()
                    .anyRequest()
                    .and()
                .authorizeRequests()
                    .mvcMatchers("/.well-know/jwks.json", "/signin/**", "signup/**").permitAll()
                    .antMatchers("/oauth/**").permitAll()
                    .and()
                //.formLogin()
                //    .loginPage("/signin").permitAll()
                    //.loginProcessingUrl("/login/authenticate")//处理表单中自定义的登录URL
                //.and()
                .httpBasic()
                    .disable()
                .exceptionHandling()
                    .accessDeniedPage("/signin?authorization_error=true")
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

    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        //原来的 用户名密码
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService());
        daoProvider.setPasswordEncoder(passwordEncoder());
        builder.authenticationProvider(daoProvider);
        //添加短信认证提供者
        SmsCodeAuthenticationProvider smsCodeProvider = new SmsCodeAuthenticationProvider();
        smsCodeProvider.setUserDetailsService(userDetailsService());
        smsCodeProvider.setSmsCodeService(smsCodeService);
        builder.authenticationProvider(smsCodeProvider);
        //社会提供者
        SocialAuthenticationProvider socialAuthenticationProvider = new SocialAuthenticationProvider();
        builder.authenticationProvider(socialAuthenticationProvider);
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new CustomizedUserDetailsService();
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new CustomizedSocialUserDetailsService();
    }


}
