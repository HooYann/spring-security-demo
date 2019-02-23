package cn.beautybase.wechat.miniapp.config;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.omg.SendingContext.RunTime;
import org.springframework.http.HttpEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Data
@Slf4j
public class WechatMiniappAccessTokenServiceImpl
        implements WechatMiniappAccessTokenService {

    private String profile;
    private String appid;
    private String secret;

    @Override
    public String get() {
        return fetch();
    }

    @Override
    public String update() {
        return fetch();
    }

    private String fetch() {
        //生产环境去微信拿access_token
        String url = "https://api.weixin.qq.com/cgi-bin/token"
                + "?grant_type=client_credential"
                + "&appid=" + appid
                + "&secret=" + secret;
        RestTemplate template = new RestTemplate();
        HttpEntity<String> result = template.getForEntity(url, String.class);
        //打印关键位置日志
        log.info("WechatMiniappAccessTokenService,fetchResult=" + result);
        JSONObject json = JSONObject.parseObject(result.getBody());
        String accessToken = json.getString("access_token");
        if (!StringUtils.hasText(accessToken)) {
            throw new RuntimeException("WechatMiniappAccessTokenService,failure,fetchResult=" + result);
        }
        return accessToken;
    }

}
