package com.codesoom.assignment.web.controller;

import com.codesoom.assignment.core.application.ProductService;
import com.codesoom.assignment.core.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController web")
class ProductControllerWebTest {
    private final String NAME = "장난감 이름";
    private final String BRAND = "장난감 브랜드";
    private final int PRICE = 10000;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Nested
    @DisplayName("Request GET /products")
    class DescribeGetProducts {
        List<Product> products;

        @Nested
        @DisplayName("장난감이 존재할 때")
        class ContextWithProducts {
            @BeforeEach
            void prepare() {
                products = new ArrayList<>();
                products.add(makeNewProduct(NAME, BRAND, PRICE));
                given(productService.fetchProducts()).willReturn(products);
            }

            @Test
            @DisplayName("장난감이 든 배열을 반환한다")
            void products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)))
                        .andExpect(jsonPath("$[0].name").value(NAME))
                        .andExpect(jsonPath("$[0].brand").value(BRAND))
                        .andExpect(jsonPath("$[0].price").value(PRICE));
            }
        }

        @Nested
        @DisplayName("장난감이 없을 때")
        class ContextWithEmptyProducts {
            @BeforeEach
            void prepare() {
                products = new ArrayList<>();
                given(productService.fetchProducts()).willReturn(products);
            }

            @Test
            @DisplayName("빈 배열을 반환한다")
            void list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("[]"));
            }
        }
    }

    public Product makeNewProduct(String name, String brand, Integer price) {
        return Product.builder()
                .name(name)
                .brand(brand)
                .price(price)
                .build();
    }


}