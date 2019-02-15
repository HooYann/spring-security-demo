package com.example.demo.social.github.signin;

import com.example.demo.social.github.api.GitHub;
import com.example.demo.social.github.api.GitHubUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

@Component
public class SimpleSignInAdapter implements SignInAdapter {
    @Override
    public String signIn(String id, Connection<?> connection, NativeWebRequest nativeWebRequest) {
        ConnectionKey key = connection.getKey();
        // 通过providerId判断是否为微信公众平台授权
        if ("github".equalsIgnoreCase(key.getProviderId())) {
            // 通过微信openId获取到用户详细信息
            GitHubUser user = ((GitHub)connection.getApi()).getUser();
            // 微信用户详细信息,可以记录到数据库.这里直接打印到后台
            System.out.println(user);
            //SecurityContextHolder.getContext().setAuthentication(
            //        new UsernamePasswordAuthenticationToken(id, null, null));
            //return null;
            return "/signup"; // 返回登录成功后跳转的url
        }
        return null;
    }
}
