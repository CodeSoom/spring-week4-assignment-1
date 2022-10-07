package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.CommandProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private CommandProductService commandProductService;

    private ObjectMapper objectMapper = new ObjectMapper();

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
                givenProducts.forEach(product -> commandProductService.createProduct(product));
            }

            @AfterEach
            void after() {
                commandProductService.deleteAll();
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
            @DisplayName("빈 리스트를 리턴한다")
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
                givenProduct = commandProductService.createProduct(
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
                commandProductService.deleteAll();
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

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_createProduct {
        @Nested
        @DisplayName("product 가 주어진다면")
        class Context_with_product {
            private Product requestProduct;
            private String requestBody;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                requestProduct = Product.builder()
                        .id(null)
                        .name("장난감1")
                        .maker("M")
                        .price(1000)
                        .imageUrl("http://images/1")
                        .build();
                requestBody = objectMapper.writeValueAsString(requestProduct);

            }

            @AfterEach
            void after() {
                commandProductService.deleteAll();
            }

            @Test
            @DisplayName("product 를 저장하고 리턴한다")
            void it_returns_product() throws Exception {
                mockMvc.perform(post("/products")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value(requestProduct.getName()))
                        .andExpect(jsonPath("$.maker").value(requestProduct.getMaker()))
                        .andExpect(jsonPath("$.price").value(requestProduct.getPrice()))
                        .andExpect(jsonPath("$.imageUrl").value(requestProduct.getImageUrl()));
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_updateProduct {
        @Nested
        @DisplayName("요청하는 product의 일부 필드가 없는 경우")
        class Context_with_partial_value {
            private Product requestProduct;
            private Product savedProduct;
            private String requestBody;


            @BeforeEach
            void setUp() throws JsonProcessingException {
                requestProduct = Product.builder()
                        .id(null)
                        .name("장난감1")
                        .maker(null)
                        .price(1000)
                        .imageUrl(null)
                        .build();

                savedProduct = commandProductService.createProduct(
                        Product.builder()
                                .id(null)
                                .name("장난감1")
                                .maker("M")
                                .price(2000)
                                .imageUrl("http://image.com")
                                .build()
                );
                requestBody = objectMapper.writeValueAsString(requestProduct);

            }

            @Test
            @DisplayName("필드가 없는 경우 수정하지 않고, 값이 있는 경우 수정 후 리턴한다")
            void it_returns_partial_updated_product() throws Exception {
                mockMvc.perform(patch("/products/" + savedProduct.getId())
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(requestProduct.getName()))
                        .andExpect(jsonPath("$.maker").value(savedProduct.getMaker()))
                        .andExpect(jsonPath("$.price").value(requestProduct.getPrice()))
                        .andExpect(jsonPath("$.imageUrl").value(savedProduct.getImageUrl()));
            }
        }

        @Nested
        @DisplayName("요청하는 product 의 필드가 모두 있는 경우")
        class Context_with_full_value {
            private Product requestProduct;
            private Product savedProduct;
            private String requestBody;


            @BeforeEach
            void setUp() throws JsonProcessingException {
                requestProduct = Product.builder()
                        .id(null)
                        .name("장난감1after")
                        .maker("K")
                        .price(3000)
                        .imageUrl("http://image10.com")
                        .build();

                savedProduct = commandProductService.createProduct(
                        Product.builder()
                                .id(null)
                                .name("장난감1")
                                .maker("M")
                                .price(2000)
                                .imageUrl("http://image.com")
                                .build());

                requestBody = objectMapper.writeValueAsString(requestProduct);

            }

            @Test
            @DisplayName("모든 필드를 수정 후 리턴한다")
            void it_returns_updated_product() throws Exception {
                mockMvc.perform(patch("/products/" + savedProduct.getId())
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(requestProduct.getName()))
                        .andExpect(jsonPath("$.maker").value(requestProduct.getMaker()))
                        .andExpect(jsonPath("$.price").value(requestProduct.getPrice()))
                        .andExpect(jsonPath("$.imageUrl").value(requestProduct.getImageUrl()));
            }
        }
    }

    @Nested
    @DisplayName("저장되어 있지 않은 product 의 id로 요청한 경우")
    class Context_with_non_existence_id {
        private Product requestProduct;
        private String requestBody;

        @BeforeEach
        void setUp() throws JsonProcessingException {
            requestProduct = Product.builder()
                    .id(null)
                    .name("장난감1after")
                    .maker("K")
                    .price(3000)
                    .imageUrl("http://image10.com")
                    .build();
            requestBody = objectMapper.writeValueAsString(requestProduct);

        }

        @Test
        @DisplayName("제품을 찾을 수 없는 예외를 던진다")
        void it_throws_exception() throws Exception {
            mockMvc.perform(patch("/products/" + INVALID_PRODUCT_ID)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("저장되어있는 product 의 id가 주어진다면 ")
        class Context_with_existing_product_id {
            private Product savedProduct;

            @BeforeEach
            void setUp() {
                savedProduct = commandProductService.createProduct(
                        Product.builder()
                                .id(null)
                                .name("장난감1after")
                                .maker("K")
                                .price(3000)
                                .imageUrl("http://image10.com")
                                .build()
                );
            }

            @Test
            @DisplayName("product 를 삭제한다")
            void it_delete_product() throws Exception {
                mockMvc.perform(delete("/products/" + savedProduct.getId()))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("저장되어 있지 않는 product 의 id가 주어진다면 ")
        class Context_with_non_existence_product_id {

            @Test
            @DisplayName("제품을 찾을 수 없는 예외를 던진다")
            void it_delete_product() throws Exception {
                mockMvc.perform(delete("/products/" + INVALID_PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
