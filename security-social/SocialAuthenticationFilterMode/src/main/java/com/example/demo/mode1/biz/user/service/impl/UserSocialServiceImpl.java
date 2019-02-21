package com.example.demo.mode1.biz.user.service.impl;

import com.example.demo.mode1.biz.user.dao.UserSocialDao;
import com.example.demo.mode1.biz.user.entity.UserSocial;
import com.example.demo.mode1.biz.user.service.UserSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSocialServiceImpl implements UserSocialService {

    @Autowired
    private UserSocialDao userSocialDao;

    @Override
    public List<String> listUserIdByProviderIdAndProviderUserId(String providerId, String providerUserId) {
        List<String> userIdList = new ArrayList();
        List<UserSocial> list = this.listByProviderIdAndProviderUserId(providerId, providerUserId);
        list.forEach(e -> userIdList.add(String.valueOf(e.getUserId())));
        return userIdList;
    }

    private List<UserSocial> listByProviderIdAndProviderUserId(String providerId, String providerUserId) {
        return userSocialDao.listByProviderIdAndProviderUserId(providerId, providerUserId);
    }
}
