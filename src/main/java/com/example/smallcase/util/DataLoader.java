package com.example.smallcase.util;

import com.example.smallcase.model.Baskets;
import com.example.smallcase.model.CartMapping;
import com.example.smallcase.model.Stocks;
import com.example.smallcase.model.StocksMapping;
import com.example.smallcase.repository.BasketsRepository;
import com.example.smallcase.repository.StocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private StocksRepository stocksRepository;

    @Autowired
    private BasketsRepository basketsRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        saveStocks("Tata Consultancy Services", (float) 34.39);
        saveStocks("Infosys", (float) 42.72);
        saveStocks("Wipro", (float) 31.65);
        saveStocks("Suzuki", (float) 21.94);
        saveStocks("BMW", (float) 39.68);
        saveStocks("Tata Motors", (float) 29.39);

        Baskets basket1 = new Baskets();
        basket1.setBasketName("House Of TATA");
        basket1.setDescription("TATA Shares");
        Set<StocksMapping> mappingSet1 = new HashSet<>();
        mappingSet1.add(setStockMappings(15,1L));
        mappingSet1.add(setStockMappings(10,6L));
        basket1.setStocksMappings(mappingSet1);
        basketsRepository.save(basket1);

        Baskets basket2 = new Baskets();
        basket2.setBasketName("IT Industry");
        basket2.setDescription("IT Shares");
        Set<StocksMapping> mappingSet2 = new HashSet<>();
        mappingSet2.add(setStockMappings(8,1L));
        mappingSet2.add(setStockMappings(6,2L));
        mappingSet2.add(setStockMappings(4,3L));
        basket2.setStocksMappings(mappingSet2);
        basketsRepository.save(basket2);

        Baskets basket3 = new Baskets();
        basket3.setBasketName("Automobile");
        basket3.setDescription("Vehicle Manufactures");
        Set<StocksMapping> mappingSet3 = new HashSet<>();
        mappingSet3.add(setStockMappings(8,4L));
        mappingSet3.add(setStockMappings(6,5L));
        basket3.setStocksMappings(mappingSet3);
        basketsRepository.save(basket3);
    }

    public StocksMapping setStockMappings(int qty, Long id){
        StocksMapping stocksMappings = new StocksMapping();
        stocksMappings.setQty(qty);
        Stocks stocks = new Stocks();
        stocks.setStockId(id);
        stocksMappings.setStocks(stocks);
        return stocksMappings;
    }

    public void saveStocks(String stocksName, Float stockPrice){
        Stocks stocks = new Stocks();
        stocks.setStockName(stocksName);
        stocks.setStockPrice(stockPrice);
        stocksRepository.save(stocks);
    }
}
