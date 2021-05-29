package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스 Web")
public class ProductControllerWebTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private List<Product> products;
    private Product registeredProduct;

    final static Long EXISTENT_ID = 1L;
    final static Long NON_EXISTENT_ID = -1L;

    @BeforeEach
    void setupFixture() {
        objectMapper = new ObjectMapper();
        products = new ArrayList<>();
        registeredProduct = generateProduct(EXISTENT_ID);

        given(productService.getAllProducts())
                .willReturn(products);
        given(productService.getProduct(EXISTENT_ID))
                .willReturn(registeredProduct);
        willThrow(new EmptyResultDataAccessException(Math.toIntExact(NON_EXISTENT_ID)))
                .given(productService)
                .getProduct(NON_EXISTENT_ID);
        willThrow(new EmptyResultDataAccessException(Math.toIntExact(NON_EXISTENT_ID)))
                .given(productService)
                .deleteProduct(NON_EXISTENT_ID);
        willThrow(new EmptyResultDataAccessException(Math.toIntExact(NON_EXISTENT_ID)))
                .given(productService)
                .updateProduct(eq(NON_EXISTENT_ID), any(Product.class));
    }

    @Nested
    @DisplayName("'/products'에 GET 요청시")
    class Describe_of_GET_products {

        private String url;

        @BeforeEach
        void setUp() {
            url = "/products";
        }

        @Nested
        @DisplayName("등록된 상품이 없다면")
        class Context_of_empty_products {

            @BeforeEach
            void setUp() {
                products.clear();
            }

            @Test
            @DisplayName("빈 상품목록을 응답한다")
            void it_returns_empty_products() throws Exception {
                mockMvc.perform(get(url))
                        .andExpect(status().isOk())
                        .andExpect(content().string("[]"));
            }
        }

        @Nested
        @DisplayName("등록된 상품이 있다면")
        class Context_of_not_empty_products {

            private Product product;
            private String productsJSON;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                product = generateProduct(1L);
                products.add(product);

                productsJSON = objectMapper.writeValueAsString(products);
            }

            @Test
            @DisplayName("상품 목록을 응답한다")
            void it_returns_products() throws Exception {
               mockMvc.perform(get(url))
                       .andExpect(status().isOk())
                       .andExpect(content().json(productsJSON));
            }
        }
    }

    @Nested
    @DisplayName("'/products/{id}'에 GET 요청시")
    class Describe_of_GET_products_with_id {

        private String url;

        @BeforeEach
        void setUp() {
            url = "/products/";
        }

        @Nested
        @DisplayName("등록된 상품 id를 전달하면")
        class Context_of_existent_id {

            @BeforeEach
            void setUp() {
                url += EXISTENT_ID;
            }

            @Test
            @DisplayName("200 상태코드와 상품을 응답한다")
            void it_returns_ok_status_with_a_product() throws Exception {
                String productJSON = objectMapper.writeValueAsString(registeredProduct);

                mockMvc.perform(get(url))
                        .andExpect(status().isOk())
                        .andExpect(content().json(productJSON));
            }
        }

        @Nested
        @DisplayName("등록되지 않은 상품 id를 전달하면")
        class Context_of_non_existent_id {

            @BeforeEach
            void setUp() {
                url += NON_EXISTENT_ID;
            }

            @Test
            @DisplayName("404 상태코드를 응답한다")
            void it_returns_not_found_status() throws Exception {
               mockMvc.perform(get(url))
                       .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("'/products'에 POST 요청시")
    class Describe_of_POST_products {

        private String url;

        @BeforeEach
        void setUp() {
            url = "/products";
        }

        @Nested
        @DisplayName("상품을 성공적으로 추가했다면")
        class Context_of_success {

            private String productJSON;
            private ResultActions response;

            @BeforeEach
            void setUp() throws Exception {
                given(productService.addProduct(any(Product.class)))
                        .will(invocation -> invocation.getArgument(0));

                productJSON = objectMapper.writeValueAsString(registeredProduct);

                response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJSON));
            }

            @Test
            @DisplayName("201 상태코드와 상품을 응답한다")
            void it_returns_201_status_code_with_product() throws Exception {
                verify(productService).addProduct(registeredProduct);

                response.andExpect(status().isCreated())
                        .andExpect(content().json(productJSON));
            }
        }
    }

    @Nested
    @DisplayName("'/products{id}에 PATCH 요청시")
    class Describe_of_patch {

        private String url;
        private Product sourceProduct;
        private String productJSON;

        @BeforeEach
        void setUp() throws JsonProcessingException {
            url = "/products/";

            sourceProduct = generateProduct(42L);
            sourceProduct.setId(EXISTENT_ID);
            productJSON = objectMapper.writeValueAsString(sourceProduct);
        }

        @Nested
        @DisplayName("존재하는 상품의 id를 전달하면")
        class Context_of_existent_id {

            private ResultActions response;

            @BeforeEach
            void setUp() throws Exception {
                url += EXISTENT_ID;


                given(productService.updateProduct(EXISTENT_ID, sourceProduct))
                        .willReturn(sourceProduct);

                response = mockMvc.perform(patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJSON));
            }

            @Test
            @DisplayName("200 상태코드와 갱신한 상품 정보를 응답한다")
            void it_returns_200_status_code_and_product() throws Exception {
                response.andExpect(status().isOk())
                        .andExpect(content().json(productJSON));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품의 id를 전달하면")
        class Describe_of_non_existent_id {

            private ResultActions response;

            @BeforeEach
            void setUp() throws Exception {
                url += NON_EXISTENT_ID;

                response = mockMvc.perform(patch(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJSON));
            }

            @Test
            @DisplayName("404 상태코드를 응답한다")
            void it_returns_404_status_code() throws Exception {
                response.andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("'/products/{id}'에 DELETE 요청시")
    class Describe_of_DELETE_products {

        private String url;

        @BeforeEach
        void setUp() {
            url = "/products/";
        }

        @Nested
        @DisplayName("존재하는 상품 id를 전달하면")
        class Context_of_existent_id {

            private ResultActions response;

            @BeforeEach
            void setUp() throws Exception {
                url += EXISTENT_ID;

                response = mockMvc.perform(delete(url));
            }

            @Test
            @DisplayName("204 상태코드를 응답한다")
            void it_returns_204_status_code() throws Exception {
                verify(productService).deleteProduct(EXISTENT_ID);

                response.andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품 id를 전달하면")
        class Context_of_non_existent_id {

            private ResultActions response;

            @BeforeEach
            void setUp() throws Exception {
                url += NON_EXISTENT_ID;

                response = mockMvc.perform(delete(url));

                willThrow(new EmptyResultDataAccessException(Math.toIntExact(NON_EXISTENT_ID)))
                        .given(productService).deleteProduct(NON_EXISTENT_ID);
            }

            @Test
            @DisplayName("404 상태코드를 응답한다")
            void it_returns_404_status_code() throws Exception {
                response.andExpect(status().isNotFound());
            }
        }
    }

    private Product generateProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Name" + id);
        product.setMaker("Maker " + id);
        product.setPrice(id * 100L);
        product.setImageUrl("product" + id + ".jpg");
        return product;
    }
}
