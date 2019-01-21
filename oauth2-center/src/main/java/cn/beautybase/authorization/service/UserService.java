package cn.beautybase.authorization.service;

import cn.beautybase.authorization.dto.UserInfoDTO;
import cn.beautybase.authorization.entity.User;

public interface UserService {
    /**
     * 加一个短时间缓存
     * @param username
     * @return
     */
    User getByUsername(String username);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    UserInfoDTO getInfo(Long id);
}
