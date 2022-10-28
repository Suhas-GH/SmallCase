package com.abnamro.smallcase.controller;

import com.abnamro.smallcase.model.ApplicationUser;
import com.abnamro.smallcase.repository.ApplicationUserRepository;
import com.abnamro.smallcase.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    Authentication authentication;
    @Mock
    ApplicationUserRepository applicationUserRepository;

    ApplicationUser user = new ApplicationUser(1L,"Prakash","A","prakash01","Abc@12345");
    @Test
    void createUser() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andReturn();
        when(userService.registerUser(user)).thenReturn(true);
        //userService.registerUser(user);
        assertEquals("",result.getResponse().getRedirectedUrl());
    }

    @Test
    void createUser1() throws Exception{
        ApplicationUser user = new ApplicationUser(1L,"a","b","aa","Abc@1223");
        Mockito.when(userService.registerUser(user)).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andReturn();
        assertEquals("/register",result.getResponse().getRedirectedUrl());
    }

    @Test
    void getUser() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                        .with(SecurityMockMvcRequestPostProcessors.user("john"))
                        .with(csrf()));

        when(userService.getUser()).thenReturn(anyLong());
        verify(userService,times(1)).getUser();
    }

//    @WithMockUser("user")
//    @Test
//    void getUser1() throws Exception{
//        UserController userController = new UserController();
//        when(userController.getUser()).thenReturn(userService.getUser());
//    }
}