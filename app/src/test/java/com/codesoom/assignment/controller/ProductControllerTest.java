package com.codesoom.assignment.controller;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * 이렇게 하는 이유는 프론트오 협업을 하기 위해 먼저 컨트롤러를 먼저 만든다.
 * <p>
 * 1. GET /products -> 목록
 * 2. GET /products/{id} -> 상세 정보
 * 3. POST /products -> 상품 추가
 * 4. PUT/PATCH /products/{id} -> 상품 수정
 * 5. DELETE /products/{id} -> 상품 삭제
 */


@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        Product product = new Product("쥐돌이", "냥이월드", 5000);
        given(productService.getProducts()).willReturn(List.of(product));
        given(productService.getProduct(1L)).willReturn(product);
        given(productService.getProduct(1000L)).willThrow(new ProductNotFoundException(1000L));
        given(productService.updateProduct(eq(1000L), any())).willThrow(new ProductNotFoundException(1000L));
        given(productService.deleteProduct(1000L)).willThrow(new ProductNotFoundException(1000L));
    }

    @Test
    void list() throws Exception {
        // 리스트 조회 테스트하기 위해서는 상품을 먼저 등록해야한다.

        mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("쥐돌이")));
    }

    @Test
    void detailWithExistedProduct() throws Exception {
        // 리스트 조회 테스트하기 위해서는 상품을 먼저 등록해야한다.

        mockMvc.perform(get("/products/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("쥐돌이")));
    }

    @Test
    void detailWithNotExistedProduct() throws Exception {
        mockMvc.perform(get("/products/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/products")
                        .content("{\"name\":\"쥐돌이\",\"maker\":\"냥이월드\",\"price\":5000}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(productService).createProduct(any());
    }

    @Test
    void updateWithExistProduct() throws Exception {
        mockMvc.perform(patch("/products/1")
                        .content("{\"name\":\"쥐순이\",\"maker\":\"냥이월드\",\"price\":5000}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService).updateProduct(anyLong(), any());
    }

    @Test
    void updateWithNotExistProduct() throws Exception {
        mockMvc.perform(patch("/products/1000")
                        .content("{\"name\":\"쥐순이\",\"maker\":\"냥이월드\",\"price\":5000}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void destroyWithExistProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(any());
    }

    @Test
    void destroyWithNotExistProduct() throws Exception {
        mockMvc.perform(delete("/products/1000"))
                .andExpect(status().isNotFound());
    }
}
