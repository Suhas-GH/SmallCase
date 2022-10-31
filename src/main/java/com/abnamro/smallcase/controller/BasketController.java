package com.abnamro.smallcase.controller;

import com.abnamro.smallcase.dto.BasketsDTO;
import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.service.BasketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;


@RestController
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping("/baskets/add")
    public ResponseEntity<Object> addBasket(@Valid @RequestBody BasketsDTO basketsDTO){
        ModelMapper modelMapper = new ModelMapper();
        Baskets baskets = modelMapper.map(basketsDTO,Baskets.class);
        basketService.addBaskets(baskets);
        return new ResponseEntity<>("Basket Created Successfully",HttpStatus.CREATED);
    }

    @PutMapping("/baskets/modify/{basketId}")
    public ResponseEntity<Object> modifyBaskets(@NotNull @PathVariable Long basketId, @Valid @RequestBody BasketsDTO basketsDTO){
        ModelMapper modelMapper = new ModelMapper();
        Baskets baskets = modelMapper.map(basketsDTO,Baskets.class);
        basketService.modifyBasket(basketId,baskets);
        return new ResponseEntity<>("Basket Modified Successfully",HttpStatus.OK);
    }

    @DeleteMapping("/baskets/delete/{basketId}")
    public ResponseEntity<Object> deleteBasket(@Valid @PathVariable Long basketId){
         basketService.deleteBasket(basketId);
         return new ResponseEntity<>("Basket Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/getBaskets")
    public ResponseEntity<List<Baskets>> getBaskets(){
        return new ResponseEntity<>(basketService.getBaskets(),HttpStatus.OK);
    }

    @GetMapping("/{basketId}")
    public ResponseEntity<Optional<Baskets>> getBasketDetails(@NotNull @PathVariable Long basketId){
        return new ResponseEntity<>(basketService.getBasketDetails(basketId),HttpStatus.OK);
    }

}
