package com.example.demo.mode1.userdetails;

import com.example.demo.mode1.biz.user.dto.UserInfoDTO;
import com.example.demo.mode1.biz.user.entity.UserSocial;
import com.example.demo.mode1.biz.user.service.UserService;
import com.example.demo.mode1.biz.user.service.UserSocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class CustomizedSocialUserDetailsService implements SocialUserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        System.out.println("loadUserByUserId: " + userId);

        UserInfoDTO userInfo = userService.getInfo(Long.valueOf(userId));
        if(userInfo == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new SocialUser(userId, "",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("user"));
    }

}
