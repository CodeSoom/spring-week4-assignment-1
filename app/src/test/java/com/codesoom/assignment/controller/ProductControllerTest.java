package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductCommandService;
import com.codesoom.assignment.application.ProductQueryService;
import com.codesoom.assignment.controller.dto.ProductRequestDto;
import com.codesoom.assignment.controller.dto.ProductResponseDto;
import com.codesoom.assignment.controller.dto.ProductUpdateRequest;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@DisplayName("ProductController 클래스")
class ProductControllerTest {

    private static final Long TEST_PRODUCT_ID = 1L;
    private static final Long WRONG_PRODUCT_ID = 100L;

    private final String name = "toy";
    private final String maker = "toy shop";
    private final int price = 15000;
    private final String imageUrl = "toy.jpg";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductQueryService productQueryService;

    @MockBean
    private ProductCommandService productCommandService;

    @BeforeEach
    void setUp() {
        ProductResponseDto product = getResponseDto();

        given(productQueryService.get(TEST_PRODUCT_ID)).willReturn(product);

        given(productQueryService.get(WRONG_PRODUCT_ID)).willThrow(ProductNotFoundException.class);

        given(productQueryService.getAll()).willReturn(Collections.emptyList());

        given(productCommandService.create(any(ProductUpdateRequest.class))).willReturn(product);

        given(productCommandService.update(eq(WRONG_PRODUCT_ID), any(ProductUpdateRequest.class))).willThrow(ProductNotFoundException.class);

        given(productCommandService.update(eq(TEST_PRODUCT_ID), any(ProductUpdateRequest.class))).willReturn(product);
    }

    private ProductResponseDto getResponseDto() {
        return new ProductResponseDto(new Product(name, maker, price, imageUrl));
    }

    private ProductRequestDto getRequestDto() {
        return new ProductRequestDto(name, maker, price, imageUrl);
    }

    private String toJsonString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    @DisplayName("모든 상품 조회 요청이 들어오면 상품 목록과 함께 200 응답 코드를 생성한다")
    void getAll_test() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(content().string(toJsonString(Collections.emptyList())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("조회 가능한 id로 상품 조회 요청이 들어오면 상품 정보와 함께 200 응답 코드를 생성한다")
    void getProduct_test() throws Exception {
        mockMvc.perform(get("/products/{id}", TEST_PRODUCT_ID))
                .andExpect(content().string(containsString(name)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("조회 불가능한 id로 상품 조회 요청이 들어오면 404 응답 코드를 생성한다")
    void getProduct_fail_test() throws Exception {
        mockMvc.perform(get("/products/{id}", WRONG_PRODUCT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("상품 생성 요청이 들어오면 201 응답 코드를 생성한다")
    void createProduct_test() throws Exception {
        final String productRequestJsonString = toJsonString(getRequestDto());

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJsonString))
                .andExpect(status().isCreated())
                .andExpect(content().string(toJsonString(getResponseDto())));
    }

    @Test
    @DisplayName("삭제 가능한 id로 상품 삭제 요청이 들어오면 204 응답 코드를 생성한다")
    void deleteProduct_test() throws Exception {
        mockMvc.perform(delete("/products/{id}", TEST_PRODUCT_ID))
                .andExpect(status().isNoContent());

        verify(productCommandService).deleteById(TEST_PRODUCT_ID);
    }

    @Test
    @DisplayName("수정 가능한 id로 상품 수정 요청이 들어오면 수정된 상품 정보와 함께 200 응답 코드를 생성한다")
    void updateProduct_test() throws Exception {
        final String productRequestJsonString = toJsonString(getRequestDto());

        mockMvc.perform(put("/products/{id}", TEST_PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJsonString))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("수정 불가능한 id로 상품 수정 요청이 들어오면 404 응답 코드를 생성한다")
    void updateProduct_fail_test() throws Exception {
        final String productRequestJsonString = toJsonString(getRequestDto());

        mockMvc.perform(put("/products/{id}", WRONG_PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJsonString))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
