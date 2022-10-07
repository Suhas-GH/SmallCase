package com.example.POC2.Controller;

import com.example.POC2.Model.ApplicationUser;
import com.example.POC2.Model.Baskets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @Autowired
    private BasketController basketController;

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
        model.addAttribute("basketList",basketController.getBaskets());
        //System.out.println(basketController.getBaskets());
        return "home";
    }

    @GetMapping("/BasketDetails/{BasketId}")
    public String BasketDetails(Model model, @PathVariable Long BasketId){
        Baskets baskets = basketController.getBasketDetails(BasketId).orElseThrow(() -> new IllegalArgumentException("Invalid Basket Id:" + BasketId));
        model.addAttribute("basketDetails",baskets);
        //System.out.println(basketController.getBasketDetails(BasketId));
        return "basketdetails";
    }
}
