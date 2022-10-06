/*
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
public class TestService {
    @Autowired
    private BasketsRepository basketsRepository;
    @Autowired
    private StocksRepository stocksRepository;
    @Autowired
    private StocksMappingRepository stocksMappingRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartMappingRepository cartMappingRepository;

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

    public ResponseEntity addBaskets(@NotNull Baskets baskets){
        if(baskets.getBasketName()!=null && baskets.getDescription()!=null && baskets.getStocksMappings()!=null){
            List<Long> Ids = baskets.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                    .collect(Collectors.toList());
            int stocksCount = stocksRepository.checkStocks(Ids);
            if(stocksCount == Ids.stream().count()){
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

    public ResponseEntity modifyBasket(Long BasketId,Baskets baskets){
        if(baskets.getBasketName()!=null && baskets.getDescription()!=null && basketsRepository.existsById(BasketId)){
            basketsRepository.updateDetails(baskets.getBasketName(),baskets.getDescription(),BasketId);
            stocksMappingRepository.deleteMappingByBasketId(BasketId);
            Set<StocksMapping> stocksMappingSet = baskets.getStocksMappings();
            stocksMappingSet.forEach(x -> {
                stocksMappingRepository.saveMappings(x.getQTY(),x.getStocks().getStockId(),BasketId);
            });
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

    public ResponseEntity addToCart(Long BasketId){
        Long UserId = Long.valueOf(1);
        if(UserId!=null && BasketId!=null && basketsRepository.existsById(BasketId)){
            Baskets baskets = new Baskets(BasketId);
            if(cartRepository.existsByUserId(UserId)){
                Cart cart = cartRepository.findByUserId(UserId);
                Long count = cartMappingRepository.existsByCartIdAndBasketId(cart.getCartId(), BasketId);
                if(count==0){
                    CartMapping cartMapping = new CartMapping(baskets,cart);
                    cartMappingRepository.save(cartMapping);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }else {
                CartMapping cartMapping = new CartMapping(baskets);
                List<CartMapping> cartMappingList = new ArrayList<>();
                cartMappingList.add(cartMapping);
                Cart cart = new Cart(UserId,cartMappingList);
                cartRepository.save(cart);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity deleteFromCart(Long BasketId){
        Long UserId = Long.valueOf(1);
        if(UserId!=null && BasketId!=null && cartMappingRepository.existsByBasketId(BasketId)!=0){
            Cart cart = cartRepository.findByUserId(UserId);
            Long count = cartMappingRepository.cartCount(cart.getCartId());
            if (count==Long.valueOf(1)){
                cartRepository.deleteById(cart.getCartId());
            }else {
                cartMappingRepository.deleteBasketFromCart(BasketId,cart.getCartId());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

 */

