package com.example.POC2.Model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Baskets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BasketId")
    private Long BasketId;

    @Column(name = "BasketName")
    private String BasketName;

    @Column(name = "Description")
    private String Description;

    @JsonManagedReference(value = "basket-mapping")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "BasketId")
    private Set<StocksMapping> stocksMappings = new HashSet<StocksMapping>();


    @JsonManagedReference(value = "basket-cart-mapping")
    @OneToMany
    @JoinColumn(name = "BasketId")
    private List<CartMapping> cartMappings = new ArrayList<CartMapping>();

    public Baskets() {
    }

    public Baskets(Long basketId, String basketName, String description, Set<StocksMapping> stocksMappings, List<CartMapping> cartMappings) {
        BasketId = basketId;
        BasketName = basketName;
        Description = description;
        this.stocksMappings = stocksMappings;
        this.cartMappings = cartMappings;
    }

    public Baskets(Long basketId) {
        BasketId = basketId;
    }

    public Long getBasketId() {
        return BasketId;
    }

    public void setBasketId(Long basketId) {
        BasketId = basketId;
    }

    public String getBasketName() {
        return BasketName;
    }

    public void setBasketName(String basketName) {
        BasketName = basketName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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
                "BasketId=" + BasketId +
                ", BasketName='" + BasketName + '\'' +
                ", Description='" + Description + '\'' +
                ", stocksMappings=" + stocksMappings +
                ", cartMappings=" + cartMappings +
                '}';
    }

}
