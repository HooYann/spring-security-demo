package com.example.demo.mode1.biz.user.controller;

import com.example.demo.mode1.biz.base.BaseController;
import com.example.demo.mode1.biz.base.Result;
import com.example.demo.mode1.biz.user.dto.UserInfoDTO;
import com.example.demo.mode1.biz.user.dto.UserSignUpDTO;
import com.example.demo.mode1.biz.user.entity.UserSocial;
import com.example.demo.mode1.biz.user.service.UserService;
import com.example.demo.mode1.biz.user.service.UserSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserSocialService userSocialService;

    @PostMapping("/user/signup")
    public String signUp(UserSignUpDTO dto) {
        SocialAuthenticationToken authentication = (SocialAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        return "/home";
    }

}
