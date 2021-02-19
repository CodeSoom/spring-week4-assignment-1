package com.codesoom.assignment.controller;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스")
class ProductControllerTest {

    @Autowired // Spring이 자동으로 new를 해주는 것
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean // 실제가 아닌 가짜지만, 실제처럼 작동하게 해서 테스트 진행
    private ProductService productService;

    private final Long PRODUCT_ID = 1L;
    private final Long NOT_EXISTING_PRODUCT_ID = 999L;
    private final String PRODUCT_NAME = "cat toy";
    private final String PRODUCT_MAKER = "toy maker";
    private final int PRODUCT_PRICE = 3000;
    private final String PRODUCT_IMAGE_URL = "img url";

    private List<Product> products;
    private Product product;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
        product = new Product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_IMAGE_URL);
    }

    @AfterEach
    void clear() {
        Mockito.reset(productService);
    }

    @Nested
    @DisplayName("GET /products 요청은")
    class Describe_get_request {

        @Nested
        @DisplayName("상품목록에 저장된 데이터가 있으면")
        class Context_with_products {
            @BeforeEach
            void setUp() {
                products.add(product);
                given(productService.getProducts())
                        .willReturn(products);
            }

            @Test
            @DisplayName("200 코드와 저장 되어있는 상품 목록을 응답한다.")
            void it_respond_200_and_all_products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(products)));
            }
        }

        @Nested
        @DisplayName("상품목록에 저장된 데이터가 없으면")
        class Context_with_no_product {
            @BeforeEach
            void setUp() {
                given(productService.getProducts()).willReturn(products);
            }

            @Test
            @DisplayName("200 코드와 비어있는 목록을 리턴한다.")
            void it_respond_200_and_empty_array() throws Exception {
                mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(0)))
                        .andExpect(content().string("[]"));
            }
        }
    }

    @Nested
    @DisplayName("GET /products/{id} 요청은")
    class Describe_get_request_with_id{

        @Nested
        @DisplayName("상품목록에 존재하는 id로 조회하면")
        class Context_contains_target_id {
            @BeforeEach
            void setUp() {
                products.add(product);
                given(productService.getProduct(PRODUCT_ID)).willReturn(product);
            }

            @Test
            @DisplayName("200 코드와 id에 일치하는 상품을 응답한다.")
            void it_respond_200_and_target_product() throws Exception {
                mockMvc.perform(get("/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").value(PRODUCT_ID))
                        .andExpect(jsonPath("name").value(PRODUCT_NAME))
                        .andExpect(jsonPath("maker").value(PRODUCT_MAKER))
                        .andExpect(jsonPath("price").value(PRODUCT_PRICE));
            }
        }

        @Nested
        @DisplayName("상품목록에 존재하지 않는 id로 조회하면")
        class Context_not_contains_target_id {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(NOT_EXISTING_PRODUCT_ID))
                        .willThrow(new ProductNotFoundException(NOT_EXISTING_PRODUCT_ID));
            }

            @Test
            @DisplayName("404 코드를 응답한다.")
            void it_respond_404() throws Exception {
                mockMvc.perform(get("/products/{id}", NOT_EXISTING_PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("id").doesNotExist());
            }
        }
    }

    @Nested
    @DisplayName("POST /products 요청은")
    class Describe_post_request {

        @Nested
        @DisplayName("새로운 상품을 추가하면")
        class Context_add_new_product {
            @BeforeEach
            void setUp() {
                product = new Product(2L, "newName", "newMaker", 9999, "newImageUrl");
                products.add(product);
                given(productService.createProduct(any(Product.class))).willReturn(product);
            }

            @Test
            @DisplayName("201 코드와 생성된 상품을 리턴한다.")
            void it_respond_201_and_new_product() throws Exception{
                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("id").value(2L))
                        .andExpect(jsonPath("name").value("newName"))
                        .andExpect(jsonPath("maker").value("newMaker"))
                        .andExpect(jsonPath("price").value(9999))
                        .andExpect(jsonPath("imageUrl").value("newImageUrl"));
            }
        }
    }

    @Nested
    @DisplayName("DELETE /products {id} 요청은")
    class Describe_delete_request {

        @Nested
        @DisplayName("상품목록에 해당하는 id가 있으면")
        class Context_contains_target_id {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(PRODUCT_ID))
                        .willThrow(new ProductNotFoundException(PRODUCT_ID));
            }

            @Test
            @DisplayName("id에 해당하는 상품을 삭제하고, 그 후 대상 id를 조회하면 예외를 던진다.")
            void it_respond_delete_product() throws Exception {
                mockMvc.perform(delete("/products/{id}", PRODUCT_ID));
                mockMvc.perform(get("/products/{id}", PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("상품목록에 해당하는 id가 없으면")
        class Context_not_contains_target_id {
            @BeforeEach
            void setUp() {
                given(productService.deleteProduct(NOT_EXISTING_PRODUCT_ID))
                        .willThrow(new ProductNotFoundException(NOT_EXISTING_PRODUCT_ID));
            }

            @Test
            @DisplayName("예외를 던진다.")
            void it_throws_product_not_found_exception() throws Exception {
                mockMvc.perform(delete("/products/{id}", NOT_EXISTING_PRODUCT_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }

}
