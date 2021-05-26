package com.codesoom.assignment.controller;

import com.codesoom.assignment.error.exception.ProductNotFoundException;
import com.codesoom.assignment.product.ProductFixtures;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("ProductController 클래스의")
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerMockTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_createProduct {

        @Nested
        @DisplayName("만약 '/products' 경로로 상품 데이터와 함께 POST 요청된다면")
        class Context_POST_products_with_one_product {
            private final Product product = ProductFixtures.laser();
            private final MockHttpServletRequestBuilder requestBuilder =
                    post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"" + product.getName() + "\"}");

            @BeforeEach
            void mocking() {
                given(productService.create(any(Product.class)))
                        .willReturn(product);
            }

            @Test
            @DisplayName("새로운 상품을 생성한 후 생성된 상품을 반환한다")
            void It_creates_the_product_and_returns_it() throws Exception {
                // when
                mockMvc.perform(requestBuilder)
                       // then
                       .andExpect(status().isCreated())
                       .andExpect(jsonPath("$.name",
                                           containsString(product.getName())))
                       .andExpect(jsonPath("$.maker",
                                           containsString(product.getMaker())))
                       .andExpect(jsonPath("$.price",
                                           is(product.getPrice()
                                                     .intValue())));

                verify(productService).create(any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {

        @Nested
        @DisplayName("만약 '/products/{id}' 경로로 등록되어 있는 상품 식별자와 함께 GET 요청된다면")
        class Context_GET_products_with_valid_product_id {
            private final Product product = ProductFixtures.laser();
            private final MockHttpServletRequestBuilder requestBuilder =
                    get("/products/" + product.getId())
                            .contentType(MediaType.APPLICATION_JSON);

            @BeforeEach
            void mocking() {
                given(productService.get(product.getId()))
                        .willReturn(product);
            }

            @Test
            @DisplayName("주어진 식별자의 상품을 반환한다")
            void It_returns_one_product_by_id() throws Exception {
                // when
                mockMvc.perform(requestBuilder)
                       // then
                       .andExpect(status().isOk())
                       .andExpect(jsonPath("$.name", containsString(product.getName())))
                       .andExpect(jsonPath("$.maker",
                                           containsString(product.getMaker())))
                       .andExpect(jsonPath("$.price",
                                           is(product.getPrice()
                                                     .intValue())));

                verify(productService).get(product.getId());
            }
        }

        @Nested
        @DisplayName("만약 '/products/{id}' 경로로 등록되어 있지 않는 상품 식별자와 함께 GET 요청된다면")
        class Context_GET_products_with_invalid_product_id {
            private final Long invalidProductId = 2L;
            private final MockHttpServletRequestBuilder requestBuilder =
                    get("/products/" + invalidProductId);
            private final String productNotFoundMessage = "Product Not Found";

            @BeforeEach
            void mocking() {
                given(productService.get(invalidProductId))
                        .willThrow(new ProductNotFoundException());
            }

            @Test
            @DisplayName("주어진 식별자의 상품을 반환한다")
            void It_returns_one_product_by_id() throws Exception {
                // when
                mockMvc.perform(requestBuilder)
                       // then
                       .andExpect(status().isNotFound())
                       .andExpect(jsonPath("$.message",
                                           containsString(productNotFoundMessage)));

                verify(productService).get(invalidProductId);
            }
        }
    }


    @Nested
    @DisplayName("listProduct 메서드는")
    class Describe_listProduct {

        @Nested
        @DisplayName("만약 '/products' 경로로 GET 요청된다면")
        class Context_GET_products {
            final int totalProductCount = 2;
            private final MockHttpServletRequestBuilder requestBuilder =
                    get("/products")
                            .contentType(MediaType.APPLICATION_JSON);
            private final Product product = ProductFixtures.helm();

            @BeforeEach
            void mocking() {
                final List<Product> products = new ArrayList<>();
                for (int index = 1; index <= totalProductCount; index++) {
                    products.add(product);
                }

                given(productService.list())
                        .willReturn(products);
            }

            @Test
            @DisplayName("등록되어 있는 상품 목록을 반환한다")
            void It_returns_one_product_by_id() throws Exception {
                // when
                mockMvc.perform(requestBuilder)
                       // then
                       .andExpect(status().isOk())
                       .andExpect(jsonPath("$[*]",
                                           hasSize(totalProductCount)))
                       .andExpect(jsonPath("$[0].name",
                                           is(product.getName())))
                       .andExpect(jsonPath("$[0].maker",
                                           containsString(product.getMaker())))
                       .andExpect(jsonPath("$[0].price",
                                           is(product.getPrice()
                                                     .intValue())));

                verify(productService).list();
            }
        }
    }

}
