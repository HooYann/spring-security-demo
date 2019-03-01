package cn.beautybase.authorization.core.security.userdetails;

import cn.beautybase.authorization.biz.user.entity.User;
import cn.beautybase.authorization.biz.user.entity.UserSocial;
import com.alibaba.fastjson.JSONObject;

public interface SocialUserDetailsService {

    UserSocial get(String providerId, String openId);

    UserSocial add(String providerId, String providerUserId, User user, boolean signUp, JSONObject socialExtData);

}
