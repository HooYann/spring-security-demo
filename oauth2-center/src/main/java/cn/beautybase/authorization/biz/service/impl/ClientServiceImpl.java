package cn.beautybase.authorization.biz.service.impl;

import cn.beautybase.authorization.biz.dao.ClientDao;
import cn.beautybase.authorization.biz.dto.ClientInfoDTO;
import cn.beautybase.authorization.biz.entity.Client;
import cn.beautybase.authorization.biz.service.ClientCacheService;
import cn.beautybase.authorization.biz.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientDao clientDao;

    @Autowired
    private ClientCacheService clientCacheService;

    @Override
    public Client getByClientId(String clientId) {
        ClientInfoDTO clientInfo = clientCacheService.getInfo(clientId);
        return clientInfo.extractClient();
    }
}
