package com.codesoom.assignment.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("ProductController 클래스의")
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

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
            private final String name = "cat1";
            private final String maker = "codesoom";
            private final Long price = 33_000L;
            private final Product product = new Product(name, maker, price);
            private final MockHttpServletRequestBuilder requestBuilder =
                    post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"" + name + "\"}");

            @BeforeEach
            void mocking() {
                given(productService.create(any(Product.class)))
                        .willReturn(product);
            }

            @Test
            @DisplayName("새로운 상품을 생성한 후 생성된 상품을 반환한다")
            void It_creates_one_product() throws Exception {
                // when
                mockMvc.perform(requestBuilder)
                       // then
                       .andExpect(status().isCreated())
                       .andExpect(jsonPath("$.name", containsString(name)))
                       .andExpect(jsonPath("$.maker",
                                           containsString(maker)))
                       .andExpect(jsonPath("$.price", is(price.intValue())));

                verify(productService).create(any(Product.class));
            }
        }
    }
}
