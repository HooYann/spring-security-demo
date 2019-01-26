package cn.beautybase.authorization.biz.common.service;

/**
 * 短信验证码服务类
 */
public interface SmsCodeService {
    //发送短信验证码
    boolean send(String phoneNumber);

    //对比code
    boolean check(String phoneNumber, String code);

}
