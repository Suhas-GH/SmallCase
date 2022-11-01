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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmallCaseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StockIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    Set<StocksMapping> stocksMappingSet = new HashSet<>();
    Baskets baskets = new Baskets(1L, "basket1", "basket1 desc", stocksMappingSet, null);
    Stocks stocks = new Stocks(1L, "stock1", 1200F, stocksMappingSet);
    StocksMapping stocksMapping = new StocksMapping(10L, stocks, baskets, 2);

    @Test
    void addStocks() throws Exception {
        stocksMappingSet.add(stocksMapping);
        String url = "/stocks/add";
        MvcResult result = mockMvc.perform(post(url)
                .content(new ObjectMapper().writeValueAsString(stocks))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());    }

    @Test
    void modifyStocks() throws Exception {
        Stocks stock = new Stocks();
        stock.setStockId(2L);
        stock.setStockName("modified stock");
        stock.setStockPrice(999F);
        String uri = "/stocks/modify/{stockID}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri, 2)
                .with(SecurityMockMvcRequestPostProcessors.user("user"))
                .with(csrf())
                .content(new ObjectMapper().writeValueAsString(stock))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
    }
    @WithMockUser("user")
    @Test
    void deleteStocks() throws Exception  {
        stocksMappingSet.add(stocksMapping);
        String uri = "/stocks/delete/{stockId}";
        mockMvc.perform(MockMvcRequestBuilders.delete(uri,3));

    }
//    @WithMockUser("user")
//    @Test
//    void getStocks() throws Exception{
//        String url="/stocks";
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();
//        System.out.println(result.getResponse().getContentAsString());
//    }
}