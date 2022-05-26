package com.codesoom.assignment.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(content().string(containsString("1")))
                .andDo(print());

        verify(toyStoreService).getProducts();
    }

    private String productToString(Object source) throws JsonProcessingException {
        return objectMapper.writeValueAsString(source);
    }
}
