package com.ai.translation.config;

import com.ai.translation.entity.Role;
import com.ai.translation.entity.User;
import com.ai.translation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        System.out.println(username + " email is present");
        String password = authentication.getCredentials().toString();

        User user = this.userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found Exceptions"));

        System.out.println(user + " user is present");
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password,getRole(user.getRoles()));
        } else {
            throw new BadCredentialsException("InValid credentials");
        }
    }

    private Set<SimpleGrantedAuthority> getRole(Set<Role> roles) {
        Set<SimpleGrantedAuthority> list=new HashSet<>();
        for (Role auth:roles){
            list.add(new SimpleGrantedAuthority("ROLE_" + auth.getRoles()));
        }
        return list;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
