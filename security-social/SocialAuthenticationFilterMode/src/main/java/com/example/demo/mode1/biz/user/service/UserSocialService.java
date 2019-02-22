package com.example.demo.mode1.biz.user.service;

import com.example.demo.mode1.biz.user.entity.UserSocial;

import java.util.List;

public interface UserSocialService {

    List<String> listUserIdByProviderIdAndProviderUserId(String providerId, String providerUserId);

    void add(Long userId, String providerId, String providerUserId);

    List<UserSocial> listByProviderIdAndProviderUserId(String providerId, String providerUserId);

    UserSocial getByProviderIdAndProviderUserId(String providerId, String providerUserId);

    void removeByProviderIdAndProviderUserId(String providerId, String providerUserId);
}
