package cn.beautybase.authorization.core.oauth2.handler;

import cn.beautybase.authorization.biz.base.ErrorCode;
import cn.beautybase.authorization.biz.base.Result;
import cn.beautybase.authorization.core.security.SecurityUser;
import cn.beautybase.authorization.core.security.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CustomizedAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        SecurityUser currentUser = SecurityUtils.currentUser();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        if(currentUser != null && isSocialUser(currentUser.getAuthorities())) {
            response.getWriter().write(objectMapper.writeValueAsString(Result.buildFailure(ErrorCode.NOT_SIGN_UP.value(), ErrorCode.NOT_SIGN_UP.message())));
            return;
        }
        response.getWriter().write(objectMapper.writeValueAsString(Result.buildFailure(ErrorCode.UNAUTHORIZED.value(), ErrorCode.UNAUTHORIZED.message())));
    }

    private boolean isSocialUser(Collection<? extends GrantedAuthority> authorities) {
        for(GrantedAuthority e : authorities){
            return e.getAuthority().equals("social_user");
        }
        return false;
    }
}
