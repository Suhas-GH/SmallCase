package com.abnamro.smallcase.service;

import com.abnamro.smallcase.model.Stocks;
import com.abnamro.smallcase.model.StocksMapping;
import com.abnamro.smallcase.repository.StocksMappingRepository;
import com.abnamro.smallcase.repository.StocksRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(StocksService.class);

    public void addStocks(@NotNull Stocks stocks){
        if(stocks.getStockName()==null){
            LOGGER.error("Stock Name is Null");
        }
        else if (stocks.getStockPrice()==null){
            LOGGER.error("Stock Price is Null");
        }
        else {
            LOGGER.info("Stock Added Successfully");
            stocksRepository.save(stocks);
        }
    }

    public void modifyStocks(Long stockId, @NotNull Stocks stocks){
        if(stockId==null){
            LOGGER.error("Stock Id is Null");
        }
        else if(stocks.getStockName()==null){
            LOGGER.error("Stock Name is Null");
        }
        else if(stocks.getStockPrice()==null){
            LOGGER.error("Stock Price is Null");
        }
        else if(!stocksRepository.existsById(stockId)){
            LOGGER.error("Stock Already Exists");
        }
        else {
            LOGGER.info("Stock Details Modified Successfully");
            stocksRepository.modifyStocks(stocks.getStockName(),stocks.getStockPrice(),stockId);
        }
    }

    public void deleteStocks(Long stockId){
        if(stocksRepository.existsById(stockId)){
            Long count = stocksMappingRepository.stocksExisting(stockId);
            if(count.equals(0L)){
                stocksRepository.deleteById(stockId);
                LOGGER.info("Stock Deleted Successfully");
            }
        }
        else {
            LOGGER.error("Stock do not exist");
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
}
