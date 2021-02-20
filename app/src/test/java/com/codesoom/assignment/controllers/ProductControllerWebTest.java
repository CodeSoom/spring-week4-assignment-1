package com.codesoom.assignment.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProductListTest() throws Exception {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk());
    }

    @Test
    void createProductTest() throws Exception {
        mockMvc.perform(post("/products"))
            .andExpect(status().isCreated());
    }
}
