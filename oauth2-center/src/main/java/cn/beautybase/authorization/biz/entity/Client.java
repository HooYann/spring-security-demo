package cn.beautybase.authorization.biz.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "z_oauth2_client")
public class Client implements ClientDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 客户端标识
     */
    @Column(name = "client_id", length = 64)
    private String clientId;
    /**
     * 客户端密钥
     */
    @Column(name = "client_secret")
    private String clientSecret;
    /**
     * 是否需要密钥
     */
    @Column(name = "secret_required")
    private Boolean secretRequired = true;
    /**
     * 授权模式
     */
    @Column(name = "grant_types")
    private String grantTypes;
    /**
     * 是否有范围
     */
    @Column
    private Boolean scoped;
    /**
     * 范围
     */
    @Column
    private String scope;
    /**
     * 资源集
     */
    @Column(name = "resource_ids")
    private String resourceIds;
    /**
     * 重定向
     */
    @Column(name = "redirect_uri")
    private String redirectUri;
    /**
     * 自动授权
     */
    @Column(name = "auto_approve")
    private Boolean autoApprove = false;
    /**
     * token有效期
     */
    @Column(name = "validity_seconds")
    private Integer validitySeconds;
    /**
     * refresh_token有效期
     */
    @Column(name = "refresh_validity_seconds")
    private Integer refreshValiditySeconds;
    /**
     * 权限集
     */
    @Column(name = "authorities")
    private String authorities;
    /**
     * 追加信息
     */
    @Column(name = "additional_information")
    private String additionalInformation;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        if(StringUtils.hasText(this.resourceIds)) {
            return new HashSet<>(Arrays.asList(this.resourceIds.split(",")));
        }
        return new HashSet<>();
    }

    @Override
    public boolean isSecretRequired() {
        return this.secretRequired;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        if(StringUtils.hasText(this.scope)) {
            return new HashSet<>(Arrays.asList(this.scope.split(",")));
        }
        return new HashSet<>();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if(StringUtils.hasText(this.grantTypes)) {
            return new HashSet<>(Arrays.asList(this.grantTypes.split(",")));
        }
        return new HashSet<>();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        if(StringUtils.hasText(this.redirectUri)) {
            return new HashSet<>(Arrays.asList(this.redirectUri));
        }
        return new HashSet<>();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        if(StringUtils.hasText(this.authorities)) {
            String[] ss = this.authorities.split(",");
            for(String s : ss) {
                list.add(new SimpleGrantedAuthority(s));
            }
        }
        return list;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.validitySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.refreshValiditySeconds;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return this.autoApprove;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
