package cn.beautybase.authorization.biz.user.service.impl;

import cn.beautybase.authorization.biz.user.dao.UserSocialDataDao;
import cn.beautybase.authorization.biz.user.entity.UserSocialData;
import cn.beautybase.authorization.biz.user.service.UserSocialDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSocialDataServiceImpl implements UserSocialDataService {

    @Autowired
    private UserSocialDataDao userSocialDataDao;

    @Override
    public UserSocialData add(UserSocialData userSocialData) {
        return userSocialDataDao.save(userSocialData);
    }
}
