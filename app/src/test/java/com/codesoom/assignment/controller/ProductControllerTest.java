package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.service.ProductService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스")
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    private static final String PRODUCTS_URI = "/products";
    private static final String PRODUCTS_URI_SLASH = "/products/";

    @Nested
    @DisplayName("list() 메소드는")
    class Describe_list {
        @Nested
        @DisplayName("등록된 Product들이 존재하면")
        class Context_has_product {
            final int givenProductCnt = 5;

            @BeforeEach
            void prepare() {
                List<Product> products = new ArrayList<>();
                IntStream.range(0, givenProductCnt).forEach((i) -> products.add(getProduct()));

                given(productService.getProducts()).willReturn(products);
            }

            @Test
            @DisplayName("200(Ok)와 Product의 전체 리스트를 응답합니다.")
            void it_return_ok_and_product() throws Exception {
                mockMvc.perform(get(PRODUCTS_URI))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(givenProductCnt)))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("등록된 Product들이 없다면")
        class Context_has_not_product {
            final int givenProductCnt = 0;

            @BeforeEach
            void prepare() {
                List<Product> products = new ArrayList<>();

                given(productService.getProducts()).willReturn(products);
            }

            @Test
            @DisplayName("200(Ok)와 빈 리스트를 응답합니다.")
            void it_return_ok_and_products() throws Exception {
                mockMvc.perform(get(PRODUCTS_URI))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(givenProductCnt)))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("detail() 메소드는")
    class Describe_detail {
        @Nested
        @DisplayName("등록된 Product의 id가 주어진다면")
        class Context_with_id {
            Long givenProductId = 1L;

            @BeforeEach
            void prepare() {
                given(productService.getProduct(givenProductId)).willReturn(getProduct());
            }

            @Test
            @DisplayName("200(Ok)와 Product의 정보를 응답합니다.")
            void it_return_ok_and_product() throws Exception {
                mockMvc.perform(get(PRODUCTS_URI_SLASH + givenProductId))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").isNotEmpty())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("등록되지 않은 Product의 id가 주어진다면")
        class Context_without_id {
            Long givenInvalidId = 100L;

            @BeforeEach
            void prepare() {
                given(productService.getProduct(givenInvalidId)).willThrow(new ProductNotFoundException(givenInvalidId));
            }

            @Test
            @DisplayName("404(Not found)를 응답합니다.")
            void it_return_not_fount() throws Exception {
                mockMvc.perform(get(PRODUCTS_URI_SLASH + givenInvalidId))
                        .andExpect(status().isNotFound())
                        .andDo(print());
            }
        }
    }
    private Product getProduct() {
        return Product.builder()
                .name("테스트 제품")
                .maker("테스트 메이커")
                .price(1000)
                .image("http://test.com/test.jpg")
                .build();
    }
}
