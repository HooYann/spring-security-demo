package cn.beautybase.authorization.biz.user.service.impl;

import cn.beautybase.authorization.biz.user.dao.UserDao;
import cn.beautybase.authorization.biz.user.dto.UserInfoDTO;
import cn.beautybase.authorization.biz.user.entity.User;
import cn.beautybase.authorization.biz.user.service.UserCacheService;
import cn.beautybase.authorization.biz.user.service.UserService;
import cn.beautybase.authorization.utils.EmailUtils;
import cn.beautybase.authorization.utils.PhoneNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            List<User> list = userDao.listByUsernameOrEmail(username, false);
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
            List<User> list = userDao.listByUsernameOrPhoneNumber(username, true);
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
        return userDao.getByUsername(username, false);
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return userDao.getByPhoneNumber(phoneNumber, false);
    }

    @Override
    public User get(Long id, boolean inMemory) {
        if(inMemory) {
            UserInfoDTO userInfo = this.getInfo(id);
            if(userInfo == null) {
                return null;
            }
            return userInfo.extractUser();
        }
        return userDao.findById(id).get();
    }

    @Override
    public UserInfoDTO getInfo(Long id) {
        return userCacheService.getInfo(id);
    }

    @Override
    public void evictInfo(Long id) {
        userCacheService.evictInfo(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User save(User user) {
        return userDao.save(user);
    }





}
