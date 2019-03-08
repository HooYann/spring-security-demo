package cn.beautybase.authorization.biz.user.service.impl;

import cn.beautybase.authorization.biz.base.ServiceException;
import cn.beautybase.authorization.biz.common.service.SmsCodeService;
import cn.beautybase.authorization.biz.constants.Deleted;
import cn.beautybase.authorization.biz.user.constants.SignUpType;
import cn.beautybase.authorization.biz.user.constants.SocialProviderID;
import cn.beautybase.authorization.biz.user.dao.UserDao;
import cn.beautybase.authorization.biz.user.dto.SignUpDTO;
import cn.beautybase.authorization.biz.user.dto.UserInfoDTO;
import cn.beautybase.authorization.biz.user.entity.User;
import cn.beautybase.authorization.biz.user.entity.UserSocial;
import cn.beautybase.authorization.biz.user.service.UserCacheService;
import cn.beautybase.authorization.biz.user.service.UserService;
import cn.beautybase.authorization.biz.user.service.UserSocialService;
import cn.beautybase.authorization.core.security.SecurityUtils;
import cn.beautybase.authorization.third.wechat.api.WechatMiniappService;
import cn.beautybase.authorization.utils.EmailUtils;
import cn.beautybase.authorization.utils.PhoneNumberUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserCacheService userCacheService;
    @Autowired
    private UserSocialService userSocialService;
    @Autowired
    private SmsCodeService smsCodeService;
    @Autowired
    @Lazy
    private WechatMiniappService wechatMiniappService;


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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User add(User user) {
        return userDao.save(user);
    }

    @Override
    public void signUp(SignUpDTO dto) {
        switch(dto.getType()){
            case SignUpType.WECHAT_MINIAPP:
                if(!StringUtils.hasText(dto.getPhoneNumber())) {
                    throw new ServiceException("请填写手机号");
                }
                //对比验证码
                if(!smsCodeService.check(dto.getPhoneNumber(), dto.getCaptcha())) {
                    throw new ServiceException("验证码错误");
                }
                signUpFromWechatMiniapp(dto.getPhoneNumber());
                break;
            case SignUpType.WECHAT_MINIAPP_PHONE_NUMBER:
                if(!dto.getExtData().containsKey("jsCode")
                        || !dto.getExtData().containsKey("encryptedData")
                        || !dto.getExtData().containsKey("iv")) {
                    throw new ServiceException("Missing parameters, jsCode=" + dto.getExtData().get("jsCode") + ", encryptedData=" + dto.getExtData().get("encryptedData") + ", iv=" + dto.getExtData().get("iv"));
                }
                JSONObject wxMaPhoneInfo = wechatMiniappService.getPhoneInfo(dto.getExtData().get("jsCode"), dto.getExtData().get("encryptedData"), dto.getExtData().get("iv"));
                signUpFromWechatMiniapp(wxMaPhoneInfo.getString("purePhoneNumber"));
                break;
            default:
                throw new ServiceException("不支持的注册类型");
        }
    }

    private void signUpFromWechatMiniapp(String phoneNumber) {
        User phoneUser = this.getByPhoneNumber(phoneNumber);
        if(phoneUser == null) {
            //如果之前不存在此手机号的用户，将手机号绑定到当前用户
            User currentUser = userDao.findById(SecurityUtils.currentUserId()).get();
            //currentUser.setUsername(dto.getPhoneNumber());
            currentUser.setPhoneNumber(phoneNumber);
            userDao.save(currentUser);
        } else {
            UserSocial userSocial = userSocialService.getByProviderIdAndUserId(SocialProviderID.WECHAT_MINIAPP, SecurityUtils.currentUserId());
            if(userSocial == null) {
                throw new ServiceException("微信小程序未授权");
            }

            //如果当前用户和手机号用户不是同一个用户
            if(!phoneUser.getId().equals(SecurityUtils.currentUserId())) {
                //更新社会关系
                userSocial.setUserId(phoneUser.getId());
                userSocial.setSignUp(false);
                userSocialService.updateById(userSocial);
                //删除当前用户
                User currentUser = userDao.findById(SecurityUtils.currentUserId()).get();
                currentUser.setDeleted(true);
                userDao.save(currentUser);
                //更新手机绑定用户
                if(!StringUtils.hasText(phoneUser.getNickname())) {
                    phoneUser.setNickname(currentUser.getNickname());
                }
                if(StringUtils.hasText(phoneUser.getAvatar())) {
                    phoneUser.setAvatar(currentUser.getAvatar());
                }
                userDao.save(phoneUser);
            }
        }
    }



}
