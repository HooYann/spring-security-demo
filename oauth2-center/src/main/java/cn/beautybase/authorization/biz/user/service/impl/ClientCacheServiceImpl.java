package cn.beautybase.authorization.biz.user.service.impl;

import cn.beautybase.authorization.biz.user.dao.ClientDao;
import cn.beautybase.authorization.biz.user.dto.ClientInfoDTO;
import cn.beautybase.authorization.biz.user.entity.Client;
import cn.beautybase.authorization.biz.user.service.ClientCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientCacheServiceImpl implements ClientCacheService {
    @Autowired
    private ClientDao clientDao;

    @Override
    public ClientInfoDTO getInfo(String clientId) {
        Client client = clientDao.getByClientId(clientId);
        return new ClientInfoDTO().init(client);
    }
}
