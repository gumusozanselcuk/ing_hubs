package com.inghubs.creditmodule.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("furkan".equals(username)){
            return new CustomUserDetails(
                    "furkan",
                    new BCryptPasswordEncoder().encode("furkan"),
                    "CUSTOMER",
                    2L
            );
        }else if("admin".equals(username)){
            return new CustomUserDetails(
                    "admin",
                    new BCryptPasswordEncoder().encode("admin"),
                    "ADMIN",
                    null
            );
        }
        else{
            throw new UsernameNotFoundException("User not found!");
        }
    }
}
