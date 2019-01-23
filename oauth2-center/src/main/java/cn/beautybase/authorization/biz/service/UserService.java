package cn.beautybase.authorization.biz.service;

import cn.beautybase.authorization.biz.dto.UserInfoDTO;
import cn.beautybase.authorization.biz.entity.User;

public interface UserService {
    /**
     * 加一个短时间缓存
     * @param username
     * @return
     */
    User getByUsername(String username);


    /**
     * 获取用户实体
     * @param id
     * @return
     */
    User get(Long id);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    UserInfoDTO getInfo(Long id);
}
