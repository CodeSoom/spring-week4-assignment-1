package com.codesoom.assignment.controller;


import com.codesoom.assignment.application.ProductApplicationService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(ProductController.class)
@DisplayName("ProductController 클래스")
public class ProductControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductApplicationService productApplicationService;

    @BeforeEach
    void setUp() {
        String name = "라스 장난감";
        String maker = "라스 회사";
        String price = "1000";
        String imageURL = "https://magical.dev/statics/las.jpg";

        ProductId id = new ProductId(1L);
        Product product = new Product(id, name, maker, price, imageURL);

        given(productApplicationService.getProduct(1L)).willReturn(Optional.of(product));
        given(productApplicationService.getProduct(12L)).willReturn(Optional.empty());
    }

    @Nested
    @DisplayName("모든 product를 얻으려고 한다면")
    class Describe_getAllProduct {
        @Test
        @DisplayName("OK를 리턴한다")
        void itReturnsOKHttpStatus() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("특정한 product를 얻으려고 할 때")
    class Describe_getSpecificProduct {

        @Nested
        @DisplayName("얻으려먼 product가 존재한다면")
        class ContextWithValidId {
            private final Long givenValidId = 1L;

            @Test
            @DisplayName("OK를 리턴한다")
            void itReturnsOKHttpStatus() throws Exception {
                mockMvc.perform(get("/products/" + givenValidId))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("얻으려먼 product가 존재하지 않는다면")
        class ContextWithInvalidId {
            private final Long givenInvalidId = 12L;

            @Test
            @DisplayName("NOT FOUND를 리턴한다")
            void itReturnsNotFoundHttpStatus() throws Exception {
                mockMvc.perform(get("/products/" + givenInvalidId))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("특정한 product를 삭제하려고 할 때")
    class Describe_deleteProduct {

        @Nested
        @DisplayName("삭제하려는 product가 존재한다면")
        class ContextWithValidId {
            private final Long givenValidId = 1L;

            @Test
            @DisplayName("NO CONTENT를 리턴한다")
            void itReturnsNoContentHttpStatus() throws Exception {
                doNothing()
                        .when(productApplicationService)
                        .deleteProduct(isNotNull());

                mockMvc.perform(delete("/products/" + givenValidId))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("삭제하려는 product가 존재하지 않는다면")
        class ContextWithInvalidId {
            private final Long givenInvalidId = 12L;

            @Test
            @DisplayName("NOT FOUND를 리턴한다")
            void itReturnsNotFoundHttpStatus() throws Exception {
                doThrow(new ProductNotFoundException(givenInvalidId))
                        .when(productApplicationService)
                        .deleteProduct(isNotNull());

                mockMvc.perform(delete("/products/" + givenInvalidId))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("특정한 product를 변경하려고 할 때")
    class Describe_updateProduct {
        private final String updateJson = "{\"name\": \"las\",\"price\": 123,\"maker\": \"las\",\"imageUrl\": \"url\"}";

        @Nested
        @DisplayName("변경하려는 product가 존재한다면")
        class ContextWithValidId {
            private final Long givenValidId = 1L;

            @Test
            @DisplayName("OK를 리턴한다")
            void itReturnsOKHttpStatus() throws Exception {
                doNothing().when(productApplicationService);

                mockMvc.perform(
                        patch("/products/" + givenValidId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateJson))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("변경하려는 product가 존재하지 않는다면")
        class ContextWithInvalidId {
            private final Long givenInvalidId = 12L;

            @Test
            @DisplayName("NOT FOUND를 리턴한다")
            void itReturnsNotFoundHttpStatus() throws Exception {
                doThrow(new ProductNotFoundException(givenInvalidId))
                        .when(productApplicationService)
                        .updateProduct(any(), any(), any(), any(), any());

                mockMvc.perform(
                        patch("/products/" + givenInvalidId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateJson))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("product생성한다면")
    class Describe_createProduct {
        private final String createJson = "{\"name\": \"라스 장난감\",\"price\": 1000,\"maker\": \"라스 회사\",\"imageUrl\": \"https://magical.dev/statics/las.jpg\"}";

        @Test
        @DisplayName("CREATED를 리턴한다")
        void itReturnsCreatedHttpStatus() throws Exception {
            String name = "라스 장난감";
            String maker = "라스 회사";
            String price = "1000";
            String imageURL = "https://magical.dev/statics/las.jpg";

            ProductId id = new ProductId(1L);
            Product product = new Product(id, name, maker, price, imageURL);
            given(productApplicationService.createProduct(name, maker, price, imageURL)).willReturn(product);

            mockMvc.perform(
                    post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(createJson))
                    .andExpect(status().isCreated());
        }
    }
}
