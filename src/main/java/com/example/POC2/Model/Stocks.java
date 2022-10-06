package com.example.POC2.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Stocks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StockId")
    private Long StockId;

    @Column(name = "StockName")
    private String StockName;

    @Column(name = "StockPrice")
    private Float StockPrice;

    @JsonManagedReference(value = "stocks-mapping")
    @OneToMany
    @JoinColumn(name = "StocksId")
    private Set<StocksMapping> stocksMapping = new HashSet<StocksMapping>();

    public Stocks() {
    }

    public Stocks(Long stockId, String stockName, Float stockPrice, Set<StocksMapping> stocksMapping) {
        StockId = stockId;
        StockName = stockName;
        StockPrice = stockPrice;
        this.stocksMapping = stocksMapping;
    }

    public Long getStockId() {
        return StockId;
    }

    public void setStockId(Long stockId) {
        StockId = stockId;
    }

    public String getStockName() {
        return StockName;
    }

    public void setStockName(String stockName) {
        StockName = stockName;
    }

    public Float getStockPrice() {
        return StockPrice;
    }

    public void setStockPrice(Float stockPrice) {
        StockPrice = stockPrice;
    }

    public Set<StocksMapping> getStocksMapping() {
        return stocksMapping;
    }

    public void setStocksMapping(Set<StocksMapping> stocksMapping) {
        this.stocksMapping = stocksMapping;
    }

}
