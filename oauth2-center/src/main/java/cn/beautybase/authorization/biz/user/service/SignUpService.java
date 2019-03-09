package cn.beautybase.authorization.biz.user.service;

import cn.beautybase.authorization.biz.user.dto.SignUpInputDTO;
import cn.beautybase.authorization.biz.user.dto.SignUpOutputDTO;

/**
 * 用户注册
 */
public interface SignUpService {

    /**
     * 用户注册
     */
    SignUpOutputDTO signUp(SignUpInputDTO dto);

    /**
     * 社会用户注册
     * @param dto
     */
    SignUpOutputDTO socialSignUp(SignUpInputDTO dto);

}
