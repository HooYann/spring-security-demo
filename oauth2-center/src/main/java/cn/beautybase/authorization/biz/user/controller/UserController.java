package cn.beautybase.authorization.biz.user.controller;

import cn.beautybase.authorization.biz.base.BaseController;
import cn.beautybase.authorization.biz.base.Result;
import cn.beautybase.authorization.biz.user.dto.SignUpInputDTO;
import cn.beautybase.authorization.biz.user.dto.SignUpOutputDTO;
import cn.beautybase.authorization.biz.user.service.SignUpService;
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
    @Autowired
    private SignUpService signUpService;

    @GetMapping(value = "/api/user/info")
    public Result<UserInfoDTO> userInfo() {
        UserInfoDTO info = userService.getInfo(SecurityUtils.currentUserId());
        return this.succeed(info);
    }

    @PostMapping(value = "/signup")
    public Result<Void> signUp(SignUpInputDTO dto) {
        SignUpOutputDTO output = signUpService.signUp(dto);
        return this.succeed();
    }

    @PostMapping(value = "/api/user/signup/social")
    public Result<String> socialSignUp(@RequestBody SignUpInputDTO dto) {
        SignUpOutputDTO output = signUpService.socialSignUp(dto);
        return this.succeed(output.getToken());
    }

}
