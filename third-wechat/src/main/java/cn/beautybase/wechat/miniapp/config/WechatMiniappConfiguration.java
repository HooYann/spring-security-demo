package cn.beautybase.wechat.miniapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yann
 * @date 2018-04-20
 */
@Configuration
@ConditionalOnProperty(name = "wechat.miniapp.appid")
public class WechatMiniappConfiguration {
    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${wechat.miniapp.appid}")
    private String appid;

    @Value("${wechat.miniapp.secret}")
    private String secret;

    @Bean
    public WechatMiniappAccessTokenService wechatMiniappAccessTokenService(){
        WechatMiniappAccessTokenServiceImpl service
                = new WechatMiniappAccessTokenServiceImpl();
        service.setProfile(profile);
        service.setAppid(appid);
        service.setSecret(secret);
        return service;
    }
}
