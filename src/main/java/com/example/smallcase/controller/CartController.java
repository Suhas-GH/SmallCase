package com.example.smallcase.controller;


import com.example.smallcase.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PutMapping("cart/add/{basketId}")
    public void addToCart(@PathVariable Long basketId) {
         cartService.addToCart(basketId);
    }

    @DeleteMapping("cart/remove/{basketId}")
    public void deleteFromCart(@PathVariable Long basketId){
         cartService.deleteFromCart(basketId);
    }
}

