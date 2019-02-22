package cn.beautybase.authorization.biz.common.service.impl;

import cn.beautybase.authorization.biz.common.service.SmsCodeCacheService;
import cn.beautybase.authorization.biz.common.service.SmsCodeLogService;
import cn.beautybase.authorization.biz.common.service.SmsCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

@Service
@Slf4j
public class SmsCodeServiceImpl implements SmsCodeService {

    @Autowired
    private SmsCodeCacheService smsCodeCacheService;

    @Autowired
    private SmsCodeLogService smsCodeLogService;

    @Override
    public boolean send(String phoneNumber) {
        //生成短信验证码的规则
        //记录日志
        //防止短信炸弹
        //放入缓存
        //发送
        //发送结果

        //先简单搞一下
        String code = createCode();
        log.info("SmsCodeServiceImpl.send, code={}", code);
        smsCodeCacheService.put(phoneNumber, code);
        return true;
    }

    private static String createCode() {
        return String.format("%06d", nextInt());
    }

    private static int nextInt() {
        Random random = new Random();
        return random.nextInt(999999) + 1;
    }

    public static void main(String[] args) {
        System.out.println(createCode());
    }


    @Override
    public boolean check(String phoneNumber, String aCode) {
        if(!StringUtils.hasText(aCode)) {
            log.error("SmsCodeServiceImpl.check, code is null");
            return false;
        }
        //对比
        String code = smsCodeCacheService.get(phoneNumber);
        if(aCode.equals(code)) {
            //清除缓存
            smsCodeCacheService.evict(phoneNumber);
            return true;
        }
        return false;
    }
}
