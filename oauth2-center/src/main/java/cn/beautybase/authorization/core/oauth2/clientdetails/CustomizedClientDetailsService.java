package cn.beautybase.authorization.core.oauth2.clientdetails;

import cn.beautybase.authorization.biz.user.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

public class CustomizedClientDetailsService implements ClientDetailsService {

    @Autowired
    private ClientService clientService;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientService.getByClientId(clientId);
    }
}
