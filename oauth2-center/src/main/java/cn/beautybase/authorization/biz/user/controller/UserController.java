package cn.beautybase.authorization.biz.user.controller;

import cn.beautybase.authorization.biz.base.BaseController;
import cn.beautybase.authorization.biz.base.Result;
import cn.beautybase.authorization.biz.user.dto.SignUpDTO;
import cn.beautybase.authorization.core.security.SecurityUtils;
import cn.beautybase.authorization.biz.user.dto.UserInfoDTO;
import cn.beautybase.authorization.biz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/api/user/info")
    public Result<UserInfoDTO> userInfo() {
        UserInfoDTO info = userService.getInfo(SecurityUtils.currentUserId());
        return this.succeed(info);
    }

    @PostMapping(value = "/api/user/signup")
    public Result<UserInfoDTO> signUp(@RequestBody SignUpDTO dto) {
        userService.signUp(dto);
        return this.succeed();
    }

}
