package cn.beautybase.authorization.biz.service;

import cn.beautybase.authorization.biz.constants.CacheAttributes;
import cn.beautybase.authorization.biz.dto.UserInfoDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

public interface UserCacheService {
    @Cacheable(value = CacheAttributes.USER, key = "#id" )
    UserInfoDTO getInfo(Long id);
}
