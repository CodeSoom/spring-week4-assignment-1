package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductNotFoundException;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {
    //  V 고양이 장난감 목록 얻기 - GET /products
    //  V 고양이 장난감 상세 조회하기 - GET /products/{id}
    //  V 고양이 장난감 등록하기 - POST /products
    //    고양이 장난감 수정하기 - PATCH /products/{id}
    //    고양이 장난감 삭제하기 - DELETE /products/{id}

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private List<Product> products;
    private Product product1;
    private Product product2;
    private String contentProducts;
    private String contentProduct;

    @BeforeEach
    void setUp() throws JsonProcessingException {

        product1 = new Product(0L, "catTower", "samsung", 35900L, "https://thumbnail14.coupangcdn.com/thumbnails/remote/712x712ex/image/retail/images/451976858609946-e5186418-5f5e-4f4c-bccc-a59ac573d029.jpg");
        product2 = new Product(1L, "catBall", "love cat", 8000L, "http://mstatic1.e-himart.co.kr/contents/goods/00/05/96/13/20/0005961320__TB10__M_640_640.jpg");
        products = Arrays.asList(product1, product2);

        contentProducts = objectMapper.writeValueAsString(products);
        contentProduct = objectMapper.writeValueAsString(product1);
    }


    @Nested
    @DisplayName("/products 로 GET 요청을 보내면")
    class Describe_request_get_to_products_path {

        @BeforeEach
        void setUp() {
            given(productService.getProducts()).willReturn(products);
        }

        @Test
        @DisplayName("OK(200)과 저장되어 있는 product 리스트를 json 형식으로 리턴합니다.")
        void it_responses_200_and_product_list_by_json_type() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(contentProducts));
        }
    }

    @Nested
    @DisplayName("/products/{id} 로 GET 요청을 보내면")
    class Describe_request_get_to_products_id_path {

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재한다면")
        class Context_with_exist_id {

            private final Long EXIST_ID = 0L;

            @BeforeEach
            void setUp() {
                given(productService.getProductById(EXIST_ID)).willReturn(product1);
            }

            @Test
            @DisplayName("OK(200)과 해당하는 id의 product를 json 형식으로 리턴합니다.")
            void it_responses_200_and_product_by_json_type() throws Exception {
                mockMvc.perform(get("/products/" + EXIST_ID))
                        .andExpect(status().isOk())
                        .andExpect(content().string(contentProduct));
            }
        }

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재하지 않는다면")
        class Context_with_not_exist_id {

            private final Long NOT_EXIST_ID = 100L;

            @BeforeEach
            void setUp() {
                given(productService.getProductById(NOT_EXIST_ID)).willThrow(new ProductNotFoundException(NOT_EXIST_ID));
            }

            @Test
            @DisplayName("NOT_FOUND(404) 상태를 리턴합니다.")
            void it_responses_404() throws Exception {
                mockMvc.perform(get("/products/" + NOT_EXIST_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("/products 로 POST 요청을 보내면")
    class Describe_request_post_to_products_path {

        @BeforeEach
        void setUp() {
            given(productService.createProduct(any(Product.class))).willReturn(product1);
        }

        @Test
        @DisplayName("Created(201)과 요청에 따라 생성한 product를 json 형식으로 리턴합니다.")
        void it_responses_created_and_product_by_json_type() throws Exception {
            mockMvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(contentProduct))
                    .andExpect(status().isCreated())
                    .andExpect(content().string(contentProduct));
        }
    }


    @Nested
    @DisplayName("/products/{id} 로 PATCH 요청을 보내면")
    class Describe_request_patch_to_products_id_path {

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재한다면")
        class Context_with_exist_id {

            private final Long EXIST_ID = 0L;

            @BeforeEach
            void setUp() {
                given(productService.updateProduct(eq(EXIST_ID), any(Product.class))).willReturn(product1);
            }

            @Test
            @DisplayName("OK(200)과 해당하는 id의 product를 주어진 요청대로 수정하고 json 형식으로 리턴합니다.")
            void it_responses_200_and_updated_product_by_json_type() throws Exception {
                mockMvc.perform(patch("/products/" + EXIST_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(contentProduct))
                        .andExpect(status().isOk())
                        .andExpect(content().string(contentProduct));
            }
        }

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재하지 않는다면")
        class Context_with_not_exist_id {

            private final Long NOT_EXIST_ID = 100L;

            @BeforeEach
            void setUp() {
                given(productService.updateProduct(eq(NOT_EXIST_ID), any(Product.class))).willThrow(new ProductNotFoundException(NOT_EXIST_ID));
            }

            @Test
            @DisplayName("NOT_FOUND(404) 상태를 리턴합니다.")
            void it_responses_404() throws Exception {
                mockMvc.perform(patch("/products/" + NOT_EXIST_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(contentProduct))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("/products/{id} 로 DELETE 요청을 보내면")
    class Describe_request_delete_to_products_id_path {

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재한다면")
        class Context_with_exist_id {

            private final Long EXIST_ID = 0L;

            @BeforeEach
            void setUp() {
                willDoNothing().given(productService).deleteById(EXIST_ID);
            }

            @Test
            @DisplayName("OK(200) 상태를 리턴합니다.")
            void it_responses_200() throws Exception {
                mockMvc.perform(delete("/products/" + EXIST_ID))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("만약 조회하는 id의 product가 존재하지 않는다면")
        class Context_with_not_exist_id {

            private final Long NOT_EXIST_ID = 100L;

            @BeforeEach
            void setUp() {
                willThrow(new ProductNotFoundException(NOT_EXIST_ID)).given(productService).deleteById(NOT_EXIST_ID);
            }

            @Test
            @DisplayName("NOT_FOUND(404) 상태를 리턴합니다.")
            void it_responses_404() throws Exception {
                mockMvc.perform(delete("/products/" + NOT_EXIST_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }

}
