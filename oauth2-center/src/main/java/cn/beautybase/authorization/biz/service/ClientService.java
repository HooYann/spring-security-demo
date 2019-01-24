package cn.beautybase.authorization.biz.service;

import cn.beautybase.authorization.biz.constants.CacheAttributes;
import cn.beautybase.authorization.biz.entity.Client;
import org.springframework.cache.annotation.Cacheable;

public interface ClientService {

    Client getByClientId(String clientId);
}
