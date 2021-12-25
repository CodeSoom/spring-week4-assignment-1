package com.codesoom.controllers;

import com.codesoom.application.ProductService;
import com.codesoom.domain.Product;
import com.codesoom.exception.ProductNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@DisplayName("ProductController Web 테스트")
class ProductControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String PRODUCT_NAME = "고양이 장남감";
    private static final String PRODUCT_MAKER = "코드숨";
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(10000);
    private static final String PRODUCT_IMAGE_URL = "test.jpg";


    @Nested
    @DisplayName("GET 요청은")
    class Describe_get {
        @Nested
        @DisplayName("등록된 Product가 있다면")
        class Context_has_product {
            final int productCount = 3;

            @BeforeEach
            void setUp() {
                List<Product> products = new ArrayList<>();

                for (int i = 1; i < productCount; i++) {
                    productController.create(getProduct(i));
                    products.add(getProduct(i));
                }
                given(productService.getProducts()).willReturn(products);
            }

            @Test
            @DisplayName("전체 리스트를 리턴한다.")
            void it_return_list() throws Exception {
                mockMvc.perform(get("/products")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(PRODUCT_NAME + 1)));

                verify(productController.list());
            }
        }

        @Nested
        @DisplayName("등록된 Product가 없다면")
        class Context_none_product {
            @BeforeEach
            void setUp() {
                List<Product> products = productController.list();
                products.forEach(product -> productController.delete(product.getId()));

                given(null).willReturn(null);
            }

            @Test
            @DisplayName("빈 리스트를 리턴한다.")
            void it_return_list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("[]")));

                verify(productController.list());
            }
        }

        @Nested
        @DisplayName("등록된 id 값이 주어졌을때")
        class Context_when_product_is_exist {
            Product product;

            @BeforeEach
            void setUp() {
                product = getProduct(0);

                productController.create(getProduct(1));
                given(productController.view(1L)).willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("등록된 Product 정보를 리턴한다.")
            void it_return_product() throws Exception {

                mockMvc.perform(get("/products/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(PRODUCT_NAME + 1)))
                        .andExpect(content().string(containsString(PRODUCT_MAKER + 1)))
                        .andExpect(content().string(containsString(String.valueOf(PRODUCT_PRICE))))
                        .andExpect(content().string(containsString(PRODUCT_IMAGE_URL)));

                verify(productController.view(1L));
            }
        }

        @Nested
        @DisplayName("id에 해당하는 Product가 존재하지 않으면")
        class Context_when_product_isnot_exist {

            @BeforeEach
            void setUp() {
                given(productController.view(0L))
                        .willThrow(new ProductNotFoundException("요청한 0의 Product를 찾지 못했습니다."));
            }

            @Test
            @DisplayName("Product를 찾을 수 없다는 예외를 던진다.")
            void it_throw_ProductNotFoundException() throws Exception {
                mockMvc.perform(get("/products/0"))
                        .andExpect(status().isNotFound());

                verify(productController.view(0L));
            }
        }
    }

    @Nested
    @DisplayName("POST 요청은")
    class Describe_post {
        String product;

        @Nested
        @DisplayName("Product가 주어진다면")
        class Context_with_new_product {
            @BeforeEach
            void setUp() throws JsonProcessingException {
                product = objectMapper.writeValueAsString(getProduct(1));
                given(productController.create(getProduct(1)))
                        .willReturn(getProduct(1));
            }

            @Test
            @DisplayName("Product를 저장하고 Created를  응답한다.")
            void it_return_status_created() throws Exception {

                mockMvc.perform(post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(product))
                        .andExpect(status().isCreated());

                verify(productController).create(any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("PATCH 요청은")
    class Describe_patch {
        String product;

        @BeforeEach
        void setUp() throws JsonProcessingException {
            productController.create(getProduct(1));
            product = objectMapper.writeValueAsString(getProduct(100));
        }

        @Nested
        @DisplayName("등록된 id가 주어진다면")
        class Context_when_product_is_exist {
            @Test
            @DisplayName("id에 해당하는 Product 정보를 수정하고 리턴한다.")
            void it_fix_product_return() throws Exception {
                mockMvc.perform(patch("/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(product))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("등록되지않은 id가 주어진다면")
        class Context_when_product_isnot_exist {
            @Test
            @DisplayName("id에 해당하는 Product를 찾을 수 없어 수정할 수 없다고 예외를 던진다.")
            void it_throw_ProductNotFoundException() throws Exception {
                mockMvc.perform(patch("/products/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(product))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("DELETE 요청은")
    class Describe_delete {
        @BeforeEach
        void setUp() {
            productController.create(getProduct(1));
        }

        @Nested
        @DisplayName("등록된 id가 주어진다면")
        class Context_when_product_is_exist {
            @Test
            @DisplayName("id에 해당하는 Product를 삭제한다.")
            void it_return_products() throws Exception {
                mockMvc.perform(delete("/products/1"))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("등록되지않은 id가 주어진다면")
        class Context_when_product_isnot_exist {
            @Test
            @DisplayName("id에 해당하는 Product를 찾을 수 없어 삭제할 수 없다고 예외를 던진다.")
            void it_return_products() throws Exception {
                mockMvc.perform(delete("/products/0"))
                        .andExpect(status().isNotFound());
            }
        }
    }

    private Product getProduct(int number) {
        Product product = new Product();

        product.setName(PRODUCT_NAME + number);
        product.setMaker(PRODUCT_MAKER + number);
        product.setPrice(PRODUCT_PRICE);
        product.setImageUrl(PRODUCT_IMAGE_URL);

        return product;
    }
}
