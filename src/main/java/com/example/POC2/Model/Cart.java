package com.example.POC2.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartId")
    private Long CartId;

    @Column(name = "userId")
    private Long userId;

    @JsonManagedReference(value = "cart-mapping")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CartId")
    private List<CartMapping> cartMappings = new ArrayList<>();

    public Cart() {
    }

    public Cart(Long cartId, Long userId, List<CartMapping> cartMappings) {
        CartId = cartId;
        this.userId = userId;
        this.cartMappings = cartMappings;
    }

    public Cart(Long userId, List<CartMapping> cartMappings) {
        this.userId = userId;
        this.cartMappings = cartMappings;
    }

    public Long getCartId() {
        return CartId;
    }

    public void setCartId(Long cartId) {
        CartId = cartId;
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

    @Override
    public String toString() {
        return "Cart{" +
                "CartId=" + CartId +
                ", userId=" + userId +
                ", cartMappings=" + cartMappings +
                '}';
    }
}
