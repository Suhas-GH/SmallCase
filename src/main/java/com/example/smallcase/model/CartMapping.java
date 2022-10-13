package com.example.smallcase.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class CartMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartMappingId;

    @JsonBackReference(value = "basket-cart-mapping")
    @ManyToOne
    @JoinColumn(name = "BasketId")
    private Baskets baskets;

    @JsonBackReference(value = "cart-mapping")
    @ManyToOne
    @JoinColumn(name = "CartId")
    private Cart cart;

    public CartMapping() {
    }

    public CartMapping(Long cartMappingId, Baskets baskets, Cart cart) {
        this.cartMappingId = cartMappingId;
        this.baskets = baskets;
        this.cart = cart;
    }

    public CartMapping(Baskets baskets) {
        this.baskets = baskets;
    }

    public CartMapping(Baskets baskets, Cart cart) {
        this.baskets = baskets;
        this.cart = cart;
    }

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

    @Override
    public String toString() {
        return "CartMapping{" +
                "CartMappingId=" + cartMappingId +
                ", baskets=" + baskets +
                ", cart=" + cart +
                '}';
    }
}
