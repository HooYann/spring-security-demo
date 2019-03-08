package cn.beautybase.authorization.biz.user.service;

import cn.beautybase.authorization.biz.user.entity.UserSocial;

import java.util.List;

public interface UserSocialService {

    UserSocial add(UserSocial userSocial);

    void updateById(UserSocial userSocial);

    UserSocial get(String providerId, String providerUserId);

    UserSocial getByProviderIdAndUserId(String providerId, Long userId);

    List<UserSocial> listByUserId(Long userId);


}
