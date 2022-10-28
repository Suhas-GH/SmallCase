package com.abnamro.smallcase.controller;

import com.abnamro.smallcase.model.*;
import com.abnamro.smallcase.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@WebMvcTest( CartController.class)
class CartControllerTest {
    @MockBean
    CartService cartService;
    @Autowired
    private MockMvc mockMvc;

    Set<StocksMapping> stocksMappingSet=new HashSet<>();

    Baskets basket = new Baskets(1L,"basket1","basket1 desc",stocksMappingSet,null);
    Stocks stock = new Stocks(1L,"stock1",1200F,stocksMappingSet);
    StocksMapping stocksMapping = new StocksMapping(1L,stock,basket,4);
    CartMapping cartMapping= new CartMapping();
    List<CartMapping> cartMappingList=new ArrayList<>();
    Cart cart = new Cart(1L,10L,cartMappingList);

    @Test
    void addToCart() throws Exception{
        stocksMappingSet.add(stocksMapping);
        cartMapping.setCart(cart);
        cartMappingList.add(cartMapping);
        basket.setCartMappings(cartMappingList);
        mockMvc.perform(MockMvcRequestBuilders.put("/cart/add/{basketId}",3)
                  .with(SecurityMockMvcRequestPostProcessors.user("John"))
                .with(csrf()));
        Mockito.verify(cartService,Mockito.times(1)).addToCart(3L);
    }

    @Test
    void deleteFromCart() throws Exception{
        stocksMappingSet.add(stocksMapping);
        cartMappingList.add(cartMapping);
        String url = "/cart/remove/{basketId}";
        mockMvc.perform(MockMvcRequestBuilders.delete(url,anyLong())
                .with(SecurityMockMvcRequestPostProcessors.user("john"))
                .with(csrf()));
        doNothing().when(cartService).deleteFromCart(anyLong());
        Mockito.verify(cartService,Mockito.times(1)).deleteFromCart(anyLong());
    }
}