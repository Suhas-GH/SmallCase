package com.example.smallcase.controller;

import com.example.smallcase.model.ApplicationUser;
import com.example.smallcase.model.Baskets;
import com.example.smallcase.model.Stocks;
import com.example.smallcase.repository.ApplicationUserRepository;
import com.example.smallcase.repository.CartRepository;
import com.example.smallcase.service.BasketService;
import com.example.smallcase.service.CartService;
import com.example.smallcase.service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private CartService cartService;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @RequestMapping("/Register")
    public String registration(Model model){
        ApplicationUser applicationUser = new ApplicationUser();
        model.addAttribute(applicationUser);
        return "register";
    }

    @RequestMapping("/")
    public String login(){
        return "index";
    }

    @RequestMapping("/Home")
    public String home(Model model){
        model.addAttribute("basketList",basketController.getBaskets());
        return "home";
    }

    @GetMapping("/BasketDetails/{basketId}")
    public String basketDetails(Model model, @PathVariable Long basketId){
        Baskets baskets = basketController.getBasketDetails(basketId).orElseThrow(() -> new IllegalArgumentException("Invalid Basket Id:" + basketId));
        model.addAttribute("basketDetails",baskets);
        model.addAttribute("basketId", basketId);
        List<Long> mappingIds = basketService.getMappingIds(baskets);
        Collections.sort(mappingIds);
        List<Stocks> stocks = stocksService.getStocksByMapping(mappingIds);
        Collections.sort(stocks);
        model.addAttribute("stockDetails",stocks);
        Float investmentAmount = stocksService.getInvestmentAmount(stocks, basketId);
        model.addAttribute("investmentAmount",investmentAmount);
        return "basketdetails";
    }

    @GetMapping("/Cart")
    public String cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        ApplicationUser user = userRepository.findByUserName(userName);
        Long userId = user.getUserId();
        if (cartRepository.existsByUserId(userId)){
            model.addAttribute("cartBaskets", cartService.getUsersCart(userId));
        }
        return "cart";
    }

    @GetMapping("/addtocart/{basketId}")
    public String addtocart(@PathVariable Long basketId){
        cartService.addToCart(basketId);
        return "redirect:../Cart";
    }

    @GetMapping("/removefromcart/{basketId}")
    public String removefromcart(@PathVariable Long basketId){
        cartService.deleteFromCart(basketId);
        return "redirect:../Cart";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }
}