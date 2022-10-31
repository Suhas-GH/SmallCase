package com.abnamro.smallcase.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartId")
    private Long cartId;

    @Column(name = "userId")
    @NotBlank(message = "User Id cannot be Null")
    private Long userId;

    @JsonManagedReference(value = "cart-mapping")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CartId")
    private List<CartMapping> cartMappings = new ArrayList<>();

    public Cart() {
    }

    public Cart(Long cartId, Long userId, List<CartMapping> cartMappings) {
        this.cartId = cartId;
        this.userId = userId;
        this.cartMappings = cartMappings;
    }

    public Cart(Long userId, List<CartMapping> cartMappings) {
        this.userId = userId;
        this.cartMappings = cartMappings;
    }

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

    @Override
    public String toString() {
        return "Cart{" +
                "CartId=" + cartId +
                ", userId=" + userId +
                ", cartMappings=" + cartMappings +
                '}';
    }
}
