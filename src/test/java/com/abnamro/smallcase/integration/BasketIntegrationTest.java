package com.abnamro.smallcase.integration;

import com.abnamro.smallcase.SmallCaseApplication;
import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.model.Stocks;
import com.abnamro.smallcase.model.StocksMapping;
import com.abnamro.smallcase.repository.BasketsRepository;
import com.abnamro.smallcase.repository.StocksRepository;
import com.abnamro.smallcase.service.BasketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.thymeleaf.spring5.expression.Mvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmallCaseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BasketIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    Set<StocksMapping> stocksMappingSet=new HashSet<>();
    Baskets basket = new Baskets(1L,"TATA group","Companies that are part of TATA group",stocksMappingSet,null);
    Stocks stock = new Stocks(2L,"Tata Consultancy Services",34.39F,stocksMappingSet);
    StocksMapping stocksMapping = new StocksMapping(1L,stock,basket,15);
    StocksMapping stocksMapping1 = new StocksMapping(2L,stock,basket,10);
    @WithMockUser("user")
    @Test
    public void addBasket() throws Exception{
        stocksMappingSet.add(stocksMapping);
        stocksMappingSet.add(stocksMapping1);
        String url = "/baskets/add";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(basket))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }
    @WithMockUser("user")
    @Test
    public void modifyBasket() throws Exception{
        stocksMappingSet.add(stocksMapping);
        stocksMappingSet.add(stocksMapping1);

        String url="/baskets/modify/{basketId}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(url,2)
                .content(new ObjectMapper().writeValueAsString(basket))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }
    @WithMockUser("user")
    @Test
    public void deleteBasket() throws Exception{
        String url="/baskets/delete/{basketId}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(url,1L)).andReturn();
        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }
    @WithMockUser("user")
    @Test
    public void getBaskets() throws Exception{
        String url="/getBaskets";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }

    @WithMockUser("user")
    @Test
    public void getBasketDetails() throws Exception{
        String url="/{basketId}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url,3)).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }

}
