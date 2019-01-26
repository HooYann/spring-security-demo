package cn.beautybase.authorization.biz.common.service.impl;

import cn.beautybase.authorization.biz.common.service.SmsCodeCacheService;
import org.springframework.stereotype.Service;

@Service
public class SmsCodeCacheServiceImpl implements SmsCodeCacheService {
    @Override
    public String put(String phoneNumber, String code) {
        return code;
    }

    @Override
    public String get(String phoneNumber) {
        return null;
    }

    @Override
    public void evict(String phoneNumber) {
    }
}
