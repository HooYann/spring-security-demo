package cn.beautybase.authorization.service.impl;

import cn.beautybase.authorization.constants.Deleted;
import cn.beautybase.authorization.dao.UserDao;
import cn.beautybase.authorization.entity.User;
import cn.beautybase.authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username, Deleted.FALSE);
    }
}
