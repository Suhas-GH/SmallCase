package com.example.POC2.Controller;


import com.example.POC2.Model.Stocks;
import com.example.POC2.Model.StocksMapping;
import com.example.POC2.Repository.StocksMappingRepository;
import com.example.POC2.Repository.StocksRepository;
import com.example.POC2.Service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class StocksController {

    @Autowired
    private StocksService stocksService;

    @PostMapping("/stocks/add")
    public ResponseEntity addStocks(@RequestBody Stocks stocks){
         return stocksService.addStocks(stocks);
    }

    @PutMapping("/stocks/modify/{StockId}")
    public ResponseEntity modifyStocks(@PathVariable Long StockId, @RequestBody Stocks stocks){
        return stocksService.modifyStocks(StockId,stocks);
    }

    @DeleteMapping("/stocks/delete/{StockId}")
    public ResponseEntity deleteStocks(@PathVariable Long StockId){
        return stocksService.deleteStocks(StockId);
    }

}
