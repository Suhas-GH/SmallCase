/*
package com.example.POC2.Controller;

import com.example.POC2.Model.Baskets;
import com.example.POC2.Model.Cart;
import com.example.POC2.Model.CartMapping;
import com.example.POC2.Model.Stocks;
import com.example.POC2.Service.TestService;
import com.example.POC2.Test1Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/stocks/add")
    public ResponseEntity addStocks(@RequestBody Stocks stocks){
         return testService.addStocks(stocks);
    }

    @PutMapping("/stocks/modify/{StockId}")
    public ResponseEntity modifyStocks(@PathVariable Long StockId, @RequestBody Stocks stocks){
        return testService.modifyStocks(StockId,stocks);
    }

    @DeleteMapping("/stocks/delete/{StockId}")
    public ResponseEntity deleteStocks(@PathVariable Long StockId){
        return testService.deleteStocks(StockId);
    }

    @PostMapping("/baskets/add")
    public ResponseEntity addBasket(@RequestBody Baskets baskets){
        return testService.addBaskets(baskets);
    }

    @PutMapping("/baskets/modify/{BasketId}")
    public ResponseEntity modifyBaskets(@PathVariable Long BasketId,@RequestBody Baskets baskets){
        return testService.modifyBasket(BasketId,baskets);
    }

    @DeleteMapping("/baskets/delete/{BasketId}")
    public ResponseEntity deleteBasket(@PathVariable Long BasketId){
         return testService.deleteBasket(BasketId);
    }

    @GetMapping("/getBaskets")
    public List<Baskets> getBaskets(){
        return testService.getBaskets();
    }

    @GetMapping("/{BasketId}")
    public Optional<Baskets> getBasketDetails(@PathVariable Long BasketId){
        return testService.getBasketDetails(BasketId);
    }

    @PutMapping("cart/add/{BasketId}")
    public ResponseEntity addToCart(@PathVariable Long BasketId) {
         return testService.addToCart(BasketId);
    }

    @DeleteMapping("cart/remove/{BasketId}")
    public ResponseEntity deleteFromCart(@PathVariable Long BasketId){
        return testService.deleteFromCart(BasketId);
    }
}

 */
