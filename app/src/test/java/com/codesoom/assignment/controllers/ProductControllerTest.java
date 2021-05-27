package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스")
class ProductControllerTest {

    private final String PRODUCT_NAME = "Product";
    private final String UPDATE_PREFIX = "Update";
    private final String INVALID_CONTENT = "Invalid";
    private final Long EXISTED_ID = 1L;
    private final Long NOT_EXISTED_ID = -1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("GET /products 는")
    class DescribeGetProductList {

        @Nested
        @DisplayName("요청이 들어오면")
        class ContextWithRequest {

            @Test
            @DisplayName("HttpStatus 200 OK 를 응답한다")
            void itReturnsHttpStatusOK() throws Exception {
                mockMvc.perform(get("/products"))
                       .andExpect(status().isOk());
                verify(productService).getProductList();
            }
        }
    }

    @Nested
    @DisplayName("GET /products/{productId} 는")
    class DescribeGetDetailProduct {

        @Nested
        @DisplayName("만약 존재하는 productId 로 요청이 들어오면")
        class ContextWithExistedProductId {

            @BeforeEach
            void prepareProduct() {
                Product product = new Product();
                product.setName(PRODUCT_NAME);
                given(productService.getProduct(EXISTED_ID)).willReturn(product);
            }

            @Test
            @DisplayName("HttpStatus 200 OK 를 응답한다")
            void itReturnsHttpStatusOK() throws Exception {
                mockMvc.perform(get("/products/" + EXISTED_ID))
                       .andExpect(status().isOk());
                verify(productService).getProduct(EXISTED_ID);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 productId 로 요청이 들어오면")
        class ContextWithNotExistedProductId {

            @Test
            @DisplayName("HttpStatus 404 Not Found 를 응답한다")
            void itReturnsHttpStatusNotFound() throws Exception {
                given(productService.getProduct(NOT_EXISTED_ID)).willThrow(new ProductNotFoundException(NOT_EXISTED_ID));
                mockMvc.perform(get("/products/" + NOT_EXISTED_ID))
                       .andExpect(status().isNotFound());
                verify(productService).getProduct(NOT_EXISTED_ID);
            }
        }

        @Nested
        @DisplayName("만약 productId 가 null인 요청이 들어오면")
        class ContextWithNull {

            @Test
            @DisplayName("HttpStatus 4xx Client Error 를 응답한다")
            void itReturnsHttpStatusClientError() throws Exception {
                mockMvc.perform(get("/products/" + null))
                       .andExpect(status().is4xxClientError());
            }
        }
    }

    @Nested
    @DisplayName("POST /products 는")
    class DescribeCreateProduct {

        @Nested
        @DisplayName("만약 Product 로 매핑에 성공한 요청이 들어오면")
        class ContextWithValidProduct {

            @Test
            @DisplayName("HttpStatus 201 Created 를 응답한다")
            void itReturnsHttpStatusCreated() throws Exception {
                String productContent = objectMapper.writeValueAsString(new Product());
                mockMvc.perform(post("/products")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(productContent))
                       .andExpect(status().isCreated());
                verify(productService).createProduct(any(Product.class));
            }
        }

        @Nested
        @DisplayName("만약 product 로 매핑에 실패한 요청이 들어오면")
        class ContextWithInvalidProduct {

            @Test
            @DisplayName("HttpStatus 4xx Client Error 를 응답한다")
            void itReturnsHttpStatusClientError() throws Exception {
                mockMvc.perform(post("/products")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(INVALID_CONTENT))
                       .andExpect(status().is4xxClientError());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /products/{productId} 는")
    class DescribeUpdateProduct {

        @Nested
        @DisplayName("만약 존재하는 productId 로 요청이 들어오면")
        class ContextWithExistedProductId {

            @Test
            @DisplayName("HttpStatus 200 OK 를 응답한다")
            void itReturnsHttpStatusOK() throws Exception {
                Product product = new Product();
                product.setName(UPDATE_PREFIX + PRODUCT_NAME);
                String productContent = objectMapper.writeValueAsString(product);
                when(productService.updateProduct(eq(EXISTED_ID), any(Product.class))).thenReturn(product);
                mockMvc.perform(patch("/products/" + EXISTED_ID)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(productContent))
                       .andExpect(status().isOk())
                       .andDo(print());
                verify(productService).updateProduct(eq(EXISTED_ID), any(Product.class));
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 productId로 요청이 들어오면")
        class ContextWithNotExistedProductId {

            @Test
            @DisplayName("HttpStatus 404 Not Found 를 응답한다")
            void itReturnsHttpStatusNotFound() throws Exception {
                String productContent = objectMapper.writeValueAsString(new Product());
                when(productService.updateProduct(eq(NOT_EXISTED_ID), any(Product.class))).thenThrow(new ProductNotFoundException(NOT_EXISTED_ID));
                mockMvc.perform(patch("/products/" + NOT_EXISTED_ID)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(productContent))
                       .andExpect(status().isNotFound());
                verify(productService).updateProduct(eq(NOT_EXISTED_ID), any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("DELETE /products/{productId} 는")
    class DescribeDeleteProduct {

        @Nested
        @DisplayName("만약 존재하는 productId 로 요청이 들어오면")
        class ContextWithExistedProductId {

            @Test
            @DisplayName("HttpStatus 204 No Content 를 응답한다")
            void itReturnsHttpStatusNoContent() throws Exception {
                doNothing().when(productService).deleteProduct(EXISTED_ID);
                mockMvc.perform(delete("/products/" + EXISTED_ID))
                       .andExpect(status().isNoContent());
                verify(productService).deleteProduct(EXISTED_ID);
            }
        }

        @Nested
        @DisplayName("만약 존재하지 않는 productId 로 요청이 들어오면")
        class ContextWithNotExistedProductId {

            @Test
            @DisplayName("HttpStatus 404 Not Found 를 응답한다")
            void itReturnsHttpStatusNotFound() throws Exception {
                doThrow(new ProductNotFoundException(NOT_EXISTED_ID)).when(productService).deleteProduct(NOT_EXISTED_ID);
                mockMvc.perform(delete("/products/" + NOT_EXISTED_ID))
                       .andExpect(status().isNotFound());
                verify(productService).deleteProduct(NOT_EXISTED_ID);
            }
        }
    }
}
