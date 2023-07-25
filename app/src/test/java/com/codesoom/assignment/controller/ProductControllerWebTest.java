package com.codesoom.assignment.controller;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.adapter.in.web.ProductCommand;
import com.codesoom.assignment.adapter.out.persistence.ProductMapper;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.adapter.in.web.ProductController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductController.class)
class ProductControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private List<Product> products;
    private Product product;
    private ProductCommand productCommand;

    private static final Long EXISTING_PRODUCT_ID = 1L;
    private static final Long NOT_EXISTING_PRODUCT_ID = 100L;
    private static final String PRODUCT_NAME = "test name";
    private static final String PRODUCT_MAKER = "test maker";
    private static final int PRODUCT_PRICE = 1000;
    private static final String PRODUCT_IMAGE_URL = "test image url";
    private static final String NEW_PRODUCT_NAME = "updated name";

    @BeforeEach
    void setup() {
        products = new ArrayList<>();

        productCommand = new ProductCommand(PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_IMAGE_URL);
        product = new Product(EXISTING_PRODUCT_ID, PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_IMAGE_URL);
    }

    @Nested
    @DisplayName("GET /products")
    class Describe_get_list_request {
        @Nested
        @DisplayName("장난감이 있으면")
        class Context_with_products {

            @BeforeEach
            void setup() {
                products.add(product);
                given(productService.getProducts()).willReturn(products);
            }

            @Test
            @DisplayName("상태코드 200을 응답하고, 장난감 리스트를 리턴한다")
            void It_returns_200_and_products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(PRODUCT_NAME)))
                        .andExpect(content().string(containsString(PRODUCT_MAKER)))
                        .andExpect(content().string(containsString(PRODUCT_IMAGE_URL)))
                        .andExpect(content().string(containsString(String.valueOf(PRODUCT_PRICE))))
                        .andExpect(content().string(containsString(String.valueOf(EXISTING_PRODUCT_ID))));
            }
        }
    }

    @Nested
    @DisplayName("GET /products/{id}")
    class Describe_get_detail_request {
        @Nested
        @DisplayName("장난감을 찾을 수 있으면")
        class Context_with_existing_product_id {

            @BeforeEach
            void setup() {
                given(productService.getProduct(EXISTING_PRODUCT_ID)).willReturn(product);
            }

            @Test
            @DisplayName("상태코드 200을 응답하고, id로 찾은 장난감을 리턴한다")
            void It_returns_200_and_product_found_by_id() throws Exception {
                mockMvc.perform(get("/products/" + EXISTING_PRODUCT_ID))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("name").value(PRODUCT_NAME))
                        .andExpect(jsonPath("maker").value(PRODUCT_MAKER))
                        .andExpect(jsonPath("imageUrl").value(PRODUCT_IMAGE_URL))
                        .andExpect(jsonPath("price").value(PRODUCT_PRICE))
                        .andExpect(jsonPath("id").value(EXISTING_PRODUCT_ID));
            }
        }

        @Nested
        @DisplayName("장난감을 찾을 수 없으면")
        class Context_with_not_existing_product_id {
            @BeforeEach
            void setup() {
                given(productService.getProduct(NOT_EXISTING_PRODUCT_ID)).willThrow(new ProductNotFoundException(NOT_EXISTING_PRODUCT_ID));
            }

            @Test
            @DisplayName("상태코드 404를 응답한다")
            void It_returns_404() throws Exception {
                mockMvc.perform(get("/products/" + NOT_EXISTING_PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("POST /products")
    class Describe_post_request {
        @Nested
        @DisplayName("새로운 장난감을 추가하면")
        class Context_when_add_product {

            @BeforeEach
            void setup() {
                given(productService.createProduct(any(Product.class))).willReturn(product);
            }

            @Test
            @DisplayName("상태코드 201을 응답하고, 추가된 장난감을 리턴한다")
            void It_returns_200_and_created_product() throws Exception {
                mockMvc.perform(post("/products/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(productCommand)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("name").value(PRODUCT_NAME))
                        .andExpect(jsonPath("maker").value(PRODUCT_MAKER))
                        .andExpect(jsonPath("imageUrl").value(PRODUCT_IMAGE_URL))
                        .andExpect(jsonPath("price").value(PRODUCT_PRICE))
                        .andExpect(jsonPath("id").value(EXISTING_PRODUCT_ID));
            }
        }
    }

    @Nested
    @DisplayName("PATCH /products/{id}")
    class Describe_patch_request {
        private ProductCommand source;

        @Nested
        @DisplayName("장난감을 찾을 수 있으면")
        class Context_with_existing_product_id {

            @BeforeEach
            void setup() {
                source = new ProductCommand();
                source.setName(NEW_PRODUCT_NAME);
                product.setName(source.getName());
                given(productService.updateProduct(eq(EXISTING_PRODUCT_ID), any(Product.class))).willReturn(product);
            }

            @Test
            @DisplayName("상태코드 200을 응답하고, 수정된 장난감을 리턴한다")
            void It_returns_200_and_updated_product() throws Exception {
                mockMvc.perform(patch("/products/" + EXISTING_PRODUCT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(source)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").value(EXISTING_PRODUCT_ID))
                        .andExpect(jsonPath("name").value(NEW_PRODUCT_NAME));
            }
        }

        @Nested
        @DisplayName("장난감을 찾을 수 없으면")
        class Context_with_not_existing_product_id {

            @BeforeEach
            void setup() {
                source = new ProductCommand();
                source.setName(NEW_PRODUCT_NAME);
                given(productService.updateProduct(eq(NOT_EXISTING_PRODUCT_ID), any(Product.class)))
                        .willThrow(new ProductNotFoundException(NOT_EXISTING_PRODUCT_ID));
            }

            @Test
            @DisplayName("상태코드 404를 응답한다")
            void It_returns_404() throws Exception {
                mockMvc.perform(patch("/products/" + NOT_EXISTING_PRODUCT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(source)))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /products/{id}")
    class Describe_delete_request {

        @Nested
        @DisplayName("장난감을 찾을 수 있으면")
        class Context_with_existing_product_id {

            @Test
            @DisplayName("상태코드 204를 응답하고, 다시 DELETE를 요청하면 404를 응답한다")
            void It_returns_204() throws Exception {
                mockMvc.perform(delete("/products/" + EXISTING_PRODUCT_ID))
                        .andExpect(status().isNoContent());

                Mockito.doThrow(ProductNotFoundException.class).when(productService).deleteProduct(EXISTING_PRODUCT_ID);

                mockMvc.perform(delete("/products/" + EXISTING_PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("장난감을 찾을 수 없으면")
        class Context_with_not_existing_product_id {

            @BeforeEach
            void setup() {
                Mockito.doThrow(ProductNotFoundException.class).when(productService).deleteProduct(NOT_EXISTING_PRODUCT_ID);
            }

            @Test
            @DisplayName("상태코드 404를 응답한다")
            void It_returns_404() throws Exception {
                mockMvc.perform(delete("/products/" + NOT_EXISTING_PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
