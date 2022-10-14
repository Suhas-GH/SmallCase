package com.example.smallcase.dto;

import com.example.smallcase.model.Baskets;
import com.example.smallcase.model.Stocks;

public class StocksMappingDTO {
    private Long mappingId;
    private Stocks stocks;
    private Baskets baskets;
    private int qty;

    public Long getMappingId() {
        return mappingId;
    }

    public void setMappingId(Long mappingId) {
        this.mappingId = mappingId;
    }

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    public Baskets getBaskets() {
        return baskets;
    }

    public void setBaskets(Baskets baskets) {
        this.baskets = baskets;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
