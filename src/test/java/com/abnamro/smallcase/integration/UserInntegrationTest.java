package com.abnamro.smallcase.integration;

import com.abnamro.smallcase.SmallCaseApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(classes = SmallCaseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserInntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Test
    public void createUser() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("firstName", "Suhas")
                        .param("lastName", "Hosalli")
                        .param("userName", "SuhasUser@1")
                        .param("password", "Suhas@123"))
                .andReturn();
        assertEquals("/", result.getResponse().getRedirectedUrl());
    }
}
