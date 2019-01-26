package cn.beautybase.authorization.biz.common.controller;

import cn.beautybase.authorization.biz.base.BaseController;
import cn.beautybase.authorization.biz.base.Result;
import cn.beautybase.authorization.biz.common.service.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsCodeController extends BaseController {
    @Autowired
    private SmsCodeService smsCodeService;

    @GetMapping("/public/sms/send")
    public Result<Void> userInfo(String phoneNumber) {
        smsCodeService.send(phoneNumber);
        return this.succeed();
    }
}
