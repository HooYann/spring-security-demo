package cn.beautybase.authorization.core.oauth2.social.wechat;

import cn.beautybase.authorization.core.oauth2.social.OAuth2CodeData;
import lombok.Data;

@Data
public class WechatMiniAppCodeData extends OAuth2CodeData {

    private static final long serialVersionUID = 1L;

    private String encryptedData;
    private String iv;

}
