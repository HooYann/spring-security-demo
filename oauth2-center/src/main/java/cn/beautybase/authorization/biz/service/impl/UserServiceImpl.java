package cn.beautybase.authorization.biz.service.impl;

import cn.beautybase.authorization.biz.constants.Deleted;
import cn.beautybase.authorization.biz.dao.UserDao;
import cn.beautybase.authorization.biz.dto.UserInfoDTO;
import cn.beautybase.authorization.biz.entity.User;
import cn.beautybase.authorization.biz.service.UserCacheService;
import cn.beautybase.authorization.biz.service.UserService;
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
    public User get(Long id) {
        UserInfoDTO userInfo = this.getInfo(id);
        if(userInfo == null) {
            return null;
        }
        return userInfo.extractUser();
    }

    @Override
    public UserInfoDTO getInfo(Long id) {
        return userCacheService.getInfo(id);
    }
}
