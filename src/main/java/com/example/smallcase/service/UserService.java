package com.example.smallcase.service;

import com.example.smallcase.model.ApplicationUser;
import com.example.smallcase.repository.ApplicationUserRepository;
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
            String pass = user.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(pass));
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

