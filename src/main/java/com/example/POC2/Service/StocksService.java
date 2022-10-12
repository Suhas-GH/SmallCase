package com.example.POC2.Service;

import com.example.POC2.Model.*;
import com.example.POC2.Repository.*;
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

    public ResponseEntity modifyStocks(Long StockId, @NotNull Stocks stocks){
        if(StockId!=null && stocks.getStockName()!=null && stocks.getStockPrice()!=null &&
                stocksRepository.existsById(StockId)){
            stocksRepository.modifyStocks(stocks.getStockName(),stocks.getStockPrice(),StockId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deleteStocks(Long StockId){
        if(stocksRepository.existsById(StockId)){
            Long count = stocksMappingRepository.stocksExisting(StockId);
            if(count==Long.valueOf(0)){
                stocksRepository.deleteById(StockId);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public List<Stocks> getStocksByMapping(List<Long> MappingId){
        List<Long> stockIds = new ArrayList<>();
        MappingId.forEach(x->{
            stockIds.add(stocksMappingRepository.getStockId(x));
        });
        List<Stocks> stocksList = new ArrayList<>();
        stockIds.forEach(x->{
            stocksList.add(stocksRepository.findById(x).orElseThrow(() -> new IllegalArgumentException("Invalid Mapping Id:" + x)));
        });
        return stocksList;
    }

    public Float getInvestmentAmount(List<Stocks> Stocks, Long BasketId){
        Float InvestmentAmount = 0F;
        for (Stocks stock : Stocks){
            Set<StocksMapping> mappings = stock.getStocksMapping();
            for (StocksMapping stocksMapping : mappings){
                if (stocksMapping.getBaskets().getBasketId()==BasketId){
                    InvestmentAmount = InvestmentAmount + (stock.getStockPrice()*stocksMapping.getQTY());
                }
            }
        }
        return InvestmentAmount;
    }
}
