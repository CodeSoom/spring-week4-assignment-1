package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductResponse;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private final Long STORED_ID = 1L;
    private final Long NOT_STORED_ID = 100L;

    private List<ProductResponse> products;
    private ProductResponse productResponse1;
    private ProductResponse productResponse2;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();

        productResponse1 = new ProductResponse(
                Product.builder()
                        .id(1L)
                        .name("name1")
                        .maker("maker1")
                        .price(1)
                        .imageUrl("url1")
                        .build()
        );

        productResponse2 = new ProductResponse(
                Product.builder()
                        .id(2L)
                        .name("name2")
                        .maker("maker2")
                        .price(2)
                        .imageUrl("url2")
                        .build()
        );
    }

    @Nested
    @DisplayName("GET 요청은")
    class Describe_GET {

        @Nested
        @DisplayName("저장된 상품이 여러 개 있다면")
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
            void it_responds_all_products_and_status_code_200() throws Exception {
                mockMvc.perform(get("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(products.size())))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("저장된 상품이 없다면")
        class Context_without_product {

            @BeforeEach
            void setUp() {
                given(productService.getProducts())
                        .willReturn(List.of());
            }

            @Test
            @DisplayName("빈 리스트와 상태코드 200을 응답한다")
            void it_responds_empty_list_and_status_code_200() throws Exception {
                mockMvc.perform(get("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$", hasSize(0)))
                        .andExpect(status().isOk());
            }
        }
    }
}
