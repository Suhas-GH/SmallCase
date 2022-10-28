package com.abnamro.smallcase.integration;

import com.abnamro.smallcase.SmallCaseApplication;
import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.model.Stocks;
import com.abnamro.smallcase.model.StocksMapping;
import com.abnamro.smallcase.repository.StocksRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmallCaseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StockIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Spy
    StocksRepository stocksRepository;



    Set<StocksMapping> stocksMappingSet = new HashSet<>();
    Baskets baskets = new Baskets(1L, "basket1", "basket1 desc", stocksMappingSet, null);
    Stocks stocks = new Stocks(2L, "stock1", 1200F, stocksMappingSet);
    StocksMapping stocksMapping = new StocksMapping(10L, stocks, baskets, 2);

    @Test
    void addStocks() throws Exception {
        stocksMappingSet.add(stocksMapping);
        String url = "/stocks/add";
        mockMvc.perform(post(url)
                .content(new ObjectMapper().writeValueAsString(stocks))
                .contentType(MediaType.APPLICATION_JSON));
        stocksRepository.save(stocks);
        Mockito.verify(stocksRepository, Mockito.times(1)).save(stocks);
    }

    @Test
    void modifyStocks() throws Exception {
        stocksMappingSet.add(stocksMapping);
        String uri = "/stocks/modify/{stockID}";
        mockMvc.perform(MockMvcRequestBuilders.put(uri, 2)
                .with(SecurityMockMvcRequestPostProcessors.user("user"))
                .with(csrf())
                .content(new ObjectMapper().writeValueAsString(stocks))
                .contentType(MediaType.APPLICATION_JSON));
        Long stockId = 1L;
        Mockito.when(stocksRepository.existsById(stockId)).thenReturn(true);
        Mockito.doNothing().when(stocksRepository).deleteById(2L);
        stocksRepository.modifyStocks(stocks.getStockName(), stocks.getStockPrice(), stockId);
        Mockito.verify(stocksRepository, Mockito.times(1)).modifyStocks(stocks.getStockName(), stocks.getStockPrice(), stockId);

    }
    @WithMockUser("user")
    @Test
    void deleteStocks() throws Exception  {
        stocksMappingSet.add(stocksMapping);
        String uri = "/stocks/delete/{stockId}";
        mockMvc.perform(MockMvcRequestBuilders.delete(uri,2));
        Mockito.doNothing().when(stocksRepository).deleteById(2L);
        stocksRepository.deleteById(2L);
        Mockito.verify(stocksRepository,Mockito.times(1)).deleteById(2L);
    }
}