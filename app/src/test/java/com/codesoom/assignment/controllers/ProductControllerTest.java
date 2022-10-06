package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    private Long INVALID_PRODUCT_ID = 0L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Nested
    @DisplayName("GET /products getProducts 메서드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("저장되어 있는 product가 여러 개 있을 때")
        class Context_with_products {
            private List<Product> givenProducts;

            @BeforeEach
            void setUp() {
                givenProducts = List.of(
                        Product.builder()
                                .id(null)
                                .name("장난감1")
                                .maker("M")
                                .price(1000)
                                .imageUrl("http://images/1")
                                .build(),
                        Product.builder()
                                .id(null)
                                .name("장난감2")
                                .maker("K")
                                .price(1000)
                                .imageUrl("http://images/2")
                                .build()
                );
                givenProducts.forEach(product -> productService.createProduct(product));
            }

            @AfterEach
            void after() {
                productService.deleteAll();
            }

            @Test
            @DisplayName("모든 product 를 리턴한다")
            void it_returns_products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].id").value(givenProducts.get(0).getId()))
                        .andExpect(jsonPath("$[0].name").value(givenProducts.get(0).getName()))
                        .andExpect(jsonPath("$[0].maker").value(givenProducts.get(0).getMaker()))
                        .andExpect(jsonPath("$[0].price").value(givenProducts.get(0).getPrice()))
                        .andExpect(jsonPath("$[0].imageUrl").value(givenProducts.get(0).getImageUrl()))

                        .andExpect(jsonPath("$[1].id").value(givenProducts.get(1).getId()))
                        .andExpect(jsonPath("$[1].name").value(givenProducts.get(1).getName()))
                        .andExpect(jsonPath("$[1].maker").value(givenProducts.get(1).getMaker()))
                        .andExpect(jsonPath("$[1].price").value(givenProducts.get(1).getPrice()))
                        .andExpect(jsonPath("$[1].imageUrl").value(givenProducts.get(1).getImageUrl()));
            }
        }

        @Nested
        @DisplayName("저장되어 있는 product 가 없을 때")
        class Context_without_product {
            @Test
            @DisplayName("빈 값을 리턴한다")
            void it_returns_empty() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("[]"));
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("저장되어 있는 product 의 id가 주어지면")
        class Context_with_existing_product_id {
            private Product givenProduct;

            @BeforeEach
            void setUp() {
                givenProduct = productService.createProduct(
                        Product.builder()
                                .id(null)
                                .name("장난감1")
                                .maker("M")
                                .price(1000)
                                .imageUrl("http://images/1")
                                .build()
                );
            }

            @AfterEach
            void after() {
                productService.deleteAll();
            }

            @Test
            @DisplayName("요청에 맞는 product 를 리턴한다")
            void it_returns_product() throws Exception {
                mockMvc.perform(get("/products/" + givenProduct.getId()))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(givenProduct.getId()))
                        .andExpect(jsonPath("$.name").value(givenProduct.getName()))
                        .andExpect(jsonPath("$.maker").value(givenProduct.getMaker()))
                        .andExpect(jsonPath("$.price").value(givenProduct.getPrice()))
                        .andExpect(jsonPath("$.imageUrl").value(givenProduct.getImageUrl()));
            }
        }

        @Nested
        @DisplayName("저장되어 있지 않은 product id가 주어지면")
        class Context_with_non_existent_product_id {
            @Test
            @DisplayName("제품을 찾을 수 없는 예외를 던진다")
            void it_returns_exception() throws Exception {
                mockMvc.perform(get("/products/" + INVALID_PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
