package com.abnamro.smallcase.controller;


import com.abnamro.smallcase.dto.StocksDTO;
import com.abnamro.smallcase.model.Stocks;
import com.abnamro.smallcase.repository.StocksRepository;
import com.abnamro.smallcase.service.StocksService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;


@RestController
public class StocksController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StocksController.class);

    @Autowired
    private StocksService stocksService;

    @Autowired
    private StocksRepository stocksRepository;

    @PostMapping("/stocks/add")
    public ResponseEntity<Object> addStocks(@Valid @RequestBody StocksDTO stocksDTO){
        ModelMapper modelMapper = new ModelMapper();
        Stocks stocks = modelMapper.map(stocksDTO,Stocks.class);
        if(stocks.getStockName()==null){
            throw new NoSuchElementException("Stock Name is Null");
        } else if(stocks.getStockPrice()==null){
            throw new NoSuchElementException("Stock Price is Null");
        } else {
            stocksService.addStocks(stocks);
            return new ResponseEntity<>("Stock Added Successfully", HttpStatus.CREATED);
        }
    }

    @PutMapping("/stocks/modify/{stockId}")
    public ResponseEntity<Object> modifyStocks(@NotNull @PathVariable Long stockId, @Valid @RequestBody StocksDTO stocksDTO){
        ModelMapper modelMapper = new ModelMapper();
        Stocks stocks = modelMapper.map(stocksDTO,Stocks.class);
        if (stockId==null){
            throw new NoSuchElementException("Stock Id is Null");
        } else if(stocks.getStockName()==null){
            throw new NoSuchElementException("Stock Name is Null");
        } else if(stocks.getStockPrice()==null){
            throw new NoSuchElementException("Stock Price is Null");
        } else if(stocksRepository.existsById(stockId)){
            stocksService.modifyStocks(stockId,stocks);
            return new ResponseEntity<>("Stock Modified Successfully", HttpStatus.OK);
        }
        else {
            throw new EntityExistsException("Stock Already Exists");
        }
    }

    @DeleteMapping("/stocks/delete/{stockId}")
    public ResponseEntity<Object> deleteStocks(@NotNull @PathVariable Long stockId){
        if(stocksRepository.existsById(stockId)){
            stocksService.deleteStocks(stockId);
            return new ResponseEntity<>("Stock Deleted Successfully",HttpStatus.OK);
        }
        else {
            throw new EmptyResultDataAccessException("Stock do not exist", 1);
        }
    }

}
