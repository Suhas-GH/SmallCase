package com.example.POC2.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class StocksMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MappingId")
    private Long MappingId;

    @JsonBackReference(value = "stocks-mapping")
    @ManyToOne
    @JoinColumn(name = "StocksId")
    private Stocks stocks;

    @JsonBackReference(value = "basket-mapping")
    @ManyToOne
    @JoinColumn(name = "BasketId")
    private Baskets baskets;

    @Column(name = "QTY")
    private int QTY;

    public StocksMapping() {
    }

    public StocksMapping(Long mappingId, Stocks stocks, Baskets baskets, int QTY) {
        MappingId = mappingId;
        this.stocks = stocks;
        this.baskets = baskets;
        this.QTY = QTY;
    }

    public Long getMappingId() {
        return MappingId;
    }

    public void setMappingId(Long mappingId) {
        MappingId = mappingId;
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

    public int getQTY() {
        return QTY;
    }

    public void setQTY(int QTY) {
        this.QTY = QTY;
    }

}
