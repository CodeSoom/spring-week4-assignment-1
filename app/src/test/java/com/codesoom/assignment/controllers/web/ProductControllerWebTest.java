package com.codesoom.assignment.controllers.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
            .name("name")
            .maker("maker")
            .price(10000L)
            .imageUrl("imageUrl")
            .build();

        productService.createProduct(product);
    }

    @Nested
    @DisplayName("POST /products 요청은")
    class Describe_postProduct {

        @Nested
        @DisplayName("상품 객체가 주어지면")
        class Context_givenProduct {

            private Product source;

            @BeforeEach
            void setUp() {
                source = Product.builder()
                    .name("name")
                    .maker("maker")
                    .price(10000L)
                    .imageUrl("imageUrl")
                    .build();
            }

            @Test
            @DisplayName("새 상품을 생성하고 201을 응답한다")
            void it_create_a_product_and_response_201() throws Exception {
                mockMvc.perform(post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(source.toString()))
                    .andExpect(content().json(source.toString()))
                    .andExpect(status().isCreated());
            }
        }
    }

    @Nested
    @DisplayName("GET /products 요청은")
    class Describe_getProduct {

        @Test
        @DisplayName("모든 상품을 반환하고 200을 응답한다")
        void it_returns_all_products_with_200() throws Exception {
            mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("GET /products/{id} 요청은")
    class Describe_getProductWithId {

        @Nested
        @DisplayName("존재하는 id일 경우")
        class Context_existId {

            private Long existProductId;

            @BeforeEach
            void setUp() {
                Long productId = product.getId();

                assertThat(checkHasId(productId)).isTrue();

                existProductId = productId;
            }

            @Test
            @DisplayName("찾은 상품을 반환하고 200을 응답한다")
            void it_return_find_product_and_response_200() throws Exception {
                mockMvc.perform(get("/products/" + existProductId))
                    .andExpect(content().json(product.toString()))
                    .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id일 경우")
        class Context_notExistId {

            private Long notExistProductId;

            @BeforeEach
            void setUp() {
                Long productId = product.getId();

                productService.deleteProduct(productId);

                assertThat(checkHasId(productId)).isFalse();

                notExistProductId = productId;
            }

            @Test
            @DisplayName("404를 응답한다")
            void it_response_404() throws Exception {
                mockMvc.perform(get("/products/" + notExistProductId))
                    .andExpect(status().isNotFound());
            }
        }
    }


    @Nested
    @DisplayName("PATCH /products/{id} 요청은")
    class Describe_patchProductWithId {

        @Nested
        @DisplayName("존재하는 id일 경우")
        class Context_existId {

            private Long existProductId;

            @BeforeEach
            void setUp() {
                Long productId = product.getId();

                assertThat(checkHasId(productId)).isTrue();

                existProductId = productId;
            }

            @Test
            @DisplayName("수정한 상품을 반환하고 200을 응답한다")
            void it_return_updated_product_and_response_200() throws Exception {
                Product source = Product.builder()
                    .name("newName")
                    .maker("newMaker")
                    .price(100L)
                    .imageUrl("newImageUrl")
                    .build();

                mockMvc.perform(patch("/products/" + existProductId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(source.toString()))
                    .andExpect(content().json(source.toString()))
                    .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id일 경우")
        class Context_notExistId {

            private Long notExistProductId;

            @BeforeEach
            void setUp() {
                Long productId = product.getId();

                productService.deleteProduct(productId);

                assertThat(checkHasId(productId)).isFalse();

                notExistProductId = productId;
            }

            @Test
            @DisplayName("404를 응답한다")
            void it_response_404() throws Exception {
                mockMvc.perform(patch("/products/" + notExistProductId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(product.toString()))
                    .andExpect(status().isNotFound());
            }
        }
    }


    @Nested
    @DisplayName("DELETE /products/{id} 요청은")
    class Describe_deleteProductWithId {

        @Nested
        @DisplayName("존재하는 id일 경우")
        class Context_existId {

            private Long existProductId;

            @BeforeEach
            void setUp() {
                Long productId = product.getId();

                assertThat(checkHasId(productId)).isTrue();

                existProductId = productId;
            }

            @Test
            @DisplayName("상품을 삭제하고 204를 응답한다")
            void it_delete_product_and_response_204() throws Exception {
                mockMvc.perform(delete("/products/" + existProductId))
                    .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id일 경우")
        class Context_notExistId {

            private Long notExistProductId;

            @BeforeEach
            void setUp() {
                Long productId = product.getId();

                productService.deleteProduct(productId);

                assertThat(checkHasId(productId)).isFalse();

                notExistProductId = productId;
            }

            @Test
            @DisplayName("404를 응답한다")
            void it_response_404() throws Exception {
                mockMvc.perform(delete("/products/" + notExistProductId))
                    .andExpect(status().isNotFound());
            }
        }
    }

    private boolean checkHasId(Long productId) {
        List<Product> products = new ArrayList<>();

        Iterator<Product> iterator = productService.getAllProducts()
            .iterator();
        iterator.forEachRemaining(products::add);

        return products.stream()
            .anyMatch(product -> productId.equals(product.getId()));
    }
}
