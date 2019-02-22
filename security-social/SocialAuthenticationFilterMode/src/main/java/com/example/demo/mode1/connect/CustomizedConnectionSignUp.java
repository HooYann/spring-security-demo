package com.example.demo.mode1.connect;

import com.example.demo.mode1.biz.user.entity.User;
import com.example.demo.mode1.biz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import java.util.Random;


public class CustomizedConnectionSignUp implements ConnectionSignUp {

    @Autowired
    private UserService userService;

    @Override
    public String execute(Connection<?> connection) {
        //生成临时UserID
        User user = new User();
        user.setUsername(getUsername());
        user.setNickname(connection.getDisplayName());
        user.setAvatar(connection.getImageUrl());
        user = userService.add(user);
        return String.valueOf(user.getId());
    }

    private Random r = new Random();

    private String getUsername() {
        return "tmp" + System.currentTimeMillis() + String.format("%03d", r.nextInt(999) + 1);
    }


}
