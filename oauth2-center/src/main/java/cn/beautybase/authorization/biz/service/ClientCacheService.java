package cn.beautybase.authorization.biz.service;

import cn.beautybase.authorization.biz.constants.CacheAttributes;
import cn.beautybase.authorization.biz.dto.ClientInfoDTO;
import org.springframework.cache.annotation.Cacheable;

public interface ClientCacheService {
    @Cacheable(value = CacheAttributes.OAUTH2_CLIENT, key = "#clientId" )
    ClientInfoDTO getInfo(String clientId);
}
