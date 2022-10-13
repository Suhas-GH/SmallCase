package com.example.smallcase.controller;


import com.example.smallcase.model.Stocks;
import com.example.smallcase.service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class StocksController {

    @Autowired
    private StocksService stocksService;

    @PostMapping("/stocks/add")
    public void addStocks(@RequestBody Stocks stocks){
         stocksService.addStocks(stocks);
    }

    @PutMapping("/stocks/modify/{stockId}")
    public void modifyStocks(@PathVariable Long stockId, @RequestBody Stocks stocks){
        stocksService.modifyStocks(stockId,stocks);
    }

    @DeleteMapping("/stocks/delete/{stockId}")
    public void deleteStocks(@PathVariable Long stockId){
        stocksService.deleteStocks(stockId);
    }

}
