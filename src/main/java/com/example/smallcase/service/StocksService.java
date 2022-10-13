package com.example.smallcase.service;

import com.example.smallcase.model.*;
import com.example.smallcase.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity addStocks(@NotNull Stocks stocks){
        if(stocks.getStockName()!=null && stocks.getStockPrice()!=null){
            stocksRepository.save(stocks);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity modifyStocks(Long stockId, @NotNull Stocks stocks){
        if(stockId!=null && stocks.getStockName()!=null && stocks.getStockPrice()!=null &&
                stocksRepository.existsById(stockId)){
            stocksRepository.modifyStocks(stocks.getStockName(),stocks.getStockPrice(),stockId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deleteStocks(Long stockId){
        if(stocksRepository.existsById(stockId)){
            Long count = stocksMappingRepository.stocksExisting(stockId);
            if(count.equals(Long.valueOf(0))){
                stocksRepository.deleteById(stockId);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        Float investmentAmount = 0F;
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
}
