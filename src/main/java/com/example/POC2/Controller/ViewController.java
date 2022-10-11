package com.example.POC2.Controller;

import com.example.POC2.Model.ApplicationUser;
import com.example.POC2.Model.Baskets;
import com.example.POC2.Model.Stocks;
import com.example.POC2.Service.BasketService;
import com.example.POC2.Service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class ViewController {
    @Autowired
    private BasketController basketController;

    @Autowired
    private BasketService basketService;

    @Autowired
    private StocksService stocksService;

    @RequestMapping("/Register")
    public String Registration(Model model){
        ApplicationUser applicationUser = new ApplicationUser();
        model.addAttribute(applicationUser);
        return "register";
    }

    @RequestMapping("/")
    public String Login(){
        return "index";
    }

    @RequestMapping("/Home")
    public String Home(Model model){
        model.addAttribute("basketList",basketController.getBaskets());
        return "home";
    }

    @GetMapping("/BasketDetails/{BasketId}")
    public String BasketDetails(Model model, @PathVariable Long BasketId){
        Baskets baskets = basketController.getBasketDetails(BasketId).orElseThrow(() -> new IllegalArgumentException("Invalid Basket Id:" + BasketId));
        model.addAttribute("basketDetails",baskets);
        List<Long> MappingIds = basketService.getMappingIds(baskets);
        Collections.sort(MappingIds);
        List<Stocks> Stocks = stocksService.getStocksByMapping(MappingIds);
        Collections.sort(Stocks);
        model.addAttribute("stockDetails",Stocks);
        Float InvestmentAmount = stocksService.getInvestmentAmount(Stocks);
        model.addAttribute("investmentAmount",InvestmentAmount);
        return "basketdetails";
    }
}
