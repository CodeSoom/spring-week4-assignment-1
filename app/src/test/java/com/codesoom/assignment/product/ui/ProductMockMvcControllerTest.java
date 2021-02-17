package com.codesoom.assignment.product.ui;

import com.codesoom.assignment.product.application.ProductNotFoundException;
import com.codesoom.assignment.product.application.ProductService;
import com.codesoom.assignment.product.ui.dto.ProductResponseDto;
import com.codesoom.assignment.product.ui.dto.ProductSaveRequestDto;
import com.codesoom.assignment.product.ui.dto.ProductUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스")
class ProductMockMvcControllerTest {
    private static final Long PRODUCT_ID = 1L;
    private static final Long NOT_EXIST_ID = -1L;

    private static final String NAME = "snake";
    private static final String MAKER = "cat toy";
    private static final int PRICE = 5000;
    private static final String IMAGE_URL = "https://http.cat/599";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Nested
    @DisplayName("GET /products는")
    class Describe_getProducts {
        @Nested
        @DisplayName("등록된 상품이 있으면")
        class Context_with_products {
            List<ProductResponseDto> products;

            @BeforeEach
            void setUp() {
                ProductResponseDto product = new ProductResponseDto(PRODUCT_ID, NAME, MAKER, PRICE, IMAGE_URL);
                products = Collections.singletonList(product);

                given(productService.getProducts())
                        .willReturn(products);
            }

            @DisplayName("200 상태 코드, OK 상태와 상품 목록을 응답한다.")
            @Test
            void It_responds_ok_with_products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(products)));
            }
        }

        @Nested
        @DisplayName("등록된 상품이 없으면")
        class Context_without_products {

            @DisplayName("200 상태코드, OK 상태와 비어있는 상품 목록을 응답한다.")
            @Test
            void It_responds_ok_with_empty_products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("[]")));
            }
        }
    }

    @Nested
    @DisplayName("GET /products/{id} 는")
    class Describe_getProduct {

        @Nested
        @DisplayName("등록된 상품이 없으면")
        class Context_without_product {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(eq(NOT_EXIST_ID)))
                        .willThrow(new ProductNotFoundException(NOT_EXIST_ID));
            }

            @DisplayName("404 상태코드, Not Found 상태를 응답한다.")
            @Test
            void It_responds_not_found() throws Exception {
                mockMvc.perform(get("/products/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("등록된 상품이 있으면")
        class Context_with_product {
            ProductResponseDto responseDto;

            @BeforeEach
            void setUp() {
                responseDto = new ProductResponseDto(PRODUCT_ID, NAME, MAKER, PRICE, IMAGE_URL);
                given(productService.getProduct(anyLong())).willReturn(responseDto);
            }

            @DisplayName("200 상태코드, OK 상태와 찾고자 하는 상품을 응답한다.")
            @Test
            void it_responds_ok_with_product() throws Exception {
                mockMvc.perform(get("/products/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(responseDto)))
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("maker").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imageUrl").exists());
            }
        }
    }

    @Nested
    @DisplayName("Patch /products/{id} 는")
    class Describe_updateProduct {
        ProductUpdateRequestDto requestDto;
        @Nested
        @DisplayName("갱신되는 상품이 없으면")
        class Context_without_product {

            @BeforeEach
            void setUp() {
                requestDto = new ProductUpdateRequestDto(NAME, MAKER, PRICE, IMAGE_URL);
                given(productService.updateProduct(eq(NOT_EXIST_ID), any(ProductUpdateRequestDto.class)))
                        .willThrow(new ProductNotFoundException(NOT_EXIST_ID));
            }

            @DisplayName("404 상태코드, Not Found 상태를 응답한다.")
            @Test
            void It_responds_not_found() throws Exception {
                mockMvc.perform(patch("/products/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("갱신되는 상품이 존재하면")
        class Context_with_product {

            @BeforeEach
            void setUp() {
                requestDto = new ProductUpdateRequestDto(NAME, MAKER, PRICE, IMAGE_URL);
                given(productService.updateProduct(anyLong(), any(ProductUpdateRequestDto.class)))
                        .willReturn(eq(PRODUCT_ID));
            }

            @DisplayName("200 상태코드, OK 상태와 갱신된 상품 id을 응답한다.")
            @Test
            void It_responds_product_id() throws Exception {
                mockMvc.perform(patch("/products/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayName("POST /products는")
    class Describe_createProduct {
        ProductSaveRequestDto requestDto;
        @Nested
        @DisplayName("등록할 상품이 주어지면")
        class Context_with_product {
            @BeforeEach
            void setUp() {
                requestDto = new ProductSaveRequestDto(NAME, MAKER, PRICE, IMAGE_URL);
                given(productService.createProduct(any(ProductSaveRequestDto.class)))
                        .willReturn(eq(PRODUCT_ID));
            }

            @DisplayName("201 상태코드, Created 상태를 응답한다.")
            @Test
            void It_responds_product_id() throws Exception {
                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                        .andExpect(status().isCreated());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /product/{id} 는")
    class Describe_deleteProduct {

        @Nested
        @DisplayName("삭제되는 상품이 없으면")
        class Context_without_product {
            @BeforeEach
            void setUp() {
                given(productService.deleteProduct(anyLong()))
                        .willThrow(new ProductNotFoundException(NOT_EXIST_ID));
            }

            @DisplayName("404 상태코드와 Not Found 상태를 응답한다.")
            @Test
            void It_responds_not_found() throws Exception {
                mockMvc.perform(delete("/products/{id}", anyLong()))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("삭제되는 상품이 존재하면")
        class Context_with_product {

            @BeforeEach
            void setUp() {
                given(productService.deleteProduct(anyLong())).willReturn(PRODUCT_ID);
            }

            @DisplayName("204 상태코드와 NO CONTENT 상태를 삭제된 상품 id를 응답한다.")
            @Test
            void It_responds_no_content_with_product() throws Exception {
                mockMvc.perform(delete("/products/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isNoContent());
            }
        }
    }
}
