package com.example.smallcase.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class StocksMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MappingId")
    private Long mappingId;

    @JsonBackReference(value = "stocks-mapping")
    @ManyToOne
    @JoinColumn(name = "StocksId")
    private Stocks stocks;

    @JsonBackReference(value = "basket-mapping")
    @ManyToOne
    @JoinColumn(name = "BasketId")
    private Baskets baskets;

    @Column(name = "QTY")
    private int qty;

    public StocksMapping() {
    }

    public StocksMapping(Long mappingId, Stocks stocks, Baskets baskets, int qty) {
        this.mappingId = mappingId;
        this.stocks = stocks;
        this.baskets = baskets;
        this.qty = qty;
    }

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
