package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest //통합테스트
@AutoConfigureMockMvc //use Spring’s MockMvc and ask for that to be injected for you by using the @AutoConfigureMockMvc annotation on the test case.
public class ProductControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productServic;

    @BeforeEach
    void setUp() {
        //given
        List<Product> products = new ArrayList<>();

        Product product1 = Product.builder()
                .name("name1")
                .maker("maker1")
                .price(300)
                .imagePath("path1")
                .build();

        products.add(product1);

        Product product2 = Product.builder()
                .name("name2")
                .maker("maker2")
                .price(300)
                .imagePath("path2")
                .build();

        products.add(product2);

        // when
        // then
        given(productServic.getProducts()).willReturn(products);
        given(productServic.getProduct(1L)).willReturn(product1);
        given(productServic.getProduct(100L)).willThrow(new IllegalArgumentException());
    }

    @Nested
    class Describe_list{

        @Test
        void it_returns_a_product_list() throws Exception {

            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk());

        }

    }

    @Nested
    class Describe_findById{

        @Nested
        class Context_with_valid_parameter{

            @Test
            void it_returns_a_status_Ok() throws Exception {

                mockMvc.perform(get("/products/1"))
                        .andExpect(status().isOk());
            }

        }

        @Nested
        class Context_with_invalid_parameter{

            @Test
            void it_returns_a_status_NotFound() throws Exception {

                mockMvc.perform(get("/products/100"))
                        .andExpect(status().isNotFound());

            }

        }

    }

}
