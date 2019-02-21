package com.example.demo.mode1.biz.user.service;

import java.util.List;

public interface UserSocialService {
    List<String> listUserIdByProviderIdAndProviderUserId(String providerId, String providerUserId);
}
