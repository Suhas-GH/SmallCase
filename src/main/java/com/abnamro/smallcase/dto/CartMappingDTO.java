package com.abnamro.smallcase.dto;

import com.abnamro.smallcase.model.Cart;
import com.abnamro.smallcase.model.Baskets;

public class CartMappingDTO {
    private Long cartMappingId;
    private Baskets baskets;
    private Cart cart;

    public Long getCartMappingId() {
        return cartMappingId;
    }

    public void setCartMappingId(Long cartMappingId) {
        this.cartMappingId = cartMappingId;
    }

    public Baskets getBaskets() {
        return baskets;
    }

    public void setBaskets(Baskets baskets) {
        this.baskets = baskets;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
