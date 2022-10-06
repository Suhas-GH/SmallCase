package com.example.POC2.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class CartMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CartMappingId;

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
        CartMappingId = cartMappingId;
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
        return CartMappingId;
    }

    public void setCartMappingId(Long cartMappingId) {
        CartMappingId = cartMappingId;
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
                "CartMappingId=" + CartMappingId +
                ", baskets=" + baskets +
                ", cart=" + cart +
                '}';
    }
}
