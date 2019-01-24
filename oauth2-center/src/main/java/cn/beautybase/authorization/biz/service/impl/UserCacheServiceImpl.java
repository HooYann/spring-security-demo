package cn.beautybase.authorization.biz.service.impl;

import cn.beautybase.authorization.biz.dao.UserDao;
import cn.beautybase.authorization.biz.dto.UserInfoDTO;
import cn.beautybase.authorization.biz.entity.User;
import cn.beautybase.authorization.biz.service.UserCacheService;
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
