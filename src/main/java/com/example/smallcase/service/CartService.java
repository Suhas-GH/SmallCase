package com.example.smallcase.service;

import com.example.smallcase.model.*;
import com.example.smallcase.repository.*;
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


    public ResponseEntity addToCart(Long basketId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        ApplicationUser user = userRepository.findByUserName(userName);
        Long userId = user.getUserId();
        if(userId!=null && basketId!=null && basketsRepository.existsById(basketId)){
            Baskets baskets = new Baskets(basketId);
            if(cartRepository.existsByUserId(userId)){
                Cart cart = cartRepository.findByUserId(userId);
                Long count = cartMappingRepository.existsByCartIdAndBasketId(cart.getCartId(), basketId);
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
                Cart cart = new Cart(userId,cartMappingList);
                cartRepository.save(cart);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deleteFromCart(Long basketId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        ApplicationUser user = userRepository.findByUserName(userName);
        Long userId = user.getUserId();
        if(userId!=null && basketId!=null && cartMappingRepository.existsByBasketId(basketId)!=0){
            Cart cart = cartRepository.findByUserId(userId);
            Long count = cartMappingRepository.cartCount(cart.getCartId());
            if (count.equals(Long.valueOf(1))){
                cartRepository.deleteById(cart.getCartId());
            }else {
                cartMappingRepository.deleteBasketFromCart(basketId,cart.getCartId());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public List<Baskets> getUsersCart(Long userId){
        Long cartId = cartRepository.findByUserId(userId).getCartId();
        List<Long> basketIds = cartMappingRepository.findAllByCartCartId(cartId)
                .stream().map(x->x.getBaskets().getBasketId())
                .toList();
        return basketIds.stream().map(x->basketsRepository.findById(x).orElseThrow(() -> new IllegalArgumentException("Invalid Basket Id:" + x))).toList();
    }
}


