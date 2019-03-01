package cn.beautybase.authorization.biz.user.service.impl;

import cn.beautybase.authorization.biz.user.dao.UserSocialDao;
import cn.beautybase.authorization.biz.user.entity.UserSocial;
import cn.beautybase.authorization.biz.user.service.UserSocialService;
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
    public UserSocial get(String providerId, String providerUserId) {
        return userSocialDao.get(providerId, providerUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSocial add(UserSocial userSocial) {
        return userSocialDao.save(userSocial);
    }
}
