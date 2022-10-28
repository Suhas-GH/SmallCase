package com.abnamro.smallcase.dto;

import com.abnamro.smallcase.model.StocksMapping;

import java.util.HashSet;
import java.util.Set;

public class StocksDTO {
    private Long stockId;
    private String stockName;
    private Float stockPrice;
    private Set<StocksMapping> stocksMappings = new HashSet<>();

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Float getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(Float stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Set<StocksMapping> getStocksMappings() {
        return stocksMappings;
    }

    public void setStocksMappings(Set<StocksMapping> stocksMappings) {
        this.stocksMappings = stocksMappings;
    }
}
