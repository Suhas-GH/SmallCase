package com.example.smallcase.controller;

import com.example.smallcase.model.ApplicationUser;
import com.example.smallcase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/users/add",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String createUser(ApplicationUser user){
        ResponseEntity response = userService.registerUser(user);
        if(response.getStatusCodeValue()==201){
            return "redirect:/";
        }
        else {
            return "redirect:/Register";
        }
    }

    @GetMapping("/user")
    public Long getUser(){
        return userService.getUser();
    }

}
