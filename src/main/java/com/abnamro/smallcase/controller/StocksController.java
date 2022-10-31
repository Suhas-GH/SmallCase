package com.abnamro.smallcase.controller;


import com.abnamro.smallcase.dto.StocksDTO;
import com.abnamro.smallcase.model.Stocks;
import com.abnamro.smallcase.service.StocksService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
public class StocksController {

    @Autowired
    private StocksService stocksService;

    @PostMapping("/stocks/add")
    public ResponseEntity<Object> addStocks(@Valid @RequestBody StocksDTO stocksDTO){
        ModelMapper modelMapper = new ModelMapper();
        Stocks stocks = modelMapper.map(stocksDTO,Stocks.class);
        stocksService.addStocks(stocks);
        return new ResponseEntity<>("Stock Added Successfully", HttpStatus.CREATED);
    }

    @PutMapping("/stocks/modify/{stockId}")
    public ResponseEntity<Object> modifyStocks(@NotNull @PathVariable Long stockId, @Valid @RequestBody StocksDTO stocksDTO){
        ModelMapper modelMapper = new ModelMapper();
        Stocks stocks = modelMapper.map(stocksDTO,Stocks.class);
        stocksService.modifyStocks(stockId,stocks);
        return new ResponseEntity<>("Stock Modified Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/stocks/delete/{stockId}")
    public ResponseEntity<Object> deleteStocks(@NotNull @PathVariable Long stockId){
        stocksService.deleteStocks(stockId);
        return new ResponseEntity<>("Stock Deleted Successfully",HttpStatus.OK);
    }

}
