package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@DisplayName("ProductController 클래스")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private final Long STORED_ID = 1L;
    private final Long NOT_STORED_ID = 100L;
    private final String STORED_NAME = "name1";
    private final String NAME = "name";

    private List<ProductResponse> products;
    private Product product;
    private ProductResponse productResponse1;
    private ProductResponse productResponse2;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();

        product = new Product().builder()
                .name(NAME)
                .maker("maker")
                .price(1)
                .imageUrl("url")
                .build();

        productResponse1 = new ProductResponse(
                Product.builder()
                        .id(STORED_ID)
                        .name(STORED_NAME)
                        .maker("maker1")
                        .price(1)
                        .imageUrl("url1")
                        .build()
        );

        productResponse2 = new ProductResponse(
                Product.builder()
                        .id(2L)
                        .name("name2")
                        .maker("maker2")
                        .price(2)
                        .imageUrl("url2")
                        .build()
        );
    }

    @Nested
    @DisplayName("GET 요청은")
    class Describe_GET {

        @Nested
        @DisplayName("저장된 상품이 여러 개 있다면")
        class Context_with_products {

            @BeforeEach
            void setUp() {
                products.add(productResponse1);
                products.add(productResponse2);

                given(productService.getProducts())
                        .willReturn(products);
            }

            @Test
            @DisplayName("모든 상품 목록과 상태코드 200을 응답한다")
            void it_responds_all_products_and_status_code_200() throws Exception {
                mockMvc.perform(get("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(products.size())))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("저장된 상품이 없다면")
        class Context_without_product {

            @BeforeEach
            void setUp() {
                given(productService.getProducts())
                        .willReturn(List.of());
            }

            @Test
            @DisplayName("빈 리스트와 상태코드 200을 응답한다")
            void it_responds_empty_list_and_status_code_200() throws Exception {
                mockMvc.perform(get("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(0)))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("저장된 상품 id가 주어진다면")
        class Context_with_an_stored_product_id {

            @BeforeEach
            void setUp() {
                given(productService.getProduct(STORED_ID))
                        .willReturn(productResponse1);
            }

            @Test
            @DisplayName("찾은 상품과 상태코드 200을 응답한다")
            void it_responds_found_product_and_status_code_200() throws Exception {
                mockMvc.perform(get("/products/{id}", STORED_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString(STORED_NAME)))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("저장되지 않은 id가 주어진다면")
        class Context_with_an_not_stored_product_id {

            @BeforeEach
            void setUp() {
                given(productService.getProduct(NOT_STORED_ID))
                        .willThrow(new ProductNotFoundException());
            }

            @Test
            @DisplayName("에러 메시지와 상태코드 404를 응답한다")
            void it_responds_error_and_status_code_404() throws Exception {
                mockMvc.perform(get("/products/{id}", NOT_STORED_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").doesNotExist())
                        .andExpect(jsonPath("message").exists())
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("POST 요청은")
    class Describe_POST {

        @Nested
        @DisplayName("상품이 주어지면")
        class Context_with_product {

            @BeforeEach
            void setUp() {
                given(productService.createProduct(any(Product.class)))
                        .willReturn(productResponse1);
            }

            @Test
            @DisplayName("생성된 상품과 상태코드 201을 응답한다")
            void it_responds_created_product_and_status_code_201() throws Exception {
                mockMvc.perform(post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(product)))
                        .andExpect(content().string(containsString(NAME)))
                        .andExpect(status().isCreated());
            }
        }
    }

    @Nested
    @DisplayName("PATCH 요청은")
    class Describe_PATCH {

        @Nested
        @DisplayName("존재하는 상품 id가 주어진다면")
        class Context_with_an_existing_product_id {
            @BeforeEach
            void setUp() {
                given(productService.updateProduct(eq(STORED_ID), any(Product.class)))
                        .willReturn(productResponse1);
            }

            @Test
            @DisplayName("수정된 상품과 상태코드 200을 응답한다")
            void it_responds_the_updated_product_and_status_code_200() throws Exception {
                mockMvc.perform(patch("/products/{id}", STORED_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(product)))
                        .andExpect(content().string(containsString(NAME)))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품 id가 주어진다면")
        class Context_with_not_existing_product_id {
            @BeforeEach
            void setUp() {
                given(productService.updateProduct(eq(NOT_STORED_ID), any(Product.class)))
                        .willThrow(new ProductNotFoundException());
            }

            @Test
            @DisplayName("에러메시지와 상태코드 404를 응답한다")
            void it_responds_the_error_message_and_status_code_404() throws Exception {
                mockMvc.perform(patch("/products/{id}", NOT_STORED_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(product)))
                        .andExpect(jsonPath("name").doesNotExist())
                        .andExpect(jsonPath("message").exists())
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("DELETE 요청은")
    class Describe_DELETE {
        @Nested
        @DisplayName("존재하는 상품 id가 주어진다면")
        class Context_with_an_existing_product_id {
            @Test
            @DisplayName("상태코드 204을 응답한다")
            void it_responds_status_code_204() throws Exception {
                mockMvc.perform(delete("/products/{id}", STORED_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품 id가 주어진다면")
        class Context_with_not_existing_product_id {
            @BeforeEach
            void setUp() {
                willThrow(new ProductNotFoundException())
                        .given(productService).deleteProduct(NOT_STORED_ID);
            }

            @Test
            @DisplayName("에러메시지와 상태코드 404를 응답한다")
            void it_responds_the_error_message_and_status_code_404() throws Exception {
                mockMvc.perform(delete("/products/{id}", NOT_STORED_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").doesNotExist())
                        .andExpect(jsonPath("message").exists())
                        .andExpect(status().isNotFound());
            }
        }
    }

}
