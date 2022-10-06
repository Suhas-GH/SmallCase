package com.example.POC2.Service;

import com.example.POC2.Model.ApplicationUser;
import com.example.POC2.Repository.ApplicationUserRepository;
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

    public ResponseEntity registerUser(ApplicationUser user){
        if(user.getUserName()!=null && user.getPassword()!=null && (!applicationUserRepository.existsByUserName(user.getUserName()))){
            String pass = user.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(pass));
            applicationUserRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    //To get UserId
    public Long getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName = authentication.getName();
        ApplicationUser user = applicationUserRepository.findByUserName(UserName);
        return user.getUserId();
    }


}

