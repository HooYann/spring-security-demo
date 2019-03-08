package cn.beautybase.authorization.third.wechat.api.adapter;

import cn.beautybase.authorization.biz.base.ServiceException;
import cn.beautybase.authorization.third.wechat.api.WechatMiniappService;
import cn.beautybase.authorization.third.wechat.utils.PKCS7Encoder;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;

@Service
@Slf4j
public class WechatMiniappServiceImpl implements WechatMiniappService {

    @Value("$wechat.miniapp.appid}")
    private String appid;
    @Value("$wechat.miniapp.secret}")
    private String secret;

    @Override
    public JSONObject getSessionInfo(String jsCode) {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_cde";
        url = String.format(url, appid, secret, jsCode);

        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> result = template.getForEntity(url, String.class);
        //打印关键位置日志
        log.info("getSessionInfo: " + result.getBody());
        JSONObject sessionInfo = JSONObject.parseObject(result.getBody());

        if(!StringUtils.hasText(sessionInfo.getString("session_key"))) {
            throw new ServiceException("jsCode微信小程序登录失败");
        }

        return sessionInfo;
    }

    @Override
    public JSONObject getUserInfo(String jsCode, String encryptedData, String iv) {
        JSONObject userInfo = this.decrypt2Info(jsCode, encryptedData, iv);
        if(!StringUtils.hasText(userInfo.getString("openid"))) {
            throw new ServiceException("获取微信小程序用户信息失败");
        }
        return userInfo;
    }

    @Override
    public JSONObject getPhoneInfo(String jsCode, String encryptedData, String iv) {
        JSONObject phoneInfo = this.decrypt2Info(jsCode, encryptedData, iv);
        if(!StringUtils.hasText(phoneInfo.getString("purePhoneNumber"))) {
            throw new ServiceException(41003, "获取微信绑定的手机号失败");
        }
        return phoneInfo;
    }

    private JSONObject decrypt2Info(String jsCode, String encryptedData, String iv) {
        JSONObject sessionInfo = this.getSessionInfo(jsCode);
        String result = this.decrypt(sessionInfo.getString("session_key"), encryptedData, iv);
        //打印关键位置日志
        log.info("decrypt2Info: " + result);
        return JSONObject.parseObject(result);
    }

    public String decrypt(String sessionKey, String encryptedData, String iv) {
        try {
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(Base64.decodeBase64(iv)));
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(2, new SecretKeySpec(Base64.decodeBase64(sessionKey), "AES"), params);
            return new String(PKCS7Encoder.decode(cipher.doFinal(Base64.decodeBase64(encryptedData))), StandardCharsets.UTF_8);
        } catch (Exception var5) {
            throw new RuntimeException("AES解密失败", var5);
        }
    }


}
