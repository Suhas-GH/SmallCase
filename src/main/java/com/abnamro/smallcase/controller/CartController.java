package com.abnamro.smallcase.controller;


import com.abnamro.smallcase.model.ApplicationUser;
import com.abnamro.smallcase.repository.ApplicationUserRepository;
import com.abnamro.smallcase.repository.BasketsRepository;
import com.abnamro.smallcase.repository.CartMappingRepository;
import com.abnamro.smallcase.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;


@RestController
public class CartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    private BasketsRepository basketsRepository;

    @Autowired
    private CartMappingRepository cartMappingRepository;

    @PutMapping("cart/add/{basketId}")
    public ResponseEntity<Object> addToCart(@NotNull @PathVariable Long basketId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        ApplicationUser user = userRepository.findByUserName(userName);
        if(user==null){
            throw new NoSuchElementException("User Not Found");
        } else {
            Long userId = user.getUserId();
            if(userId != null && basketId != null && basketsRepository.existsById(basketId)){
                cartService.addToCart(basketId, userId);
                return new ResponseEntity<>("Basket Added To Cart", HttpStatus.ACCEPTED);
            }
            else {
                throw new EmptyResultDataAccessException("Basket Not Found", 1);
            }
        }
    }

    @DeleteMapping("cart/remove/{basketId}")
    public ResponseEntity<Object> deleteFromCart(@NotNull @PathVariable Long basketId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        ApplicationUser user = userRepository.findByUserName(userName);
        if(user==null){
            throw new NoSuchElementException("User Not Found");
        } else {
            Long userId = user.getUserId();
            if(userId != null && basketId != null && cartMappingRepository.existsByBasketId(basketId)!=0){
                cartService.deleteFromCart(basketId,userId);
                return new ResponseEntity<>("Basket Removed from Cart",HttpStatus.OK);
            }
            else {
                throw new EmptyResultDataAccessException("Basket Not Available In Cart", 1);
            }
        }
    }
}

