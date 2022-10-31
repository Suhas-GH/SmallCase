package com.abnamro.smallcase.service;

import com.abnamro.smallcase.model.ApplicationUser;
import com.abnamro.smallcase.repository.ApplicationUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<Object> registerUser(ApplicationUser user){
        if((applicationUserRepository.existsByUserName(user.getUserName()))){
            LOGGER.error("Username Already Exists");
            return new ResponseEntity<>("Username Already Exists", HttpStatus.BAD_REQUEST);
        }
        else if(user.getUserName()!=null && user.getPassword()!=null){
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            applicationUserRepository.save(user);
            LOGGER.info("User Created");
            return new ResponseEntity<>("User Created",HttpStatus.CREATED);
        } else {
            LOGGER.error("Username or Password is Null");
            return new ResponseEntity<>("Username or Password is Null", HttpStatus.BAD_REQUEST);
        }
    }


    //To get UserId
    public Long getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        ApplicationUser user = applicationUserRepository.findByUserName(userName);
        if(user == null){
            LOGGER.error("User Not Found");
            return null;
        }
        else {
            LOGGER.info("User Found");
            return user.getUserId();
        }
    }
}

