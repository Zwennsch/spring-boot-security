package com.svenschroeder.security_example.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailService implements UserDetailsService{

    @Autowired
    private MyUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = repository.findUserByName(username);
        if(user.isPresent()){
            var userObject = user.get();
            return User.builder()
                .username(userObject.getUsername())
                .password(userObject.getPassword()) 
                .roles(getRoles(userObject))
                .build();
        }else{
            throw new UsernameNotFoundException("User with name: " + username + " not found");
        }
    }

    private String[] getRoles(MyUser user) {
        if(user.getRole() == null){
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }
    
}
