package com.abnamro.smallcase.controller;

import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.model.Stocks;
import com.abnamro.smallcase.model.StocksMapping;
import com.abnamro.smallcase.service.StocksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class StocksControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    StocksService stocksService;
    Set<StocksMapping> stocksMappingSet = new HashSet<>();
    Baskets baskets = new Baskets(1L,"basket1","basket1 desc",stocksMappingSet,null);
    Stocks stocks = new Stocks(2L,"stock1",1200F,stocksMappingSet);
    StocksMapping stocksMapping = new StocksMapping(10L,stocks,baskets,2);

    @Test
    void addStocks() throws Exception{

        stocksMappingSet.add(stocksMapping);
        String url="/stocks/add";
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(stocks))
                .contentType(MediaType.APPLICATION_JSON));
        Mockito.doNothing().when(stocksService).addStocks(stocks);
        stocksService.addStocks(stocks);
        Mockito.verify(stocksService,Mockito.times(1)).addStocks(stocks);
    }

    @Test
    void modifyStocks() throws Exception{
        stocksMappingSet.add(stocksMapping);
        String uri="/stocks/modify/{stockId}";
        mockMvc.perform(MockMvcRequestBuilders.put(uri,2)
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                        .with(csrf())
                        .content(new ObjectMapper().writeValueAsString(stocks))
                        .contentType(MediaType.APPLICATION_JSON));
        Mockito.doNothing().when(stocksService).modifyStocks(2L,stocks);
        stocksService.modifyStocks(2L,stocks);
        Mockito.verify(stocksService,Mockito.times(1)).modifyStocks(2L,stocks);
    }

    @WithMockUser
    @Test
    void deleteStocks() throws Exception  {
        stocksMappingSet.add(stocksMapping);
        String uri = "/stocks/delete/{stockid}";
        mockMvc.perform(MockMvcRequestBuilders.delete(uri,2));
        Mockito.doNothing().when(stocksService).deleteStocks(2L);
        Mockito.verify(stocksService,Mockito.times(1)).deleteStocks(2L);
    }
}