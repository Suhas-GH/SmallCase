package com.abnamro.smallcase.service;

import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.model.StocksMapping;
import com.abnamro.smallcase.repository.BasketsRepository;
import com.abnamro.smallcase.repository.StocksMappingRepository;
import com.abnamro.smallcase.repository.StocksRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BasketService {
    @Autowired
    private BasketsRepository basketsRepository;
    @Autowired
    private StocksRepository stocksRepository;
    @Autowired
    private StocksMappingRepository stocksMappingRepository;

    public void addBaskets(@NotNull Baskets baskets){
        System.out.println("service start");
        if(baskets.getBasketName()!=null && baskets.getDescription()!=null && baskets.getStocksMappings()!=null){
            List<Long> ids = baskets.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                    .toList();
            int stocksCount = stocksRepository.checkStocks(ids);
            if(stocksCount == ids.stream().count() && ids.stream().count()!=0){
                basketsRepository.save(baskets);
            }
        }
        System.out.println("service end");
    }

    public void modifyBasket(Long basketId, @NotNull Baskets baskets){
        if(baskets.getBasketName()!=null && baskets.getDescription()!=null && basketsRepository.existsById(basketId)){
            List<Long> ids = baskets.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                    .toList();
            int stocksCount = stocksRepository.checkStocks(ids);
            System.out.println("stocksCount "+stocksCount);
            System.out.println("count "+ids.stream().count());
            stocksMappingRepository.deleteMappingByBasketId(basketId);
            if(stocksCount == ids.stream().count() && ids.stream().count()!=0){
                basketsRepository.updateDetails(baskets.getBasketName(),baskets.getDescription(),basketId);
                Set<StocksMapping> stocksMappingSet = baskets.getStocksMappings();
                stocksMappingSet.forEach(x ->
                        stocksMappingRepository.saveMappings(x.getQty(),x.getStocks().getStockId(),basketId));
            }else {
                basketsRepository.deleteById(basketId);
            }
        }
    }

    public void deleteBasket(Long basketId) {
        if(basketsRepository.existsById(basketId)){
            basketsRepository.deleteById(basketId);
        }
    }

    public List<Baskets> getBaskets(){
        return basketsRepository.findAll();
    }

    public Optional<Baskets> getBasketDetails(Long basketId){
        //System.out.println("start");
        return basketsRepository.findById(basketId);
    }

    public List<Long> getMappingIds(Baskets basket){
        List<Long> ids = new ArrayList<>();
        Set<StocksMapping> stocksMappingSet = basket.getStocksMappings();
        stocksMappingSet.forEach(x -> ids.add(x.getMappingId()));
        return ids;
    }
}
