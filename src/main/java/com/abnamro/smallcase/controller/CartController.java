package com.abnamro.smallcase.controller;


import com.abnamro.smallcase.model.Cart;
import com.abnamro.smallcase.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

//    @GetMapping("/cart")
//    public List<Cart> getCart11(){
//        return cartService.getCart11();
//    }
}

