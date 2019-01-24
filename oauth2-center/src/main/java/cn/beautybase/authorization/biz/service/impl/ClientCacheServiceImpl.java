package cn.beautybase.authorization.biz.service.impl;

import cn.beautybase.authorization.biz.dao.ClientDao;
import cn.beautybase.authorization.biz.dto.ClientInfoDTO;
import cn.beautybase.authorization.biz.entity.Client;
import cn.beautybase.authorization.biz.service.ClientCacheService;
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
