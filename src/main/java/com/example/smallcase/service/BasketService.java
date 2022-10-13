package com.example.smallcase.service;

import com.example.smallcase.model.*;
import com.example.smallcase.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity addBaskets(@NotNull Baskets baskets){
        if(baskets.getBasketName()!=null && baskets.getDescription()!=null && baskets.getStocksMappings()!=null){
            List<Long> ids = baskets.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                    .toList();
            int stocksCount = stocksRepository.checkStocks(ids);
            if(stocksCount == ids.stream().count() && ids.stream().count()!=0){
                basketsRepository.save(baskets);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity modifyBasket(Long basketId, @NotNull Baskets baskets){
        if(baskets.getBasketName()!=null && baskets.getDescription()!=null && basketsRepository.existsById(basketId)){
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
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deleteBasket(Long basketId) {
        if(basketsRepository.existsById(basketId)){
            basketsRepository.deleteById(basketId);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
