package cn.beautybase.authorization.biz.dto;

import cn.beautybase.authorization.biz.entity.Client;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

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
