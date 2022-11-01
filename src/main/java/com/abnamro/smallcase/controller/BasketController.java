package com.abnamro.smallcase.controller;

import com.abnamro.smallcase.dto.BasketsDTO;
import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.repository.BasketsRepository;
import com.abnamro.smallcase.service.BasketService;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
public class BasketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketController.class);

    @Autowired
    private BasketService basketService;

    @Autowired
    private BasketsRepository basketsRepository;

    @PostMapping("/baskets/add")
    public ResponseEntity<Object> addBasket(@Valid @RequestBody BasketsDTO basketsDTO){
        ModelMapper modelMapper = new ModelMapper();
        Baskets baskets = modelMapper.map(basketsDTO,Baskets.class);
        if(baskets.getBasketName()==null){
            throw new NoSuchElementException("Basket Name is Null");
        } else if(baskets.getDescription()==null){
            throw new NoSuchElementException("Basket Description is Null");
        } else if(baskets.getStocksMappings()==null){
            throw new NoSuchElementException("Basket Stocks Mapping is Null");
        } else {
            basketService.addBaskets(baskets);
            return new ResponseEntity<>("Basket Created Successfully",HttpStatus.CREATED);
        }
    }

    @PutMapping("/baskets/modify/{basketId}")
    public ResponseEntity<Object> modifyBaskets(@NotNull @PathVariable Long basketId, @Valid @RequestBody BasketsDTO basketsDTO){
        ModelMapper modelMapper = new ModelMapper();
        Baskets baskets = modelMapper.map(basketsDTO,Baskets.class);
        if(baskets.getBasketName()==null){
            throw new NoSuchElementException("Basket Name is Null");
        } else if(baskets.getDescription()==null){
            throw new NoSuchElementException("Basket Description is Null");
        } else if(!basketsRepository.existsById(basketId)){
            throw new EmptyResultDataAccessException("Basket Do Not Exist", 1);
        } else {
            basketService.modifyBasket(basketId,baskets);
            return new ResponseEntity<>("Basket Modified Successfully",HttpStatus.OK);
        }
    }

    @DeleteMapping("/baskets/delete/{basketId}")
    public ResponseEntity<Object> deleteBasket(@Valid @PathVariable Long basketId){
        if(basketsRepository.existsById(basketId)){
            basketService.deleteBasket(basketId);
            return new ResponseEntity<>("Basket Deleted Successfully", HttpStatus.OK);
        }
        else {
            throw new EmptyResultDataAccessException("Basket Do Not Exist", 1);
        }
    }

    @GetMapping("/getBaskets")
    public ResponseEntity<List<Baskets>> getBaskets(){
        return new ResponseEntity<>(basketService.getBaskets(),HttpStatus.OK);
    }

    @GetMapping("/{basketId}")
    public ResponseEntity<Optional<Baskets>> getBasketDetails(@NotNull @PathVariable Long basketId){
        if(basketsRepository.existsById(basketId)){
            return new ResponseEntity<>(basketService.getBasketDetails(basketId),HttpStatus.OK);
        }
        else {
            throw new EmptyResultDataAccessException("Basket Do Not Exist",1);
        }
    }

}
