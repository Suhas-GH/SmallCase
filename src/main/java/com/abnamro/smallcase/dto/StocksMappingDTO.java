package com.abnamro.smallcase.dto;

import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.model.Stocks;

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
