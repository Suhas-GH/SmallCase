package com.abnamro.smallcase.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Baskets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BasketId")
    private Long basketId;

    @Column(name = "BasketName")
    @NotBlank(message = "Basket Name cannot be Null")
    private String basketName;

    @Column(name = "Description")
    private String description;

    @JsonManagedReference(value = "basket-mapping")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "BasketId")
    private Set<StocksMapping> stocksMappings = new HashSet<>();


    @JsonManagedReference(value = "basket-cart-mapping")
    @OneToMany
    @JoinColumn(name = "BasketId")
    private List<CartMapping> cartMappings = new ArrayList<>();

    public Baskets() {
    }

    public Baskets(Long basketId, String basketName, String description, Set<StocksMapping> stocksMappings, List<CartMapping> cartMappings) {
        this.basketId = basketId;
        this.basketName = basketName;
        this.description = description;
        this.stocksMappings = stocksMappings;
        this.cartMappings = cartMappings;
    }

    public Baskets(Long basketId) {
        this.basketId = basketId;
    }

    public Long getBasketId() {
        return basketId;
    }

    public void setBasketId(Long basketId) {
        this.basketId = basketId;
    }

    public String getBasketName() {
        return basketName;
    }

    public void setBasketName(String basketName) {
        this.basketName = basketName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<StocksMapping> getStocksMappings() {
        return stocksMappings;
    }

    public void setStocksMappings(Set<StocksMapping> stocksMappings) {
        this.stocksMappings = stocksMappings;
    }

    public List<CartMapping> getCartMappings() {
        return cartMappings;
    }

    public void setCartMappings(List<CartMapping> cartMappings) {
        this.cartMappings = cartMappings;
    }

    @Override
    public String toString() {
        return "Baskets{" +
                "BasketId=" + basketId +
                ", BasketName='" + basketName + '\'' +
                ", Description='" + description + '\'' +
                ", stocksMappings=" + stocksMappings +
                ", cartMappings=" + cartMappings +
                '}';
    }

}
