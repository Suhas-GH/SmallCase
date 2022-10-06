package com.example.POC2.Controller;


import com.example.POC2.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PutMapping("cart/add/{BasketId}")
    public ResponseEntity addToCart(@PathVariable Long BasketId) {
         return cartService.addToCart(BasketId);
    }

    @DeleteMapping("cart/remove/{BasketId}")
    public ResponseEntity deleteFromCart(@PathVariable Long BasketId){
        return cartService.deleteFromCart(BasketId);
    }
}

