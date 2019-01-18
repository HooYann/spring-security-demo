package cn.beautybase.authorization.base;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static UserDetails currentUser() {
        return currentUser(UserDetails.class);
    }

    public static <T> T currentUser(Class<T> clazz) {
        T user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(clazz.isAssignableFrom(principal.getClass())) {
            user = (T) principal;
        }
        return user;
    }

}
