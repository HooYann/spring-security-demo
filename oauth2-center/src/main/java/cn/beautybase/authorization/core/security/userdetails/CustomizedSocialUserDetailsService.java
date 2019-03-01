package cn.beautybase.authorization.core.security.userdetails;

import cn.beautybase.authorization.biz.user.entity.User;
import cn.beautybase.authorization.biz.user.entity.UserSocial;
import cn.beautybase.authorization.biz.user.entity.UserSocialData;
import cn.beautybase.authorization.biz.user.service.UserService;
import cn.beautybase.authorization.biz.user.service.UserSocialDataService;
import cn.beautybase.authorization.biz.user.service.UserSocialService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class CustomizedSocialUserDetailsService implements SocialUserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserSocialService userSocialService;
    @Autowired
    private UserSocialDataService userSocialDataService;

    @Override
    public UserSocial get(String providerId, String providerUserId) {
        UserSocial userSocial = userSocialService.get(providerId, providerUserId);
        if(userSocial == null) {
            return null;
        }
        User user = userService.get(userSocial.getUserId());
        user.setSignUp(userSocial.getSignUp());
        userSocial.setUser(user);
        return userSocial;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserSocial add(String providerId, String providerUserId, User user, boolean signUp, JSONObject socialExtData) {
        user = userService.add(user);

        UserSocial userSocial = new UserSocial();
        userSocial.setUserId(user.getId());
        userSocial.setProviderId(providerId);
        userSocial.setProviderUserId(providerUserId);
        userSocial.setSignUp(signUp);
        userSocial = userSocialService.add(userSocial);

        UserSocialData userSocialData = new UserSocialData();
        userSocialData.setId(userSocial.getId());
        userSocialData.setExtData(socialExtData.toString());
        userSocialDataService.add(userSocialData);

        user.setSignUp(signUp);
        userSocial.setUser(user);

        return userSocial;
    }
}
