package com.codesoom.assignment.product.ui;

import com.codesoom.assignment.product.application.ProductService;
import com.codesoom.assignment.product.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스")
class ProductMockMvcControllerTest {
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
            List<Product> products;

            @BeforeEach
            void setUp() {
                Product product = new Product(NAME, MAKER, PRICE, IMAGE_URL);
                products = Arrays.asList(product);
                ReflectionTestUtils.setField(product, "id", 1L);

                given(productService.getProducts())
                        .willReturn(products);
            }

            @DisplayName("200 상태 코드, OK 상태와 상품 목록을 응답한다.")
            @Test
            void It_responds_ok_with_products() throws Exception {

                mockMvc.perform(get("/v1/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(objectMapper.writeValueAsString(products)));
            }
        }

        @Nested
        @DisplayName("등록된 상품이 없으면")
        class Context_without_products {

            @DisplayName("200 상태코드, OK 상태와 비어있는 상품 목록 응답한다.")
            @Test
            void It_responds_ok_with_empty_products() throws Exception {
                mockMvc.perform(get("/v1/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("[]")));
            }
        }
    }

}
