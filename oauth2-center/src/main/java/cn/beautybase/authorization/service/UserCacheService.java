package cn.beautybase.authorization.service;

import cn.beautybase.authorization.dto.UserInfoDTO;

public interface UserCacheService {
    UserInfoDTO getInfo(Long id);
}
