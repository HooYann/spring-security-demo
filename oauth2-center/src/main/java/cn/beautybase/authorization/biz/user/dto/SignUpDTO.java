package cn.beautybase.authorization.biz.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignUpDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;

    private String username;

    private String password;

    private String phoneNumber;

    private String email;

    private String captcha;

    private String sex;

    private String avatar;



}
