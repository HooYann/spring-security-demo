package com.example.demo.mode1.biz.user.service.impl;

import com.example.demo.mode1.biz.user.dao.UserSocialDao;
import com.example.demo.mode1.biz.user.entity.UserSocial;
import com.example.demo.mode1.biz.user.service.UserSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Long userId, String providerId, String providerUserId) {
        UserSocial entity = new UserSocial(userId, providerId, providerUserId);
        entity.setCreateTime(LocalDateTime.now());
        userSocialDao.save(entity);
    }

    @Override
    public List<UserSocial> listByProviderIdAndProviderUserId(String providerId, String providerUserId) {
        return userSocialDao.listByProviderIdAndProviderUserId(providerId, providerUserId);
    }

    @Override
    public UserSocial getByProviderIdAndProviderUserId(String providerId, String providerUserId) {
        List<UserSocial> list = this.listByProviderIdAndProviderUserId(providerId, providerUserId);
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void removeByProviderIdAndProviderUserId(String providerId, String providerUserId) {
        userSocialDao.deleteByProviderIdAndProviderUserId(providerId, providerUserId);
    }

}
