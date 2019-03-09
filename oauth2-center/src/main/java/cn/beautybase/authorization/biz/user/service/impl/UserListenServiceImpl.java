package cn.beautybase.authorization.biz.user.service.impl;

import cn.beautybase.authorization.biz.user.entity.User;
import cn.beautybase.authorization.biz.user.event.UserUpdateEvent;
import cn.beautybase.authorization.biz.user.service.UserCacheService;
import cn.beautybase.authorization.biz.user.service.UserListenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserListenServiceImpl implements UserListenService {

    @Autowired
    private UserCacheService userCacheService;

    @Override
    public void listenUserUpdateEvent(UserUpdateEvent event) {
        User user = event.getUser();
        //去除缓存
        userCacheService.evictInfo(user.getId());
    }
}
