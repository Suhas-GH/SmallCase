package com.abnamro.smallcase.service;

import com.abnamro.smallcase.model.ApplicationUser;
import com.abnamro.smallcase.repository.ApplicationUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<Object> registerUser(ApplicationUser user){
        if((applicationUserRepository.existsByUserName(user.getUserName()))){
            throw new EmptyResultDataAccessException("Username Already Exists", 1);
        } else if(user.getUserName()!=null && user.getPassword()!=null){
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            applicationUserRepository.save(user);
            LOGGER.info("User Created");
            return new ResponseEntity<>("User Created",HttpStatus.CREATED);
        } else {
            throw new NoSuchElementException("Username or Password is Null");
        }
    }


    //To get UserId
    public Long getUser(ApplicationUser user){
        LOGGER.info("User Found");
        return user.getUserId();
    }
}

