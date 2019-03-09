package cn.beautybase.authorization.biz.user.dto;

import cn.beautybase.authorization.biz.user.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class SignUpOutputDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    private User user;
}
