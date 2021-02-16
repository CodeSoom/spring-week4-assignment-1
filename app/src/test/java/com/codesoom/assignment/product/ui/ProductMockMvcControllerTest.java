package com.codesoom.assignment.product.ui;

import com.codesoom.assignment.product.application.ProductNotFoundException;
import com.codesoom.assignment.product.application.ProductService;
import com.codesoom.assignment.product.ui.dto.ProductResponseDto;
import com.codesoom.assignment.product.ui.dto.ProductSaveDto;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스")
class ProductMockMvcControllerTest {
    private static final Long PRODUCT_ID = 1L;
    private static final String NAME = "snake";
    private static final String MAKER = "cat toy";
    private static final int PRICE = 5000;
    private static final String IMAGE_URL = "https://http.cat/599";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Nested
    @DisplayName("GET /products는")
    class Describe_getProducts {
        @Nested
        @DisplayName("등록된 상품이 있으면")
        class Context_with_products {
            List<ProductResponseDto> products;

            @BeforeEach
            void setUp() {
                ProductResponseDto product = new ProductResponseDto(PRODUCT_ID, NAME, MAKER, PRICE, IMAGE_URL);
                products = Arrays.asList(product);

                given(productService.getProducts())
                        .willReturn(products);
            }

            @DisplayName("200 상태 코드, OK 상태와 상품 목록을 응답한다.")
            @Test
            void It_responds_ok_with_products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(products)));
            }
        }

        @Nested
        @DisplayName("등록된 상품이 없으면")
        class Context_without_products {

            @DisplayName("200 상태코드, OK 상태와 비어있는 상품 목록을 응답한다.")
            @Test
            void It_responds_ok_with_empty_products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("[]")));
            }
        }
    }

    @Nested
    @DisplayName("GET /products/:id 는")
    class Describe_getProduct {

        @Nested
        @DisplayName("등록된 상품이 없으면")
        class Context_without_product {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(PRODUCT_ID))
                        .willThrow(new ProductNotFoundException(PRODUCT_ID));
            }

            @DisplayName("404 상태코드, Not Found 상태를 응답한다.")
            @Test
            void It_responds_not_found() throws Exception {
                mockMvc.perform(get("/products/{id}", PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("등록된 상품이 있으면")
        class Context_with_product {
            ProductResponseDto productDto;

            @BeforeEach
            void setUp() {
                productDto = new ProductResponseDto(PRODUCT_ID, NAME, MAKER, PRICE, IMAGE_URL);

                given(productService.getProduct(PRODUCT_ID))
                        .willReturn(productDto);
            }

            @DisplayName("200 상태코드, OK 상태와 찾고자 하는 상품을 응답한다.")
            @Test
            void it_responds_ok_with_product() throws Exception {
                mockMvc.perform(get("/products/{id}", PRODUCT_ID))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(productDto)))
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("maker").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imageUrl").exists());
            }
        }
    }

    @Nested
    @DisplayName("POST /products는")
    class Describe_create {
        ProductSaveDto productSaveDto;

        @BeforeEach
        void setUp() {
            productSaveDto = new ProductSaveDto(NAME, MAKER, PRICE, IMAGE_URL);
        }

        @Test
        @DisplayName("201 상태코드, Created 상태를 응답한다.")
        void It_responds_created() throws Exception {
            mockMvc.perform(post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productSaveDto))
            ).andExpect(status().isCreated());
        }
    }
}
