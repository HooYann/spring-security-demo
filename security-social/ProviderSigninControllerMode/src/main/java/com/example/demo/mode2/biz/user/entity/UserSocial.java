package com.example.demo.mode2.biz.user.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "z_user_social")
public class UserSocial implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 社交提供者
     */
    @Column(name = "provider_id")
    private String providerId;
    /**
     * 社交用户ID
     */
    @Column(name = "provider_user_id")
    private String providerUserId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

}
