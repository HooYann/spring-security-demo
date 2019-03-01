package cn.beautybase.authorization.biz.user.service;

import cn.beautybase.authorization.biz.user.entity.UserSocial;

public interface UserSocialService {

    UserSocial get(String providerId, String providerUserId);

    UserSocial add(UserSocial userSocial);
}
