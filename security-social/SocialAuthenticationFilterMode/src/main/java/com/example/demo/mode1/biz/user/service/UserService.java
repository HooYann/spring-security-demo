package com.example.demo.mode1.biz.user.service;


import com.example.demo.mode1.biz.user.dto.UserInfoDTO;
import com.example.demo.mode1.biz.user.entity.User;

public interface UserService {


    /**
     * @param username
     * @return
     */
    User getByUsername(String username);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    UserInfoDTO getInfo(Long id);


    User add(User user);
}
