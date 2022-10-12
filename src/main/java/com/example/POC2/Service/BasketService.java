package com.example.POC2.Service;

import com.example.POC2.Model.*;
import com.example.POC2.Repository.*;
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

    public ResponseEntity addBaskets(@NotNull Baskets baskets){
        if(baskets.getBasketName()!=null && baskets.getDescription()!=null && baskets.getStocksMappings()!=null){
            List<Long> Ids = baskets.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                    .collect(Collectors.toList());
            int stocksCount = stocksRepository.checkStocks(Ids);
            if(stocksCount == Ids.stream().count() && Ids.stream().count()!=0){
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

    public ResponseEntity modifyBasket(Long BasketId, @NotNull Baskets baskets){
        if(baskets.getBasketName()!=null && baskets.getDescription()!=null && basketsRepository.existsById(BasketId)){
            List<Long> Ids = baskets.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                    .collect(Collectors.toList());
            int stocksCount = stocksRepository.checkStocks(Ids);
            stocksMappingRepository.deleteMappingByBasketId(BasketId);
            if(stocksCount == Ids.stream().count() && Ids.stream().count()!=0){
                basketsRepository.updateDetails(baskets.getBasketName(),baskets.getDescription(),BasketId);
                Set<StocksMapping> stocksMappingSet = baskets.getStocksMappings();
                stocksMappingSet.forEach(x -> {
                    stocksMappingRepository.saveMappings(x.getQTY(),x.getStocks().getStockId(),BasketId);
                });
            }else {
                basketsRepository.deleteById(BasketId);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deleteBasket(Long BasketId) {
        if(basketsRepository.existsById(BasketId)){
            basketsRepository.deleteById(BasketId);
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
        List<Long> Ids = new ArrayList<>();
        Set<StocksMapping> stocksMappingSet = basket.getStocksMappings();
        stocksMappingSet.forEach(x -> {
            Ids.add(x.getMappingId());
        });
        return Ids;
    }
}
