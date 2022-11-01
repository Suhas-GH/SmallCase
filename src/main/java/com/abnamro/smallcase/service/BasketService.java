package com.abnamro.smallcase.service;

import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.model.StocksMapping;
import com.abnamro.smallcase.repository.BasketsRepository;
import com.abnamro.smallcase.repository.StocksMappingRepository;
import com.abnamro.smallcase.repository.StocksRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class BasketService {
    @Autowired
    private BasketsRepository basketsRepository;
    @Autowired
    private StocksRepository stocksRepository;
    @Autowired
    private StocksMappingRepository stocksMappingRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketService.class);

    public void addBaskets(@NotNull Baskets baskets){
        List<Long> ids = baskets.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                .toList();
        int stocksCount = stocksRepository.checkStocks(ids);
        if(stocksCount == ids.stream().count() && ids.stream().count()!=0){
            LOGGER.info("Basket is created");
            basketsRepository.save(baskets);
        }
        else {
            LOGGER.error("Basket Already Exists");
        }
    }

    public void modifyBasket(Long basketId, @NotNull Baskets baskets){
        List<Long> ids = baskets.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                .toList();
        int stocksCount = stocksRepository.checkStocks(ids);
        stocksMappingRepository.deleteMappingByBasketId(basketId);
        if(stocksCount == ids.stream().count() && ids.stream().count()!=0){
            basketsRepository.updateDetails(baskets.getBasketName(),baskets.getDescription(),basketId);
            Set<StocksMapping> stocksMappingSet = baskets.getStocksMappings();
            stocksMappingSet.forEach(x ->
                    stocksMappingRepository.saveMappings(x.getQty(),x.getStocks().getStockId(),basketId));
        }else {
            basketsRepository.deleteById(basketId);
        }
        LOGGER.info("Basket Updated");
    }

    public void deleteBasket(Long basketId) {
        LOGGER.info("Basket Deleted");
        basketsRepository.deleteById(basketId);
    }

    public List<Baskets> getBaskets(){
        return basketsRepository.findAll();
    }

    public Optional<Baskets> getBasketDetails(Long basketId){
        return basketsRepository.findById(basketId);
    }

    public List<Long> getMappingIds(Baskets basket){
        List<Long> ids = new ArrayList<>();
        Set<StocksMapping> stocksMappingSet = basket.getStocksMappings();
        stocksMappingSet.forEach(x -> ids.add(x.getMappingId()));
        return ids;
    }
}
