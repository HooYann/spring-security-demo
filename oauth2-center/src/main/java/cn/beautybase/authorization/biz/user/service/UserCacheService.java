package cn.beautybase.authorization.biz.user.service;

import cn.beautybase.authorization.biz.constants.CacheAttributes;
import cn.beautybase.authorization.biz.user.dto.UserInfoDTO;
import org.springframework.cache.annotation.Cacheable;

public interface UserCacheService {
    @Cacheable(value = CacheAttributes.USER, key = "#id" )
    UserInfoDTO getInfo(Long id);
}
