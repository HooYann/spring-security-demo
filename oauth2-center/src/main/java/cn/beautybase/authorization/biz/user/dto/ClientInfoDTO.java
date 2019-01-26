package cn.beautybase.authorization.biz.user.dto;

import cn.beautybase.authorization.biz.user.entity.Client;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
public class ClientInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String clientId;

    private String clientSecret;

    private Boolean secretRequired;

    private String grantTypes;

    private Boolean scoped;

    private String scope;

    private String resourceIds;

    private String redirectUri;

    private Boolean autoApprove;

    private Integer validitySeconds;

    private Integer refreshValiditySeconds;

    private String authorities;

    private String additionalInformation;


    public ClientInfoDTO init(Client client) {
        BeanUtils.copyProperties(client, this);
        return this;
    }

    public Client extractClient() {
        Client client = new Client();
        BeanUtils.copyProperties(this, client);
        return client;
    }
}
