package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_NAME = "고양이 낚시대";
    private static final String PRODUCT_MAKER = "애옹이네 장난감";
    private static final int PRODUCT_PRICE = 5000;
    private static final String PRODUCT_IMAGE_URL = "http://image.kyobobook.co.kr/newimages/giftshop_new/goods/400/1095/hot1602809707085.jpg";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .price(PRODUCT_PRICE)
                .imageUrl(PRODUCT_IMAGE_URL)
                .build();
    }

    @Nested
    @DisplayName("GET /products 요청 시")
    class Describe_get_products {
        private List<Product> products;

        @BeforeEach
        void setUp() {
            products = new ArrayList<>();
            products.add(product);

            given(productService.getProducts()).willReturn(products);
        }

        @Test
        @DisplayName("product 리스트를 반환한다")
        void It_returns_product_list() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(PRODUCT_ID))
                    .andExpect(jsonPath("$[0].name").value(PRODUCT_NAME))
                    .andExpect(jsonPath("$[0].maker").value(PRODUCT_MAKER))
                    .andExpect(jsonPath("$[0].price").value(PRODUCT_PRICE));

            verify(productService).getProducts();
        }
    }
}
