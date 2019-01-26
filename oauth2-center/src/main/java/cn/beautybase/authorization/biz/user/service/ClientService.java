package cn.beautybase.authorization.biz.user.service;

import cn.beautybase.authorization.biz.user.entity.Client;

public interface ClientService {

    Client getByClientId(String clientId);
}
