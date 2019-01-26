package cn.beautybase.authorization.biz.common.service;

import cn.beautybase.authorization.biz.constants.CacheAttributes;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface SmsCodeCacheService {
    @CachePut(value = CacheAttributes.SMS_CODE, key = "#phoneNumber")
    String put(String phoneNumber, String code);

    @Cacheable(value = CacheAttributes.SMS_CODE, key = "#phoneNumber")
    String get(String phoneNumber);

    @CacheEvict(value = CacheAttributes.SMS_CODE, key = "#phoneNumber")
    void evict(String phoneNumber);
}
