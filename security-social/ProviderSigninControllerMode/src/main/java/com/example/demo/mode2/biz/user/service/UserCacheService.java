package com.example.demo.mode2.biz.user.service;

import com.example.demo.mode2.biz.constants.CacheAttributes;
import com.example.demo.mode2.biz.user.dto.UserInfoDTO;
import org.springframework.cache.annotation.Cacheable;

public interface UserCacheService {
    @Cacheable(value = CacheAttributes.USER, key = "#id" )
    UserInfoDTO getInfo(Long id);
}
