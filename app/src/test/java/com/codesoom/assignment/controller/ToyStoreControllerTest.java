package com.codesoom.assignment.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.service.ToyStoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ToyStoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ToyStoreService toyStoreService;

    @DisplayName("고양이 장난감 등록 후 등록된 정보 반환")
    @Test
    void addToyTest() throws Exception {

        Product source = new Product("장난감 뱀", "애옹이네 장난감", 5000, "abc_aaa.jpg");

        Product savedSource = new Product("장난감 뱀", "애옹이네 장난감", 5000, "abc_aaa.jpg");

        given(toyStoreService.save(source)).willReturn(savedSource);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productToString(source))
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("장난감 뱀")))
                .andDo(print());

        verify(toyStoreService).save(any(Product.class));
    }

    @DisplayName("장난감 목록 조회 후 반환")
    @Test
    void getProductsTest() throws Exception {

        List<Product> productsSource = new ArrayList<>();
        productsSource.add(new Product(1L, "name", "maker", 10000, "abcdefg.jpg"));
        productsSource.add(new Product(2L, "name", "maker", 10000, "abcdefg.jpg"));
        productsSource.add(new Product(3L, "name", "maker", 10000, "abcdefg.jpg"));

        given(toyStoreService.getProducts()).willReturn(productsSource);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1")))
                .andDo(print());

        verify(toyStoreService).getProducts();
    }

    @DisplayName("유효한 id로 장난감 조회 후 반환")
    @Test
    void getProductWithValidIdTest() throws Exception {

        Product product = new Product(1L, "name", "maker", 10000, "abcdefg.jpg");

        given(toyStoreService.getProduct(1L)).willReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("abcdefg.jpg")));

        verify(toyStoreService).getProduct(1L);
    }

    @DisplayName("유효하지 않은 id로 장난감 조회 후 반환")
    @Test
    void getProductWithInValidIdTest() throws Exception {

        given(toyStoreService.getProduct(100L)).willThrow(new ProductNotFoundException(100L));

        mockMvc.perform(get("/products/100"))
                .andExpect(status().isNotFound());

        verify(toyStoreService).getProduct(100L);
    }

    @DisplayName("유효한 id로 장난감 삭제")
    @Test
    void deleteProductWithValidIdTest() throws Exception {

        Product product = new Product(1L, "name", "maker", 10000, "abcdefg.jpg");

        given(toyStoreService.deleteProduct(1L)).willReturn(product);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(toyStoreService).deleteProduct(1L);
    }

    @DisplayName("유효하지 않은 id로 장난감 삭제")
    @Test
    void deleteProductWithInValidIdTest() throws Exception {

        given(toyStoreService.deleteProduct(100L)).willThrow(new ProductNotFoundException(100L));

        mockMvc.perform(delete("/products/100"))
                .andExpect(status().isNotFound());

        verify(toyStoreService).deleteProduct(100L);
    }

    @DisplayName("유효한 id로 장난감 등록 정보 수정")
    @Test
    void updateProductWithValidIdTest() throws Exception {

        Product source = new Product(1L, "장난감 뱀", "애옹이네 장난감", 5000, "abc_aaa.jpg");
        Product updatedSource = new Product(1L, "장난감 상어", "애옹이네 장난감", 5000, "abc_aaa.jpg");

        given(toyStoreService.updateProduct(source)).willReturn(updatedSource);

        mockMvc.perform(patch("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productToString(source)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("장난감 상어")))
                .andDo(print());

        verify(toyStoreService).updateProduct(any(Product.class));
    }

    @DisplayName("유효하지 않은 id로 장난감 등록 정보 수정")
    @Test
    void updateProductWithInValidIdTest() throws Exception {

        Product source = new Product(100L, "장난감 뱀", "애옹이네 장난감", 5000, "abc_aaa.jpg");

        given(toyStoreService.updateProduct(source)).willThrow(new ProductNotFoundException(100L));

        mockMvc.perform(patch("/products/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productToString(source)))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(toyStoreService).updateProduct(any(Product.class));
    }

    private String productToString(Object source) throws JsonProcessingException {
        return objectMapper.writeValueAsString(source);
    }
}
