package com.example.demo.oauth2.resourceserver.api;

import lombok.Data;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @CrossOrigin
    @GetMapping("/api/userinfo")
    public UserInfo getUserInfo() {
        //User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Yann");
        userInfo.setAge(28);
        return userInfo;
    }

    @Data
    class UserInfo {
        private String name;
        private int age;
    }
}
