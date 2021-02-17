package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final String PRODUCT_NAME = "dyson";
    private final String PRODUCT_MAKER = "nike";
    private final int PRODUCT_PRICE = 5000;
    private final String PRODUCT_IMAGE = "dyson-kodolth.png";

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product =  Product.builder()
                                    .name(PRODUCT_NAME)
                                    .maker(PRODUCT_MAKER)
                                    .price(PRODUCT_PRICE)
                                    .image(PRODUCT_IMAGE)
                                    .build();
        products.add(product);

        given(productService.findAll()).willReturn(products);

        given(productService.findById(1L)).willReturn(product);

        given(productService.findById(100L))
                .willThrow(new ProductNotFoundException(100L));

    }


    @Test
    @DisplayName("제품 목록 전체를 조회하고 조회된 목록의 내용중 PRODUCT_NAME 값이 일치한지 확인한다.")
    void findAll() throws Exception {
        mockMvc.perform(get("/products"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(PRODUCT_NAME)));
    }

    @Test
    @DisplayName("특정 제품을 조회한다.")
    void findByValidId() throws Exception {
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());

        verify(productService).findById(1L);
    }

    @Test
    @DisplayName("존재하지 않는 제품을 조회하면 예외를 발생시킨다.")
    void findByInValidId() throws Exception {
        mockMvc.perform(get("/products/100"))
                .andExpect(status().isNotFound());

        verify(productService).findById(100L);
    }

    @Test
    @DisplayName("새로운 제품을 추가하고 추가된 목록과 HttpStatus 201를 응답받는다.")
    void create() throws Exception {
//        "name" : "코돌쓰" "maker" : "apple"
        mockMvc.perform(
                post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\" : \"코돌쓰\" \"maker\" : \"apple\"}")
        )
                .andExpect(status().isCreated());

        verify(productService).save(any(Product.class));
    }
}
