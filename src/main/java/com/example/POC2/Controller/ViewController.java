package com.example.POC2.Controller;

import com.example.POC2.Model.ApplicationUser;
import com.example.POC2.Model.Baskets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/Register")
    public String Registration(Model model){
        ApplicationUser applicationUser = new ApplicationUser();
        model.addAttribute(applicationUser);
        return "register";
    }
    @RequestMapping("/")
    public String Login(){
        return "index";
    }
    @RequestMapping("/Home")
    public String Home(Model model){
        Baskets baskets = new Baskets();
        model.addAttribute(baskets);
        return "home";
    }
}
