package cn.beautybase.authorization.base;

import cn.beautybase.authorization.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static Long currentUserId() {
        User user = currentUser(User.class);
        if(user == null) {
            return null;
        }
        return user.getId();
    }

    public static User currentUser() {
        return currentUser(User.class);
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
