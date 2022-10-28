package com.abnamro.smallcase.controller;

import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.model.Stocks;
import com.abnamro.smallcase.model.StocksMapping;
import com.abnamro.smallcase.service.BasketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class BasketControllerTest {
    @MockBean
    BasketService basketService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    Set<StocksMapping> stocksMappingSet=new HashSet<>();
    Baskets basket = new Baskets(1L,"basket1","basket1 desc",stocksMappingSet,null);
    Stocks stock = new Stocks(1L,"stock1",1200F,stocksMappingSet);
    StocksMapping stocksMapping = new StocksMapping(1L,stock,basket,4);

    @Test
    void addBasket() throws Exception{
        stocksMappingSet.add(stocksMapping);
        String url = "/baskets/add";
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(basket))
                .contentType(MediaType.APPLICATION_JSON));
        //doNothing().when(basketService).addBaskets(basket);
        basketService.addBaskets(basket);
        verify(basketService,times(1)).addBaskets(basket);
    }



    @Test
    void modifyBaskets() throws Exception{
        stocksMappingSet.add(stocksMapping);
        String url = "/baskets/modify/{basketId}";
        Long basketId = basket.getBasketId();
        this.mockMvc.perform(MockMvcRequestBuilders.put(url,1)
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                .content(new ObjectMapper().writeValueAsString(basket))
                .contentType(MediaType.APPLICATION_JSON));
        doNothing().when(basketService).modifyBasket(basketId,basket);
        basketService.modifyBasket(basketId,basket);
        verify(basketService,atLeast(1)).modifyBasket(basketId,basket);
    }

    @Test
    void deleteBasket() throws Exception{
        stocksMappingSet.add(stocksMapping);
        String url = "/baskets/delete/{basketId}";
        mockMvc.perform(MockMvcRequestBuilders.delete(url,3)
                .with(SecurityMockMvcRequestPostProcessors.user("user")));
        //doNothing().when(basketService).deleteBasket(basketId);
        verify(basketService,times(1)).deleteBasket(3L);
    }

    @Test
    void getBaskets() throws Exception {
        List<Baskets> basketsList = new ArrayList<>();
        basketsList.add(basket);
        String url ="/getBaskets";
       MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        when(basketService.getBaskets()).thenReturn(basketsList);
//      assertEquals(basketService.getBaskets(),basketsList);
        verify(basketService,times(1)).getBaskets();
    }

    @WithMockUser
    @Test
    void getBasketDetails() throws Exception{
        String url = "/{basketId}";
        mockMvc.perform(MockMvcRequestBuilders.get(url,anyLong()));
        when(basketService.getBasketDetails(anyLong())).thenReturn(Optional.ofNullable(basket));
        //assertEquals(basketService.getBasketDetails(anyLong()), Optional.ofNullable(basket));
        verify(basketService,times(1)).getBasketDetails(anyLong());
    }

}