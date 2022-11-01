package com.abnamro.smallcase.controller;

import com.abnamro.smallcase.dto.ApplicationUserDTO;
import com.abnamro.smallcase.repository.ApplicationUserRepository;
import com.abnamro.smallcase.service.UserService;
import com.abnamro.smallcase.model.ApplicationUser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/users/add",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createUser(@Valid ApplicationUserDTO userDTO){
        ModelMapper modelMapper = new ModelMapper();
        ApplicationUser user = modelMapper.map(userDTO,ApplicationUser.class);
        if(userService.registerUser(user).getStatusCodeValue()==201){
            LOGGER.info("User Registered Successfully");
            return "redirect:/";
        }
        else {
            LOGGER.error("User not Registered");
            return "redirect:/register";
        }
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        ApplicationUser user = applicationUserRepository.findByUserName(userName);
        if(user == null){
            throw new NoSuchElementException("User Not Found");
        }
        else {
            return new ResponseEntity<>(userService.getUser(user), HttpStatus.OK);
        }
    }

}

