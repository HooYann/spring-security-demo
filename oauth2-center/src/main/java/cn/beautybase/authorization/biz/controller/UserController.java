package cn.beautybase.authorization.biz.controller;

import cn.beautybase.authorization.biz.base.BaseController;
import cn.beautybase.authorization.biz.base.Result;
import cn.beautybase.authorization.core.security.SecurityUtils;
import cn.beautybase.authorization.biz.dto.UserInfoDTO;
import cn.beautybase.authorization.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/userinfo")
    public Result<UserInfoDTO> userInfo() {
        UserInfoDTO info = userService.getInfo(SecurityUtils.currentUserId());
        return this.succeed(info);
    }
}
