package cn.beautybase.wechat.miniapp.config;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface WechatMiniappAccessTokenService {

    /**
     * 获取小程序access_token
     */
    @Cacheable(value = "wechatAccessTokenCache", key = "'miniapp_token'")
    String get();

    /**
     * 更新小程序access_token
     */
    @CachePut(value = "wechatAccessTokenCache", key = "'miniapp_token'")
    String update();

}
