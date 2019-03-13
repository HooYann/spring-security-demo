package cn.beautybase.authorization.core.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 只能获取当前用户ID和用户名，jwt只携带用户信息里的用户ID和用户名
 */
public class SecurityUtils {

    public static Long currentUserId() {
        SecurityUser user = currentUser();
        if(user == null) {
            return null;
        }
        return user.getId();
    }

    public static String currentUsername() {
        SecurityUser user = currentUser();
        if(user == null) {
            return null;
        }
        return user.getUsername();
    }

    private static SecurityUser currentUser() {
        return currentUser(SecurityUser.class);
    }

    private static <T> T currentUser(Class<T> clazz) {
        T user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(clazz.isAssignableFrom(principal.getClass())) {
            user = (T) principal;
        }
        return user;
    }

}
