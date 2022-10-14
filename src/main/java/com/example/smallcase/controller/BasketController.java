package com.example.smallcase.controller;

import com.example.smallcase.dto.BasketsDTO;
import com.example.smallcase.model.Baskets;
import com.example.smallcase.service.BasketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping("/baskets/add")
    public void addBasket(@RequestBody BasketsDTO basketsDTO){
        ModelMapper modelMapper = new ModelMapper();
        Baskets baskets = modelMapper.map(basketsDTO,Baskets.class);
        basketService.addBaskets(baskets);
    }

    @PutMapping("/baskets/modify/{basketId}")
    public void modifyBaskets(@PathVariable Long basketId, @RequestBody BasketsDTO basketsDTO){
        ModelMapper modelMapper = new ModelMapper();
        Baskets baskets = modelMapper.map(basketsDTO,Baskets.class);
        basketService.modifyBasket(basketId,baskets);
    }

    @DeleteMapping("/baskets/delete/{basketId}")
    public void deleteBasket(@PathVariable Long basketId){
         basketService.deleteBasket(basketId);
    }

    @GetMapping("/getBaskets")
    public List<Baskets> getBaskets(){
        return basketService.getBaskets();
    }

    @GetMapping("/{basketId}")
    public Optional<Baskets> getBasketDetails(@PathVariable Long basketId){
        return basketService.getBasketDetails(basketId);
    }

}
