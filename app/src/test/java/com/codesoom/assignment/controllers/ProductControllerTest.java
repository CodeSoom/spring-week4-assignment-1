package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String PRODUCT_NAME = "test";
    private static final String SAVE_POSTFIX = "111";
    private static final String UPDATE_POSTFIX = "222";

    @DisplayName("GET /products는 저장하고 있는 상품 목록을 반환한다")
    @Test
    void getProducts() throws Exception {
        given(productService.getProducts()).willReturn(new ArrayList<>());

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());

        verify(productService).getProducts();
    }

    @DisplayName("GET /products/{id}는 주어진 아이디의 상품을 반환한다")
    @Test
    void getProduct() throws Exception {
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());

        verify(productService).getProduct(1L);
    }

    @DisplayName("POST /products는 상품을 저장한다")
    @Test
    void saveProduct() throws Exception {
        String name = PRODUCT_NAME + SAVE_POSTFIX;

        String json = createProductJson(name);

        mockMvc.perform(post("/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(productService).saveProduct(any(Product.class));
    }

    @DisplayName("PATCH /products/{id}는 주어진 아이디의 상품을 수정한다")
    @Test
    void updateProduct() throws Exception {
        String name = PRODUCT_NAME + UPDATE_POSTFIX;

        String json = createProductJson(name);

        mockMvc.perform(patch("/products/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("DELETE /products/{id}는 주어진 아이디의 상품을 삭제한다")
    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(1L);
    }

    private String createProductJson(String name) throws JsonProcessingException {
        Product source = Product.insert(name, null, 0, null);
        return objectMapper.writeValueAsString(source);
    }
}