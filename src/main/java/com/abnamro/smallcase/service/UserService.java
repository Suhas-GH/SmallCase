package com.abnamro.smallcase.service;

import com.abnamro.smallcase.model.ApplicationUser;
import com.abnamro.smallcase.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    public boolean registerUser(ApplicationUser user){
        if(user.getUserName()!=null && user.getPassword()!=null && (!applicationUserRepository.existsByUserName(user.getUserName()))){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            applicationUserRepository.save(user);
            return true;
        } else {
            return false;
        }
    }


    //To get UserId
    public Long getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        ApplicationUser user = applicationUserRepository.findByUserName(userName);
        return user.getUserId();
    }


}

