package com.example.demo.mode1.biz.user.service.impl;

import com.example.demo.mode1.biz.user.dao.UserDao;
import com.example.demo.mode1.biz.user.dto.UserInfoDTO;
import com.example.demo.mode1.biz.user.entity.User;
import com.example.demo.mode1.biz.user.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCacheServiceImpl implements UserCacheService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserInfoDTO getInfo(Long id) {
        User user = userDao.findById(id).get();
        return new UserInfoDTO().init(user);
    }

}
