package cn.beautybase.authorization.service.impl;

import cn.beautybase.authorization.constants.Deleted;
import cn.beautybase.authorization.dao.UserDao;
import cn.beautybase.authorization.dto.UserInfoDTO;
import cn.beautybase.authorization.entity.User;
import cn.beautybase.authorization.service.UserCacheService;
import cn.beautybase.authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private UserDao userDao;

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
        //User user = userDao.findById(id).get();
        //return UserInfoDTO.initUser(user);
        return userCacheService.getInfo(id);
    }
}
