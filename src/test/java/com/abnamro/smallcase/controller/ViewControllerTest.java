package com.abnamro.smallcase.controller;

import com.abnamro.smallcase.controller.BasketController;
import com.abnamro.smallcase.model.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.thymeleaf.spring5.expression.Mvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ViewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    BasketController basketController;
    ApplicationUser user = new ApplicationUser(1L,"Prakash","A","user1","Abc@123");

    @Test
    public void registration() throws Exception{
        String url="/register";
        MvcResult result = mockMvc.perform(get("/register")
                .content(new ModelMap().addAttribute(user).toString())).andReturn();
        assertEquals("register",result.getModelAndView().getViewName());
    }

    @Test
    public void Login() throws Exception{
        MvcResult result= mockMvc.perform(get("/")).andReturn();
        assertEquals("index",result.getModelAndView().getViewName());
    }

    @WithMockUser("Prakash")
    @Test
    public void home() throws Exception{
        MvcResult result = mockMvc.perform(get("/home")
                .content(new ModelMap().addAttribute("basketList",basketController.getBaskets()).toString())).andReturn();
        assertEquals("home",result.getModelAndView().getViewName());
    }
}
