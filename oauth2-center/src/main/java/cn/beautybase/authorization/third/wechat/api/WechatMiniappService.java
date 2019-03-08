package cn.beautybase.authorization.third.wechat.api;

import com.alibaba.fastjson.JSONObject;

public interface WechatMiniappService {

    JSONObject getSessionInfo(String jsCode);

    JSONObject getUserInfo(String jsCode, String encryptedData, String iv);

    JSONObject getPhoneInfo(String jsCode, String encryptedData, String iv);
}
