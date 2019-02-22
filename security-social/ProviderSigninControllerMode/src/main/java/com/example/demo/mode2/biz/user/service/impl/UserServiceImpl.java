package com.example.demo.mode2.biz.user.service.impl;

import com.example.demo.mode2.biz.constants.Deleted;
import com.example.demo.mode2.biz.user.dao.UserDao;
import com.example.demo.mode2.biz.user.dto.UserInfoDTO;
import com.example.demo.mode2.biz.user.entity.User;
import com.example.demo.mode2.biz.user.service.UserCacheService;
import com.example.demo.mode2.biz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Autowired
    private UserCacheService userCacheService;

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username, Deleted.FALSE);
    }

    @Override
    public UserInfoDTO getInfo(Long id) {
        return userCacheService.getInfo(id);
    }
}
