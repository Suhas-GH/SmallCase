package com.abnamro.smallcase.service;

import com.abnamro.smallcase.model.Stocks;
import com.abnamro.smallcase.model.StocksMapping;
import com.abnamro.smallcase.repository.StocksMappingRepository;
import com.abnamro.smallcase.repository.StocksRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class StocksService {
    @Autowired
    private StocksRepository stocksRepository;
    @Autowired
    private StocksMappingRepository stocksMappingRepository;

    public void addStocks(@NotNull Stocks stocks){
        if(stocks.getStockName()!=null && stocks.getStockPrice()!=null){
            stocksRepository.save(stocks);
        }
    }

    public void modifyStocks(Long stockId, @NotNull Stocks stocks){
        if(stockId!=null && stocks.getStockName()!=null && stocks.getStockPrice()!=null &&
                stocksRepository.existsById(stockId)){
            stocksRepository.modifyStocks(stocks.getStockName(),stocks.getStockPrice(),stockId);
        }
    }

    public void deleteStocks(Long stockId){
        if(stocksRepository.existsById(stockId)){
            Long count = stocksMappingRepository.stocksExisting(stockId);
            //System.out.println(count);
            if(count.equals(0L)){
                stocksRepository.deleteById(stockId);
            }
        }
    }

    public List<Stocks> getStocksByMapping(List<Long> mappingId){
        List<Long> stockIds = new ArrayList<>();
        mappingId.forEach(x->stockIds.add(stocksMappingRepository.getStockId(x)));
        List<Stocks> stocksList = new ArrayList<>();
        stockIds.forEach(x->stocksList.add(stocksRepository.findById(x)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid Mapping Id:" + x))));
        return stocksList;
    }

    public Float getInvestmentAmount(List<Stocks> stocks, Long basketId){
        float investmentAmount = 0F;
        for (Stocks stock : stocks){
            Set<StocksMapping> mappings = stock.getStocksMapping();
            for (StocksMapping stocksMapping : mappings){
                if (stocksMapping.getBaskets().getBasketId().equals(basketId)){
                    investmentAmount = investmentAmount + (stock.getStockPrice()*stocksMapping.getQty());
                }
            }
        }
        return investmentAmount;
    }

//    public List<Stocks> getStocks() {
//        return stocksRepository.findAll();
//    }
}
