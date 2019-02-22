package com.example.demo.mode2.biz.user.dto;

import com.example.demo.mode2.biz.user.entity.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String nickname;

    private String name;

    private String sex;

    private String phoneNumber;

    private String email;

    private String avatar;

    private String status;

    public UserInfoDTO init(User user) {
        //userInfo.id = user.getId();
        //username = user.getUsername();
        //userInfo.name = user.getName();
        //userInfo.sex = user.getSex();
        //userInfo.phoneNumber = user.getPhoneNumber();
        //userInfo.email = user.getEmail();
        //userInfo.avatar = user.getAvatar();
        //userInfo.status = user.getStatus();
        BeanUtils.copyProperties(user, this);
        return this;
    }


    public User extractUser() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
