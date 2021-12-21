package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ProductService productService;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String PRODUCT_NAME = "test";
    private static final String SAVE_POSTFIX = "111";
    private static final String UPDATE_POSTFIX = "222";

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
    }

    @Test
    void getProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    void getProduct() throws Exception {
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void saveProduct() throws Exception {
        String name = PRODUCT_NAME + SAVE_POSTFIX;

        Product source = Product.insert(name, null, 0, null);
        String json = objectMapper.writeValueAsString(source);

        mockMvc.perform(post("/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void updateProduct() throws Exception {
        String name = PRODUCT_NAME + UPDATE_POSTFIX;

        Product source = Product.insert(name, null, 0, null);
        String json = objectMapper.writeValueAsString(source);

        mockMvc.perform(patch("/products/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}