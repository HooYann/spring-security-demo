package cn.beautybase.authorization.biz.service.impl;

import cn.beautybase.authorization.biz.constants.Deleted;
import cn.beautybase.authorization.biz.dao.UserDao;
import cn.beautybase.authorization.biz.dto.UserInfoDTO;
import cn.beautybase.authorization.biz.entity.User;
import cn.beautybase.authorization.biz.service.UserCacheService;
import cn.beautybase.authorization.biz.service.UserService;
import cn.beautybase.authorization.utils.EmailUtils;
import cn.beautybase.authorization.utils.PhoneNumberUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserCacheService userCacheService;


    @Override
    public User getByMultiUsername(String username) {
        if(EmailUtils.match(username)) {
            //如果是邮箱，username=username or email=username
            List<User> list = userDao.listByUsernameOrEmail(username, Deleted.FALSE);
            if(list.isEmpty()) {
                return null;
            }
            for(User e : list) {
                if(username.equals(e.getEmail())) {
                    return e;
                }
            }
            return list.get(0);
        } else if(PhoneNumberUtils.match(username)) {
            //如果是手机号，username=username or phone_number=username
            List<User> list = userDao.listByUsernameOrPhoneNumber(username, Deleted.FALSE);
            if(list.isEmpty()) {
                return null;
            }
            for(User e : list) {
                if(username.equals(e.getPhoneNumber())) {
                    return e;
                }
            }
            return list.get(0);
        }
        return this.getByUsername(username);
    }

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
