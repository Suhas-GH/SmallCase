package com.example.smallcase.controller;

import com.example.smallcase.model.Baskets;
import com.example.smallcase.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping("/baskets/add")
    public ResponseEntity addBasket(@RequestBody Baskets baskets){
        return basketService.addBaskets(baskets);
    }

    @PutMapping("/baskets/modify/{basketId}")
    public ResponseEntity modifyBaskets(@PathVariable Long basketId, @RequestBody Baskets baskets){
        return basketService.modifyBasket(basketId,baskets);
    }

    @DeleteMapping("/baskets/delete/{basketId}")
    public ResponseEntity deleteBasket(@PathVariable Long basketId){
         return basketService.deleteBasket(basketId);
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
