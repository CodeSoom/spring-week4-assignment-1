package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductRequest;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DisplayName("ProductController 클래스")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private final Long existingId = 1L;
    private final Long notExistingId = 100L;

    private List<ProductResponse> products;
    private ProductRequest productRequest;
    private ProductResponse productResponse1;
    private ProductResponse productResponse2;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest();
        productRequest.setName("장난감");
        productRequest.setMaker("장난감 메이커");
        productRequest.setPrice(10000);
        productRequest.setImageUrl("url");

        products = new ArrayList<>();

        productResponse1 = new ProductResponse(
                Product.builder()
                        .id(1L)
                        .name("장난감1")
                        .maker("장난감 메이커1")
                        .price(10000)
                        .imageUrl("url1")
                        .build()
        );

        productResponse2 = new ProductResponse(
                Product.builder()
                        .id(2L)
                        .name("장난감2")
                        .maker("장난감 메이커2")
                        .price(20000)
                        .imageUrl("url2")
                        .build()
        );
    }

    @Nested
    @DisplayName("GET 요청은")
    class Describe_GET {
        @Nested
        @DisplayName("저장된 상품이 여러개 있다면")
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
            void it_responds_all_product_list_and_status_code_200() throws Exception {
                mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(products.size())))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("저장된 상품이 없다면")
        class Context_without_products {
            @BeforeEach
            void setUp() {
                given(productService.getProducts())
                        .willReturn(List.of());
            }

            @Test
            @DisplayName("비어있는 상품 목록과 상태코드 200을 응답한다")
            void it_responds_empty_product_list_and_status_code_200() throws Exception {
                mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(0)))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("존재하는 상품 id가 주어진다면")
        class Context_with_an_existing_product_id {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(existingId))
                        .willReturn(productResponse1);
            }

            @Test
            @DisplayName("찾은 상품과 상태코드 200을 응답한다")
            void it_responds_the_found_product_and_status_code_200() throws Exception {
                mockMvc.perform(get("/products/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("maker").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imageUrl").exists())
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품 id가 주어진다면")
        class Context_with_not_existing_product_id {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(notExistingId))
                        .willThrow(new ProductNotFoundException());
            }

            @Test
            @DisplayName("에러메시지와 상태코드 404를 응답한다")
            void it_responds_the_error_message_and_status_code_404() throws Exception {
                mockMvc.perform(get("/products/{id}", notExistingId)
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
        @DisplayName("상품이 주어진다면")
        class Context_with_product {
            @BeforeEach
            void setUp() {
                given(productService.createProduct(any(ProductRequest.class)))
                        .willReturn(productResponse1);
            }

            @Test
            @DisplayName("생성된 상품과 상태코드 201을 응답한다")
            void it_responds_the_created_product_and_status_code_201() throws Exception {
                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("maker").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imageUrl").exists())
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
                given(productService.updateProduct(eq(existingId), any(ProductRequest.class)))
                        .willReturn(productResponse1);
            }

            @Test
            @DisplayName("수정된 상품과 상태코드 200을 응답한다")
            void it_responds_the_updated_product_and_status_code_200() throws Exception {
                mockMvc.perform(patch("/products/{id}", existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("maker").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imageUrl").exists())
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품 id가 주어진다면")
        class Context_with_not_existing_product_id {
            @BeforeEach
            void setUp() {
                given(productService.updateProduct(eq(notExistingId), any(ProductRequest.class)))
                        .willThrow(new ProductNotFoundException());
            }

            @Test
            @DisplayName("에러메시지와 상태코드 404를 응답한다")
            void it_responds_the_error_message_and_status_code_404() throws Exception {
                mockMvc.perform(patch("/products/{id}", notExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
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
                mockMvc.perform(delete("/products/{id}", existingId)
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
                        .given(productService).deleteProduct(notExistingId);
            }

            @Test
            @DisplayName("에러메시지와 상태코드 404를 응답한다")
            void it_responds_the_error_message_and_status_code_404() throws Exception {
                mockMvc.perform(delete("/products/{id}", notExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").doesNotExist())
                        .andExpect(jsonPath("message").exists())
                        .andExpect(status().isNotFound());
            }
        }
    }

}
