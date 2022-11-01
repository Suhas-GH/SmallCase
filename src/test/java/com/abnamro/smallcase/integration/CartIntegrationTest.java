package com.abnamro.smallcase.integration;

import com.abnamro.smallcase.SmallCaseApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SmallCaseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CartIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void getCart1() throws Exception{
//        String url="/cart";
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)).andReturn();
//        System.out.println(result.getResponse().getContentAsString());
//        Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
//    }
    @WithMockUser("user")
    @Test
    public void addToCart() throws Exception{
        String url = "/cart/add/{basketId}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(url,1))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(),result.getResponse().getStatus());
    }

    @WithMockUser("user")
    @Test
    public void deleteFromCart() throws Exception{
        String url = "/cart/remove/{basketId}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(url,2))
                .andReturn();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(),result.getResponse().getStatus());
    }
}
