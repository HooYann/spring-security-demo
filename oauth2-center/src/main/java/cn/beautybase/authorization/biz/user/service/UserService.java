package cn.beautybase.authorization.biz.user.service;

import cn.beautybase.authorization.biz.user.dto.UserInfoDTO;
import cn.beautybase.authorization.biz.user.entity.User;

public interface UserService {

    /**
     * 登录时候用
     * 判断是用户名、手机号、邮箱中的哪一种方式登录
     * @param username
     * @return
     */
    User getByMultiUsername(String username);

    /**
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
