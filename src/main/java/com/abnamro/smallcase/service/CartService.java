package com.abnamro.smallcase.service;

import com.abnamro.smallcase.model.ApplicationUser;
import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.model.Cart;
import com.abnamro.smallcase.model.CartMapping;
import com.abnamro.smallcase.repository.ApplicationUserRepository;
import com.abnamro.smallcase.repository.BasketsRepository;
import com.abnamro.smallcase.repository.CartMappingRepository;
import com.abnamro.smallcase.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);


    public void addToCart(Long basketId, Long userId){
        Baskets baskets = new Baskets(basketId);
        if(cartRepository.existsByUserId(userId)){
            Cart cart = cartRepository.findByUserId(userId);
            Long count = cartMappingRepository.existsByCartIdAndBasketId(cart.getCartId(), basketId);
            if(count==0){
                CartMapping cartMapping = new CartMapping(baskets,cart);
                cartMappingRepository.save(cartMapping);
            }
        }else {
            CartMapping cartMapping = new CartMapping(baskets);
            List<CartMapping> cartMappingList = new ArrayList<>();
            cartMappingList.add(cartMapping);
            Cart cart = new Cart(userId,cartMappingList);
            cartRepository.save(cart);
        }
        LOGGER.info("Basket Added To Cart");
    }

    public void deleteFromCart(Long basketId, Long userId){
        Cart cart = cartRepository.findByUserId(userId);
        Long count = cartMappingRepository.cartCount(cart.getCartId());
        if (count.equals(1L)){
            cartRepository.deleteById(cart.getCartId());
        }else {
            cartMappingRepository.deleteBasketFromCart(basketId,cart.getCartId());
        }
        LOGGER.info("Basket Removed From Cart");
    }

    public List<Baskets> getUsersCart(Long userId){
        Long cartId = cartRepository.findByUserId(userId).getCartId();
        List<Long> basketIds = cartMappingRepository.findAllByCartCartId(cartId)
                .stream().map(x->x.getBaskets().getBasketId())
                .toList();
        return basketIds.stream().map(x->basketsRepository.findById(x).orElseThrow(() -> new IllegalArgumentException("Invalid Basket Id:" + x))).toList();
    }
}


