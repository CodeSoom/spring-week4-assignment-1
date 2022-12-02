package com.codesoom.assignment.controller;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@DisplayName("ProductController 테스트")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatToyService service;

    private static final String PRODUCT_NAME = "춘식이 고구마 장난감";
    private static final String MAKER        = "카카오";
    private static final Long PRICE          = 39000L;
    private static final String IMAGE_URL    = "http://localhost:8080/snake";

    @BeforeEach
    void setUp() {
        given(service.findById(1L))
                .willReturn(Optional.of(new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL)));

        given(service.findById(100L))
                .willThrow(new ProductNotFoundException());

        doThrow(new ProductNotFoundException())
                .when(service)
                .deleteById(100L);
    }

    @Nested
    @DisplayName("GET 요청은")
    class Describe_get {
        @Nested
        @DisplayName("id가 없으면")
        class Context_without_segment {
            @Test
            @DisplayName("모든 Product 리스트를 리턴한다")
            void it_return_products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk());

                verify(service).findAll();
            }
        }

        @Nested
        @DisplayName("id를 넘겨받으면")
        class Context_with_id {
            @Test
            @DisplayName("해당하는 id의 Product를 리턴한다")
            void it_return_product() throws Exception {
                mockMvc.perform(get("/products/1"))
                        .andExpect(status().isOk());

                verify(service).findById(1L);
            }
        }
    }

    @Nested
    @DisplayName("POST 요청은")
    class Describe_post {
        @Test
        @DisplayName("상품을 새로 만들어 리턴한다")
        void it_return_new_product() throws Exception {
            Product product = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);
            String content = new ObjectMapper().writeValueAsString(product);

            mockMvc.perform(post("/products")
                            .content(content)
                            .contentType(APPLICATION_JSON))
                    .andExpect(status().isCreated());

            verify(service).save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("PATCH 요청은")
    class Describe_patch {
        @Nested
        @DisplayName("파라미터로 존재하는 id가 넘어오면")
        class Context_with_validId {
            @Test
            @DisplayName("해당하는 id의 product를 업데이트한다")
            void it_update_product() throws Exception {
                Product product = service.findById(1L).get();
                product.setProductName(PRODUCT_NAME + "!!!");
                String content = new ObjectMapper().writeValueAsString(product);

                mockMvc.perform(patch("/products/1")
                                .content(content)
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk());

                verify(service).save(any(Product.class));
                assertThat(product.getProductName())
                        .isEqualTo(PRODUCT_NAME + "!!!");
            }
        }

        @Nested
        @DisplayName("파라미터로 존재하지 않는 id가 넘어오면")
        class Context_with_invalidId {
            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_ProductNotFoundException() throws Exception {
                mockMvc.perform(patch("/products/100")
                                .content("{\"productName\":PRODUCT_NAME + \"!!!\"}")
                                .contentType(APPLICATION_JSON))
                        .andExpect(status().isNotFound());

                assertThatThrownBy(() -> service.findById(100L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("DELETE 요청은")
    class Describe_delete {
        @Nested
        @DisplayName("파라미터로 존재하는 id가 넘어오면")
        class Context_with_validId {
            @Test
            @DisplayName("해당하는 id의 Product를 삭제한다")
            void it_remove_product() throws Exception {
                mockMvc.perform(delete("/products/1"))
                        .andExpect(status().isNoContent());

                verify(service).deleteById(1L);
            }
        }

        @Nested
        @DisplayName("파라미터로 존재하지 않는 id가 넘어오면")
        class Context_with_invalidId {
            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_return_ProductNotFoundException() throws Exception {
                mockMvc.perform(delete("/products/100"))
                        .andExpect(status().isNotFound());

                assertThatThrownBy(() -> service.deleteById(100L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
