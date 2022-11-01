package com.abnamro.smallcase.controller;

import com.abnamro.smallcase.dto.ApplicationUserDTO;
import com.abnamro.smallcase.service.UserService;
import com.abnamro.smallcase.model.ApplicationUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/users/add",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createUser(@Valid ApplicationUserDTO userDTO){
        ModelMapper modelMapper = new ModelMapper();
        ApplicationUser user = modelMapper.map(userDTO,ApplicationUser.class);
        if(userService.registerUser(user)){//true
            //System.out.println("print");
            return "redirect:/";
        }
        else {
            return "redirect:/register";
        }
    }

    @GetMapping("/user")
    public Long getUser(){
        return userService.getUser();
    }

}

