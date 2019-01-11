package com.example.demo.base;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsEntity userDetails = new UserDetailsEntity();
        userDetails.setUsername(username);
        userDetails.setPassword(new BCryptPasswordEncoder().encode("admin"));
        userDetails.setAccountNonExpired(true);
        userDetails.setAccountNonLocked(true);
        userDetails.setCredentialsNonExpired(true);
        userDetails.setEnabled(true);
        return userDetails;
    }
}
