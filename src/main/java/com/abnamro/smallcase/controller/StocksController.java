package com.abnamro.smallcase.controller;


import com.abnamro.smallcase.dto.StocksDTO;
import com.abnamro.smallcase.model.Stocks;
import com.abnamro.smallcase.service.StocksService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class StocksController {

    @Autowired
    private StocksService stocksService;

    @PostMapping("/stocks/add")
    public void addStocks(@RequestBody StocksDTO stocksDTO){
        ModelMapper modelMapper = new ModelMapper();
        Stocks stocks = modelMapper.map(stocksDTO,Stocks.class);
        stocksService.addStocks(stocks);
    }

    @PutMapping("/stocks/modify/{stockId}")
    public void modifyStocks(@PathVariable Long stockId, @RequestBody StocksDTO stocksDTO){
        ModelMapper modelMapper = new ModelMapper();
        Stocks stocks = modelMapper.map(stocksDTO,Stocks.class);
        stocksService.modifyStocks(stockId,stocks);
    }

    @DeleteMapping("/stocks/delete/{stockId}")
    public void deleteStocks(@PathVariable Long stockId){
        stocksService.deleteStocks(stockId);
    }

//    @GetMapping("/stocks")
//    public List<Stocks> getStocks(){
//        return stocksService.getStocks();
//    }

}
