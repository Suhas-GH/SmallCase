package com.example.smallcase.controller;


import com.example.smallcase.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PutMapping("cart/add/{basketId}")
    public ResponseEntity addToCart(@PathVariable Long basketId) {
         return cartService.addToCart(basketId);
    }

    @DeleteMapping("cart/remove/{basketId}")
    public ResponseEntity deleteFromCart(@PathVariable Long basketId){
        return cartService.deleteFromCart(basketId);
    }
}

