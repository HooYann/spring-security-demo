package cn.beautybase.authorization.biz.user.service.impl;

import cn.beautybase.authorization.biz.base.ServiceException;
import cn.beautybase.authorization.biz.common.service.SmsCodeService;
import cn.beautybase.authorization.biz.user.constants.SignUpType;
import cn.beautybase.authorization.biz.user.constants.SocialProviderID;
import cn.beautybase.authorization.biz.user.dto.SignUpInputDTO;
import cn.beautybase.authorization.biz.user.dto.SignUpOutputDTO;
import cn.beautybase.authorization.biz.user.entity.Client;
import cn.beautybase.authorization.biz.user.entity.User;
import cn.beautybase.authorization.biz.user.entity.UserSocial;
import cn.beautybase.authorization.biz.user.event.UserUpdateEvent;
import cn.beautybase.authorization.biz.user.service.ClientService;
import cn.beautybase.authorization.biz.user.service.SignUpService;
import cn.beautybase.authorization.biz.user.service.UserService;
import cn.beautybase.authorization.biz.user.service.UserSocialService;
import cn.beautybase.authorization.core.oauth2.provider.token.AutoReSignInTokenGenerator;
import cn.beautybase.authorization.core.security.SecurityUtils;
import cn.beautybase.authorization.core.security.authentication.social.SocialAuthenticationToken;
import cn.beautybase.authorization.third.wechat.api.WechatMiniappService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserSocialService userSocialService;
    @Autowired
    private SmsCodeService smsCodeService;
    @Autowired
    @Lazy
    private WechatMiniappService wechatMiniappService;
    @Autowired
    private AutoReSignInTokenGenerator autoReSignInTokenGenerator;


    @Override
    public SignUpOutputDTO signUp(SignUpInputDTO dto) {
        //TODO
        return null;
    }

    @Override
    public SignUpOutputDTO socialSignUp(SignUpInputDTO dto) {
        SignUpOutputDTO result = null;
        if(!StringUtils.hasText(dto.getType())) {
            throw new ServiceException("未指定注册类型");
        }
        switch(dto.getType()){
            case SignUpType.WECHAT_MINIAPP:
                if(!StringUtils.hasText(dto.getPhoneNumber())) {
                    throw new ServiceException("请填写手机号");
                }
                //对比验证码
                if(!smsCodeService.check(dto.getPhoneNumber(), dto.getCaptcha())) {
                    throw new ServiceException("验证码错误");
                }
                result = socialSignUpFromWechatMiniapp(dto.getPhoneNumber());
                break;
            case SignUpType.WECHAT_MINIAPP_PHONE_NUMBER:
                if(!dto.getExtData().containsKey("jsCode")
                        || !dto.getExtData().containsKey("encryptedData")
                        || !dto.getExtData().containsKey("iv")) {
                    throw new ServiceException("Missing parameters, jsCode=" + dto.getExtData().get("jsCode") + ", encryptedData=" + dto.getExtData().get("encryptedData") + ", iv=" + dto.getExtData().get("iv"));
                }
                JSONObject wxMaPhoneInfo = wechatMiniappService.getPhoneInfo(dto.getExtData().get("jsCode"), dto.getExtData().get("encryptedData"), dto.getExtData().get("iv"));
                result = socialSignUpFromWechatMiniapp(wxMaPhoneInfo.getString("purePhoneNumber"));
                break;
            default:
                throw new ServiceException("不支持的注册类型");
        }

        if(result != null) {
            this.applicationContext.publishEvent(new UserUpdateEvent(this, result.getUser()));
            result.setUser(null);
        }
        return result;
    }

    private SignUpOutputDTO socialSignUpFromWechatMiniapp(String phoneNumber) {
        SignUpOutputDTO result = new SignUpOutputDTO();

        User phoneUser = userService.getByPhoneNumber(phoneNumber);
        if(phoneUser == null) {
            //如果之前不存在此手机号的用户，将手机号绑定到当前用户
            User currentUser = userService.get(SecurityUtils.currentUserId(), false);
            //currentUser.setUsername(dto.getPhoneNumber());
            currentUser.setPhoneNumber(phoneNumber);
            userService.save(currentUser);
            result.setUser(currentUser);
        } else {
            UserSocial userSocial = userSocialService.getByProviderIdAndUserId(SocialProviderID.WECHAT_MINIAPP, SecurityUtils.currentUserId());
            if(userSocial == null) {
                throw new ServiceException("微信小程序未授权");
            }

            //如果当前用户和手机号用户不是同一个用户
            if(!phoneUser.getId().equals(SecurityUtils.currentUserId())) {
                //更新社会关系
                userSocial.setUserId(phoneUser.getId());
                userSocial.setSignUp(false);
                userSocialService.updateById(userSocial);
                //删除当前用户
                User currentUser = userService.get(SecurityUtils.currentUserId(), false);
                currentUser.setDeleted(true);
                userService.save(currentUser);
                //更新手机绑定用户
                if(!StringUtils.hasText(phoneUser.getNickname())) {
                    phoneUser.setNickname(currentUser.getNickname());
                }
                if(StringUtils.hasText(phoneUser.getAvatar())) {
                    phoneUser.setAvatar(currentUser.getAvatar());
                }
                userService.save(phoneUser);
            }

            result.setUser(phoneUser);
        }
        Client client = clientService.getByClientId(SocialProviderID.WECHAT_MINIAPP);
        Authentication authentication = new SocialAuthenticationToken(result.getUser(), "N/A", result.getUser().getAuthorities());
        String token = autoReSignInTokenGenerator.getAccessToken(client, "user", SocialProviderID.WECHAT_MINIAPP, authentication).getValue();
        result.setToken(token);
        return result;
    }
}
