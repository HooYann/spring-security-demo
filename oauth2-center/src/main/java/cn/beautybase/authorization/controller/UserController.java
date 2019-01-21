package cn.beautybase.authorization.controller;

import cn.beautybase.authorization.base.BaseController;
import cn.beautybase.authorization.base.Result;
import cn.beautybase.authorization.base.SecurityUtils;
import cn.beautybase.authorization.dto.UserInfoDTO;
import cn.beautybase.authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
