package cn.beautybase.authorization.service.impl;

import cn.beautybase.authorization.dao.UserDao;
import cn.beautybase.authorization.dto.UserInfoDTO;
import cn.beautybase.authorization.entity.User;
import cn.beautybase.authorization.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCacheServiceImpl implements UserCacheService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserInfoDTO getInfo(Long id) {
        User user = userDao.findById(id).get();
        return UserInfoDTO.init(user);
    }

}
