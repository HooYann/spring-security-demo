package cn.beautybase.authorization.core.userdetails;

import cn.beautybase.authorization.entity.User;
import cn.beautybase.authorization.service.RoleService;
import cn.beautybase.authorization.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomizedUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        //user.setAuthorities(); 后续...
        return user;
    }
}
