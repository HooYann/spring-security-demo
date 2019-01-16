package com.example.demo.oauth2.authorizationserver.service;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.oauth2.authorizationserver.dao.UserInfoRepository;
import com.example.demo.oauth2.authorizationserver.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserInfoEntity findByUsername(String username) {
        QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("deleted", "N");
        return userInfoRepository.selectOne(wrapper);
    }


}
