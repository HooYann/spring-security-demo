package cn.beautybase.authorization.dto;

import cn.beautybase.authorization.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String sex;

    private String phoneNumber;

    private String email;

    private String avatar;

    public static UserInfoDTO initUser(User user) {
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.id = user.getId();
        userInfo.username = user.getUsername();
        userInfo.name = user.getName();
        userInfo.sex = user.getSex();
        userInfo.phoneNumber = user.getPhoneNumber();
        userInfo.email = user.getEmail();
        userInfo.avatar = user.getAvatar();
        return userInfo;
    }


}
