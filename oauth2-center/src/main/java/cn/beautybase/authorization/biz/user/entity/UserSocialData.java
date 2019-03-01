package cn.beautybase.authorization.biz.user.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "z_user_social_data")
public class UserSocialData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    /**
     * 社会用户一些额外的数据
     */
    @Column(name = "ext_data")
    private String extData;

}
