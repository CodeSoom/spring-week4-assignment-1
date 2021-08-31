package com.codesoom.assignment.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.Dto.CreateProductDto;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@DisplayName("장난감 리소스")
public final class ProductControllerWebTest {
    private static final String EMPTY_LIST = "[]";

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("전체 목록 조회 엔드포인트는")
    class Describe_get_products {
        @Nested
        @DisplayName("전체 목록 요청 시")
        class Context_request_product_list {
            @Nested
            @DisplayName("저장된 데이터가 없다면")
            class Context_product_empty {
                @Test
                @DisplayName("빈 목록을 리턴한다.")
                void it_returns_a_empty_list() throws Exception {
                    mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(EMPTY_LIST));
                }
            }
        }
    }

    @Nested
    @DisplayName("생성 엔드포인트는")
    class Describe_post_products {
        @BeforeEach
        void setUp() {
            when(productService.createProduct(any(CreateProductDto.class)))
                .thenReturn(new Product("title"));
        }

        @Nested
        @DisplayName("장난감 생성 요청 시")
        class Context_request_product_create {
            @Test
            @DisplayName("장난감을 생성하고 리턴한다.")
            void it_returns_a_product() throws Exception {
                mockMvc.perform(
                        post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"title\"}")
                    )
                    .andExpect(status().isCreated())
                    .andExpect(content().string(containsString("title")));
            }
        }

        @AfterEach
        void tearDown() {
            verify(productService)
                .createProduct(argThat(input -> "title".equals(input.getTitle())));
        }

    }
}
