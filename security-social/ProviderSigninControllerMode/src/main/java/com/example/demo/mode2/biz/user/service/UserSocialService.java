package com.example.demo.mode2.biz.user.service;

import java.util.List;

public interface UserSocialService {
    List<String> listUserIdByProviderIdAndProviderUserId(String providerId, String providerUserId);
}
