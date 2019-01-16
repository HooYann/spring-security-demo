package com.example.demo.oauth2.authorizationserver.api;

import com.example.demo.oauth2.authorizationserver.entity.UserInfoEntity;
import com.example.demo.oauth2.authorizationserver.service.UserInfoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @CrossOrigin
    @GetMapping("/api/userinfo")
    public UserInfoEntity getUserInfo(String username) {
        //User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfoEntity userInfo = userInfoService.findByUsername(username);
        return userInfo;
    }

}
