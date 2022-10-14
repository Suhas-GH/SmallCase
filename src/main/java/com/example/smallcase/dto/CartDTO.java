package com.example.smallcase.dto;

import com.example.smallcase.model.CartMapping;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {
    private Long cartId;
    private Long userId;
    private List<CartMapping> cartMappings = new ArrayList<>();

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartMapping> getCartMappings() {
        return cartMappings;
    }

    public void setCartMappings(List<CartMapping> cartMappings) {
        this.cartMappings = cartMappings;
    }
}
