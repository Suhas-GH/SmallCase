package com.example.smallcase.dto;

import com.example.smallcase.model.CartMapping;
import com.example.smallcase.model.StocksMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasketsDTO {
    private Long basketId;
    private String basketName;
    private String description;
    private Set<StocksMapping> stocksMappings = new HashSet<>();
    private List<CartMapping> cartMappings = new ArrayList<>();

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
}
