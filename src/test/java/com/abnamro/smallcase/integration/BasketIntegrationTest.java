package com.abnamro.smallcase.integration;

import com.abnamro.smallcase.SmallCaseApplication;
import com.abnamro.smallcase.model.Baskets;
import com.abnamro.smallcase.model.Stocks;
import com.abnamro.smallcase.model.StocksMapping;
import com.abnamro.smallcase.repository.BasketsRepository;
import com.abnamro.smallcase.repository.StocksRepository;
import com.abnamro.smallcase.service.BasketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmallCaseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BasketIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Spy
    BasketsRepository basketsRepository;
    @Spy
    StocksRepository stocksRepository;
   @MockBean
           BasketService basketService;

    Set<StocksMapping> stocksMappingSet=new HashSet<>();
    Baskets basket = new Baskets(1L,"basket1","basket1 desc",stocksMappingSet,null);
    Stocks stock = new Stocks(2L,"stock1",1200F,stocksMappingSet);
    StocksMapping stocksMapping = new StocksMapping(1L,stock,basket,4);

    @WithMockUser("user")
    @Test
    public void addBasket() throws Exception{
        stocksMappingSet.add(stocksMapping);
        String url = "/baskets/add";
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(new ObjectMapper().writeValueAsString(basket))
                .contentType(MediaType.APPLICATION_JSON));
        basketService.addBaskets(basket);
        basketsRepository.save(basket);
        verify(basketsRepository).save(basket);
    }

    @WithMockUser("user")
    @Test
    public void modifyBasket() throws Exception{
        stocksMappingSet.add(stocksMapping);
        List<Long> Ids = basket.getStocksMappings().stream().map(x -> x.getStocks().getStockId())
                .collect(Collectors.toList());
        long count = Ids.stream().count();
        when(stocksRepository.checkStocks(Ids)).thenReturn((int)count);
        when(basketsRepository.existsById(basket.getBasketId())).thenReturn(true);
        basketService.modifyBasket(1L,basket);
        String url = "/baskets/modify/{basketId}";
        mockMvc.perform(MockMvcRequestBuilders.put(url,1L)
                .content(new ObjectMapper().writeValueAsString(basket))
                .contentType(MediaType.APPLICATION_JSON));
        assertEquals(basket,basketsRepository.save(basket));
    }
}
