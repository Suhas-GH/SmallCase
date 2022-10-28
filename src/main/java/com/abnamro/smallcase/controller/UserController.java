package com.abnamro.smallcase.controller;

import com.abnamro.smallcase.dto.ApplicationUserDTO;
import com.abnamro.smallcase.service.UserService;
import com.abnamro.smallcase.model.ApplicationUser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/users/add",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createUser(ApplicationUserDTO userDTO){
        ModelMapper modelMapper = new ModelMapper();
        ApplicationUser user = modelMapper.map(userDTO,ApplicationUser.class);
        if(userService.registerUser(user)){
            LOGGER.info("User Registered Successfully");
            return "redirect:/";
        }
        else {
            LOGGER.error("User not Registered");
            return "redirect:/register";
        }
    }

    @GetMapping("/user")
    public Long getUser(){
        return userService.getUser();
    }

}

