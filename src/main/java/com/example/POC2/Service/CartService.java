package com.example.POC2.Service;

import com.example.POC2.Model.*;
import com.example.POC2.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartService {
    @Autowired
    private BasketsRepository basketsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartMappingRepository cartMappingRepository;

    @Autowired
    private ApplicationUserRepository userRepository;


    public ResponseEntity addToCart(Long BasketId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName = authentication.getName();
        ApplicationUser user = userRepository.findByUserName(UserName);
        Long UserId = user.getUserId();
        //Long UserId = Long.valueOf(1);
        if(UserId!=null && BasketId!=null && basketsRepository.existsById(BasketId)){
            Baskets baskets = new Baskets(BasketId);
            if(cartRepository.existsByUserId(UserId)){
                Cart cart = cartRepository.findByUserId(UserId);
                Long count = cartMappingRepository.existsByCartIdAndBasketId(cart.getCartId(), BasketId);
                if(count==0){
                    CartMapping cartMapping = new CartMapping(baskets,cart);
                    cartMappingRepository.save(cartMapping);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }else {
                CartMapping cartMapping = new CartMapping(baskets);
                List<CartMapping> cartMappingList = new ArrayList<>();
                cartMappingList.add(cartMapping);
                Cart cart = new Cart(UserId,cartMappingList);
                cartRepository.save(cart);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deleteFromCart(Long BasketId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName = authentication.getName();
        ApplicationUser user = userRepository.findByUserName(UserName);
        Long UserId = user.getUserId();
        //Long UserId = Long.valueOf(1);
        if(UserId!=null && BasketId!=null && cartMappingRepository.existsByBasketId(BasketId)!=0){
            Cart cart = cartRepository.findByUserId(UserId);
            Long count = cartMappingRepository.cartCount(cart.getCartId());
            if (count==Long.valueOf(1)){
                cartRepository.deleteById(cart.getCartId());
            }else {
                cartMappingRepository.deleteBasketFromCart(BasketId,cart.getCartId());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}


