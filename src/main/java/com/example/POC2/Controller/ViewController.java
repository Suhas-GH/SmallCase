package com.example.POC2.Controller;

import com.example.POC2.Model.ApplicationUser;
import com.example.POC2.Model.Baskets;
import com.example.POC2.Model.Stocks;
import com.example.POC2.Repository.ApplicationUserRepository;
import com.example.POC2.Repository.CartRepository;
import com.example.POC2.Service.BasketService;
import com.example.POC2.Service.CartService;
import com.example.POC2.Service.StocksService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        model.addAttribute("basketId",BasketId);
        List<Long> MappingIds = basketService.getMappingIds(baskets);
        Collections.sort(MappingIds);
        List<Stocks> Stocks = stocksService.getStocksByMapping(MappingIds);
        Collections.sort(Stocks);
        model.addAttribute("stockDetails",Stocks);
        Float InvestmentAmount = stocksService.getInvestmentAmount(Stocks,BasketId);
        model.addAttribute("investmentAmount",InvestmentAmount);
        return "basketdetails";
    }

    @GetMapping("/Cart")
    public String Cart(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String UserName = authentication.getName();
        ApplicationUser user = userRepository.findByUserName(UserName);
        Long UserId = user.getUserId();
        if (cartRepository.existsByUserId(UserId)){
            model.addAttribute("cartBaskets", cartService.getUsersCart(UserId));
        }
        return "cart";
    }

    @GetMapping("/addtocart/{BasketId}")
    public String addtocart(@PathVariable Long BasketId){
        cartService.addToCart(BasketId);
        return "redirect:../Cart";
    }

    @GetMapping("/removefromcart/{BasketId}")
    private String removefromcart(@PathVariable Long BasketId){
        cartService.deleteFromCart(BasketId);
        return "redirect:../Cart";
    }

    @GetMapping("/logout")
    private String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return "redirect:/";
    }
}
