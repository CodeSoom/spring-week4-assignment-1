package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.errors.ProductNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings({"InnerClassMayBeStatic", "NonAsciiCharacters"})
@WebMvcTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String PRODUCT_NAME = "test";
    private static final String SAVE_POSTFIX = "111";
    private static final String UPDATE_POSTFIX = "222";

    @DisplayName("GET /products는 저장하고 있는 상품 목록을 반환한다")
    @Test
    void getProducts() throws Exception {
        given(productService.getProducts()).willReturn(new ArrayList<>());

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());

        verify(productService).getProducts();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 상품_조회_요청은 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_있다면 {
            @Test
            void 상품을_리턴한다() throws Exception {
                mockMvc.perform(get("/products/1"))
                        .andExpect(status().isOk());

                verify(productService).getProduct(1L);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_없다면 {
            @Test
            void NOT_FOUND를_보낸다() throws Exception {
                given(productService.getProduct(100L)).willThrow(ProductNotFoundException.class);

                mockMvc.perform(get("/products/100"))
                        .andExpect(status().isNotFound());

                verify(productService).getProduct(100L);
            }
        }
    }

    @DisplayName("POST /products는 상품을 저장한다")
    @Test
    void saveProduct() throws Exception {
        String name = PRODUCT_NAME + SAVE_POSTFIX;

        String json = createProductJson(name);

        mockMvc.perform(post("/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(productService).saveProduct(any(Product.class));
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 상품_수정_요청은 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_있다면 {
            @Test
            void 상품을_수정한다() throws Exception {
                String name = PRODUCT_NAME + UPDATE_POSTFIX;

                String json = createProductJson(name);

                mockMvc.perform(patch("/products/1")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

                verify(productService).updateProduct(eq(1L), any(Product.class));
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_없다면 {
            @Test
            void NOT_FOUND를_보낸다() throws Exception {
                given(productService.updateProduct(eq(100L), any(Product.class))).willThrow(ProductNotFoundException.class);

                String name = PRODUCT_NAME + UPDATE_POSTFIX;
                String json = createProductJson(name);

                mockMvc.perform(patch("/products/100")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());

                verify(productService).updateProduct(eq(100L), any(Product.class));
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class 상품_삭제_요청은 {
        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_있다면 {
            @Test
            void 상품을_삭제한다() throws Exception {
                mockMvc.perform(delete("/products/1"))
                        .andExpect(status().isNoContent());

                verify(productService).deleteProduct(1L);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 상품목록에서_주어진_아이디의_상품이_없다면 {
            @Test
            void NOT_FOUND를_보낸다() throws Exception {
                willThrow(new ProductNotFoundException("아이디 100번의 상품을 찾지 못했으므로, 상품을 삭제하지 못했습니다.")).given(productService).deleteProduct(100L);

                mockMvc.perform(delete("/products/100"))
                        .andExpect(status().isNotFound());

                verify(productService).deleteProduct(100L);
            }
        }
    }

    private String createProductJson(String name) throws JsonProcessingException {
        Product source = Product.createSaveProduct(name, null, 0, null);
        return objectMapper.writeValueAsString(source);
    }
}