package com.abnamro.smallcase.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Stocks implements Comparable<Stocks>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StockId")
    private Long stockId;

    @Column(name = "StockName")
    @NotNull(message = "Stock Name cannot be Null")
    private String stockName;

    @Column(name = "StockPrice")
    @NotNull(message = "Stock Price cannot be Null")
    private Float stockPrice;

    @JsonManagedReference(value = "stocks-mapping")
    @OneToMany
    @JoinColumn(name = "StocksId")
    private Set<StocksMapping> stocksMapping = new HashSet<>();

    public Stocks() {
    }

    public Stocks(Long stockId, String stockName, Float stockPrice, Set<StocksMapping> stocksMapping) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.stocksMapping = stocksMapping;
    }

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

    public Set<StocksMapping> getStocksMapping() {
        return stocksMapping;
    }

    public void setStocksMapping(Set<StocksMapping> stocksMapping) {
        this.stocksMapping = stocksMapping;
    }

    @Override
    public String toString() {
        return "Stocks{" +
                "StockId=" + stockId +
                ", StockName='" + stockName + '\'' +
                ", StockPrice=" + stockPrice +
                ", stocksMapping=" + stocksMapping +
                '}';
    }

    @Override
    public int compareTo(@NotNull Stocks o) {
        return this.getStockId().compareTo(o.getStockId());
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
