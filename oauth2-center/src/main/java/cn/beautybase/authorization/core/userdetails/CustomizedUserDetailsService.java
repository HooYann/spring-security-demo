package cn.beautybase.authorization.core.userdetails;

import cn.beautybase.authorization.core.exception.UserIdNotFoundException;
import cn.beautybase.authorization.entity.User;
import cn.beautybase.authorization.service.RoleService;
import cn.beautybase.authorization.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class CustomizedUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUsername(username);
        if(user == null) {
            log.error("CustomizedUserDetailsService.loadUserByUsername, user=null");
            throw new UsernameNotFoundException("CustomizedUserDetailsService.loadUserByUsername, username = " + username + " not found");
        }
        //user.setAuthorities(); 后续...
        return user;
    }


}
