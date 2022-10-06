package com.example.POC2.Controller;

import com.example.POC2.Model.Baskets;
import com.example.POC2.Service.BasketService;
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

    @PutMapping("/baskets/modify/{BasketId}")
    public ResponseEntity modifyBaskets(@PathVariable Long BasketId,@RequestBody Baskets baskets){
        return basketService.modifyBasket(BasketId,baskets);
    }

    @DeleteMapping("/baskets/delete/{BasketId}")
    public ResponseEntity deleteBasket(@PathVariable Long BasketId){
         return basketService.deleteBasket(BasketId);
    }

    @GetMapping("/getBaskets")
    public List<Baskets> getBaskets(){
        return basketService.getBaskets();
    }

    @GetMapping("/{BasketId}")
    public Optional<Baskets> getBasketDetails(@PathVariable Long BasketId){
        return basketService.getBasketDetails(BasketId);
    }

}
