package cn.beautybase.authorization.biz.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册 数据传输对象
 * @author Yann
 * @date 2018-03-05
 */
@Data
public class SignUpDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 注册类型：
     *  WECHAT_MINIAPP：小程序
     *  WECHAT_MINIAPP_PHONE_NUMBER：小程序绑定的手机号
     */
    private String type;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 注册渠道
     */
    private String channel;

    /**
     * 额外信息
     */
    private Map<String, Object> extData = new HashMap<>();


}
