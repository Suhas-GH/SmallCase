package com.abnamro.smallcase.controller;


import com.abnamro.smallcase.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PutMapping("cart/add/{basketId}")
    public ResponseEntity<Object> addToCart(@NotNull @PathVariable Long basketId) {
        cartService.addToCart(basketId);
        return new ResponseEntity<>("Basket Added To Cart", HttpStatus.ACCEPTED);
    }

    @DeleteMapping("cart/remove/{basketId}")
    public ResponseEntity<Object> deleteFromCart(@NotNull @PathVariable Long basketId){
         cartService.deleteFromCart(basketId);
         return new ResponseEntity<>("Basket Removed from Cart",HttpStatus.OK);
    }
}

