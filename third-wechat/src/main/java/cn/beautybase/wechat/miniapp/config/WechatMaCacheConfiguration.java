package cn.beautybase.wechat.miniapp.config;

import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxAccessToken;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 狗屎微信配置，早晚改掉
 */
@Slf4j
public class WechatMaCacheConfiguration extends WxMaInMemoryConfig {


    private Lock accessTokenLock = new ReentrantLock();


    private WechatMiniappAccessTokenService wechatMiniappAccessTokenService;


    public WechatMaCacheConfiguration(WechatMiniappAccessTokenService wechatMiniappAccessTokenService){
        this.wechatMiniappAccessTokenService = wechatMiniappAccessTokenService;
    }

    @Override
    public String getAccessToken() {
        log.debug("getAccessToken");
        String accessToken = wechatMiniappAccessTokenService.get();
        log.debug("getAccessToken=" + accessToken);
        return accessToken;
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }

    @Override
    public boolean isAccessTokenExpired() {
        log.debug("isAccessTokenExpired");
        return false;
    }

    @Override
    public void expireAccessToken() {
        this.wechatMiniappAccessTokenService.update();
        log.debug("expireAccessToken");
    }

    @Override
    public void updateAccessToken(WxAccessToken wxAccessToken) {
        log.debug("updateAccessToken");
        this.wechatMiniappAccessTokenService.update();
    }

    @Override
    public void updateAccessToken(String s, int i) {
        log.debug("updateAccessToken");
        this.wechatMiniappAccessTokenService.update();
    }


    @Override
    public long getExpiresTime() {
        log.debug("getExpiresTime");
        return this.expiresTime;
    }
}
