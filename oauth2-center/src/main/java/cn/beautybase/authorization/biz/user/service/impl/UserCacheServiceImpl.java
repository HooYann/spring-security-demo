package cn.beautybase.authorization.biz.user.service.impl;

import cn.beautybase.authorization.biz.user.dao.UserDao;
import cn.beautybase.authorization.biz.user.dto.UserInfoDTO;
import cn.beautybase.authorization.biz.user.entity.User;
import cn.beautybase.authorization.biz.user.service.UserCacheService;
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

    @Override
    public void evictInfo(Long id) {
        //do nothing
    }

}
