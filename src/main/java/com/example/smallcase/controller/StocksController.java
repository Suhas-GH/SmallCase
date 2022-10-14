package com.example.smallcase.controller;


import com.example.smallcase.dto.StocksDTO;
import com.example.smallcase.model.Stocks;
import com.example.smallcase.service.StocksService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

}
