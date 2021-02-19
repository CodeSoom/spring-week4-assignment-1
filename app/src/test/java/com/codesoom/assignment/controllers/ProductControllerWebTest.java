package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.request.ProductRequest;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

    private final Long existingId = 1L;
    private final Long notExistingId = 100L;

    private final String PRODUCT_NAME = "shoes";

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product =  Product.builder()
                                    .name(PRODUCT_NAME)
                                    .maker("nike")
                                    .price(5000)
                                    .image("nike_shoes.png")
                                    .build();
        products.add(product);

        given(productService.getProducts()).willReturn(products);

        given(productService.getProduct(existingId)).willReturn(product);

        given(productService.getProduct(notExistingId))
                .willThrow(new ProductNotFoundException(notExistingId));

        given(productService.updateProduct(eq(notExistingId),
                                            any(ProductRequest.class)))
                .willThrow(new ProductNotFoundException(notExistingId));

        given(productService.deleteProduct(notExistingId))
                .willThrow(new ProductNotFoundException(notExistingId));
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
        mockMvc.perform(get("/products/{id}", existingId))
                .andExpect(status().isOk());

        verify(productService).getProduct(existingId);
    }

    @Test
    @DisplayName("존재하지 않는 제품을 조회하면 예외를 발생시킨다.")
    void findByInValidId() throws Exception {
        mockMvc.perform(get("/products/{id}", notExistingId))
                .andExpect(status().isNotFound());

        verify(productService).getProduct(notExistingId
        );
    }

    @Test
    @DisplayName("새로운 제품을 추가하고 추가된 목록과 HttpStatus 201를 응답받는다.")
    void create() throws Exception {
        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"코돌쓰\"}")
        )
                .andExpect(status().isCreated());

        verify(productService).createProduct(any(ProductRequest.class));
    }

    @Test
    @DisplayName("요청한 id에 해당하는 상품의 값을 변경한다.")
    void updateExistedProduct() throws Exception {
        mockMvc.perform(
                patch("/products/{id}", existingId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\" : \"Rename_코돌쓰\"}")
                )
                .andExpect(status().isOk());

        verify(productService).updateProduct(eq(existingId), any(ProductRequest.class));
    }

    @Test
    @DisplayName("존재하지 않는 상품의 값 변경을 요청 할 경우 예외를 던진다.")
    void updateNotExistedProduct() throws Exception {
        mockMvc.perform(
                patch("/products/{id}", notExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"코돌쓰 변경\"}")
        )
                .andExpect(status().isNotFound());

        verify(productService).updateProduct(eq(notExistingId), any(ProductRequest.class));
    }

    @Test
    @DisplayName("요청한 id에 해당하는 상품을 찾아 삭제하고 상태코드 204를 응답해준다.")
    void deleteExistedProduct() throws Exception {
        mockMvc.perform(delete("/products/{id}", existingId))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(existingId);
    }

    @Test
    @DisplayName("존재하지 않는 상품을 삭제 요청할 경우 상태코드 404를 응답해준다.")
    void deleteNotExistedProduct() throws Exception {
        mockMvc.perform(delete("/products/{id}", notExistingId))
                .andExpect(status().isNotFound());

        verify(productService).deleteProduct(notExistingId);
    }
}
