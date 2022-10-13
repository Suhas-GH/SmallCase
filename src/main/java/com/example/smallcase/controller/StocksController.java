package com.example.smallcase.controller;


import com.example.smallcase.model.Stocks;
import com.example.smallcase.service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class StocksController {

    @Autowired
    private StocksService stocksService;

    @PostMapping("/stocks/add")
    public ResponseEntity addStocks(@RequestBody Stocks stocks){
         return stocksService.addStocks(stocks);
    }

    @PutMapping("/stocks/modify/{stockId}")
    public ResponseEntity modifyStocks(@PathVariable Long stockId, @RequestBody Stocks stocks){
        return stocksService.modifyStocks(stockId,stocks);
    }

    @DeleteMapping("/stocks/delete/{stockId}")
    public ResponseEntity deleteStocks(@PathVariable Long stockId){
        return stocksService.deleteStocks(stockId);
    }

}
