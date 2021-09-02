package com.codesoom.assignment.controllers;

import com.codesoom.assignment.applications.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductControllerTest 클래스")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper;

    private List<Product> givenProducts;
    private Product product1;
    private Product product2;
    private String contentProduct;
    private String contentProducts;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        objectMapper = new ObjectMapper();

        product1 = new Product(1L, "toy1", "maker1", 1000L, "toy1.jpg");
        product2 = new Product(2L, "toy2", "maker2", 2000L, "toy2.jpg");
        givenProducts = List.of(product1, product2);

        contentProduct = objectMapper.writeValueAsString(product1);
        contentProducts = objectMapper.writeValueAsString(givenProducts);
    }

    @Nested
    @DisplayName("POST /products")
    class Describe_POST_Path_products {

        @BeforeEach
        void setUp() {
            given(productService.save(any(Product.class))).willReturn(product1);
        }

        @Test
        @DisplayName("response(status: created) 반환합니다.")
        void it_response_created() throws Exception {
            mockMvc.perform(post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(contentProduct))
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    @DisplayName("GET /products 호출은")
    class Describe_GET_Path_prorducts {

        @BeforeEach
        void setUp() {
            given(productService.findAll()).willReturn(givenProducts);
        }

        @Test
        @DisplayName("response(status: ok, content: json tasks) 반환합니다.")
        void it_response_ok() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(contentProducts));
        }
    }

    @Nested
    @DisplayName("GET /products/{id} 호출은")
    class Describe_GET_Path_products_id {

        @Nested
        @DisplayName("해당되는 id의 product가 있다면")
        class Context_with_valid_id {

            private final Long VALID_ID = 1L;

            @BeforeEach
            void setUp() {
                given(productService.findById(VALID_ID)).willReturn(product1);
            }

            @Test
            @DisplayName("response(status: ok, content: json task) 반환합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(get("/products/" + VALID_ID))
                        .andExpect(status().isOk())
                        .andExpect(content().string(contentProduct));
            }
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private final Long INVALID_ID = 100L;

            @BeforeEach
            void setUp() {
                willThrow(new ProductNotFoundException(INVALID_ID)).given(productService).findById(INVALID_ID);
            }

            @Test
            @DisplayName("response(status: not_found)를 반환합니다.")
            void it_response_not_found() throws Exception {
                mockMvc.perform(get("/products/" + INVALID_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /products/{id} 호출은")
    class Describe_PATCH_Path_products_id {

        @Nested
        @DisplayName("해당되는 id의 product가 있다면")
        class Context_with_valid_id {

            private final Long VALID_ID = 1L;

            @BeforeEach
            void setUp() {
                willDoNothing().given(productService).update(eq(VALID_ID), any(Product.class));
            }

            @Test
            @DisplayName("response(status: ok)를 반환합니다.")
            void it_response_ok() throws Exception {
                mockMvc.perform(patch("/products/" + VALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentProduct))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private final Long INVALID_ID = 100L;

            @BeforeEach
            void setUp() {
                willThrow(new ProductNotFoundException(INVALID_ID)).given(productService).update(eq(INVALID_ID), any(Product.class));
            }

            @Test
            @DisplayName("response(status: not_found)를 반환합니다.")
            void it_response_not_found() throws Exception {
                mockMvc.perform(patch("/products/" + INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentProduct))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /products/{id}")
    class Describe_DELETE_Path_products_id {

        @Nested
        @DisplayName("해당되는 id의 product가 있다면")
        class Context_with_valid_id {

            private final Long VALID_ID = 1L;

            @BeforeEach
            void setUp() {
                willDoNothing().given(productService).deleteById(VALID_ID);
            }

            @Test
            @DisplayName("response(status: no_content)를 반환합니다.")
            void it_response_no_content() throws Exception {
                mockMvc.perform(delete("/products/" + VALID_ID))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("해당되는 id의 product가 없다면")
        class Context_with_invalid_id {

            private final Long INVALID_ID = 100L;

            @BeforeEach
            void setUp() {
                willThrow(new ProductNotFoundException(INVALID_ID)).given(productService).deleteById(INVALID_ID);
            }

            @Test
            @DisplayName("response(status: not_found)를 반환합니다.")
            void it_response_not_found() throws Exception {
                mockMvc.perform(delete("/products/" + INVALID_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }
}