package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DisplayName("ProductController 클래스")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private List<Product> products = new ArrayList<>();

    @BeforeEach
    void setUp() {

    }

    public void generateProducts() {
        Product product1 = Product.builder()
                .id(1L)
                .name("name 1")
                .image("imageURL 1")
                .maker("brand 1")
                .price(100)
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .name("name 2")
                .image("imageURL 2")
                .maker("brand 2")
                .price(200)
                .build();

        products.add(product1);
        products.add(product2);
    }

    @Nested
    @DisplayName("GET /products 요청은")
    class Describe_GET_products {
        @Nested
        @DisplayName("저장된 상품이 여러개 있다면")
        class Context_with_products {

            @BeforeEach
            void setUp() {
                generateProducts();
                given(productService.getProducts()).willReturn(products);
            }

            @DisplayName("200코드와 모든 상품 목록을 응답한다")
            @Test
            void it_responses_200_with_all_products() throws Exception {
                mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(2)))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("저장된 상품이 없다면 있다면")
        class Context_without_products {

            @BeforeEach
            void setUp() {
                given(productService.getProducts()).willReturn(products);
            }

            @DisplayName("200코드와 빈 목록을 응답한다")
            @Test
            void it_responses_200_with_all_products() throws Exception {
                mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(0)))
                        .andExpect(status().isOk());
            }
        }
    }
}
