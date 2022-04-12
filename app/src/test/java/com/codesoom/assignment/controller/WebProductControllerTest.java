package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductCommandService;
import com.codesoom.assignment.application.ProductQueryService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductSaveDto;
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc
@DisplayName("상품에 대한 HTTP 요청")
public class WebProductControllerTest {

    private static final String TEST_PRODUCT_MAKER = "MAKER";
    private static final Integer TEST_PRODUCT_PRICE = 10000;
    private static final String TEST_PRODUCT_IMAGE_PATH = "/images/test.jpg";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductCommandService productCommandService;

    @MockBean
    private ProductQueryService productQueryService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Nested
    @DisplayName("GET - /products 요청시")
    class Describe_list {

        @Nested
        @DisplayName("상품 수 만큼")
        class Context_givenCount {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {
                List<Product> products = LongStream.rangeClosed(1, givenCount)
                        .mapToObj(Product::new)
                        .collect(Collectors.toList());

                given(productQueryService.getProducts()).willReturn(products);
            }

            @Test
            @DisplayName("상품 목록을 응답한다. [200]")
            void it_response_products_and_http_status_200() throws Exception {

                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(givenCount)));
            }
        }
    }

    @Nested
    @DisplayName("GET - /products/{productId} 요청시")
    class Describe_detail {

        final Long productId = 1L;

        @Nested
        @DisplayName("{productId} 와 일치하는 상품이 있다면")
        class Context_existsProduct {

            final Product product = new Product(productId, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

            @BeforeEach
            void setUp() {
                given(productQueryService.getProduct(productId)).willReturn(product);
            }

            @Test
            @DisplayName("상품 정보를 응답한다. [200]")
            void it_response_product() throws Exception {
                mockMvc.perform(get("/products/{productId}", productId)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("maker").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imagePath").exists());
            }
        }
    }


    @Nested
    @DisplayName("POST - /products 요청시")
    class Describe_save {

        @Nested
        @DisplayName("상품 등록에 필요한 데이터가 주어진다면")
        class Context_valid {

            final ProductSaveDto source = new ProductSaveDto(TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

            @BeforeEach
            void setUp() {
                Product product = new Product(1L, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);
                given(productCommandService.saveProduct(any(Product.class))).willReturn(product);
            }

            @Test
            @DisplayName("상품을 등록하고 응답한다. [200]")
            void it_save_and_return_product() throws Exception {

                mockMvc.perform(post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(source)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("maker").value(TEST_PRODUCT_MAKER))
                        .andExpect(jsonPath("price").value(TEST_PRODUCT_PRICE))
                        .andExpect(jsonPath("imagePath").value(TEST_PRODUCT_IMAGE_PATH));
            }
        }
    }
}
