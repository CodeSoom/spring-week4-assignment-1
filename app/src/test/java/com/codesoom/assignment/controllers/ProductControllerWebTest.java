package com.codesoom.assignment.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNonExistException;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
    }

    @DisplayName("장난감 목록을 성공적으로 가져왔을 때의 테스트")
    @Test
    void getProductListTest() throws Exception {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk());
    }

    @DisplayName("특정한 장난감을 성공적으로 가져왔을 떄의 테스트")
    @Test
    void getExistProductTest() throws Exception {
        Product product = new Product();
        given(productService.getProduct(1L)).willReturn(product);
        mockMvc.perform(get("/products/1"))
            .andExpect(status().isOk());
    }

    @DisplayName("특정한 장난감을 찾지 못했을 때의 테스트")
    @Test
    void getNoExistProductTest() throws Exception {
        given(productService.getProduct(100L))
            .willThrow(new ProductNonExistException(100L));
        mockMvc.perform(get("/products/100"))
            .andExpect(status().isNotFound());
    }

    @DisplayName("장난감을 생성하였을 때의 테스트")
    @Test
    void createProductTest() throws Exception {
        mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"name\": \"장난감 뱀\",\n  \"brand\": \"토이저러스\","
                    + "\n  \"price\": 5000,\n  \"image\": \"toy_snake_png\"\n}"))
            .andExpect(status().isCreated());
    }

    @DisplayName("존재하는 장난감을 수정하였을 때의 테스트")
    @Test
    void updateExistProduct() throws Exception {
        mockMvc.perform(put("/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"수정된 뱀\",\n  \"brand\": \"토이저러스\","
                + "\n  \"price\": 5000,\n  \"image\": \"toy_snake_png\"\n}"))
            .andExpect(status().isOk());
    }

    @DisplayName("존재하지 않는 장난감을 수정하였을 때의 테스트")
    @Test
    void updateNonExistProduct() throws Exception {
        given(productService.updateProduct(100L, new Product()))
            .willThrow(new ProductNonExistException(100L));
        mockMvc.perform(put("/products/100")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"수정된 뱀\",\n  \"brand\": \"토이저러스\","
                + "\n  \"price\": 5000,\n  \"image\": \"toy_snake_png\"\n}"))
            .andExpect(status().isNotFound());
    }

    @DisplayName("존재하는 장난감을 삭제하였을 때의 테스트")
    @Test
    void deleteExistProduct() throws Exception {
        given(productService.deleteProduct(1L)).willReturn(new Product());
        mockMvc.perform(delete("/products/1"))
            .andExpect(status().isNoContent());
    }

    @DisplayName("존재하지 않는 장난감을 삭제하였을 때의 테스트")
    @Test
    void deleteNonExistProduct() throws Exception {
        given(productService.deleteProduct(100L))
            .willThrow(new ProductNonExistException(100L));
        mockMvc.perform(delete("/products/100"))
            .andExpect(status().isNotFound());
    }
}
