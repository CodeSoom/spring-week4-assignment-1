package com.codesoom.assignment.web.controller;

import com.codesoom.assignment.core.application.ProductService;
import com.codesoom.assignment.core.domain.Product;
import com.codesoom.assignment.web.exception.InvalidProductException;
import com.codesoom.assignment.web.exception.ProductNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController web")
class ProductControllerWebTest {
    private final Long ID = 1L;
    private final Long INVALID_ID = 9999L;
    private final String NAME = "장난감 이름";
    private final String BRAND = "장난감 브랜드";
    private final int PRICE = 10000;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Nested
    @DisplayName("GET /products")
    class DescribeGetProducts {
        List<Product> products;

        @Nested
        @DisplayName("장난감이 존재할 때")
        class ContextWithProducts {
            @BeforeEach
            void prepare() {
                products = new ArrayList<>();
                products.add(makeValidProduct(NAME, BRAND, PRICE));
                given(productService.fetchProducts()).willReturn(products);
            }

            @Test
            @DisplayName("장난감이 든 배열을 반환한다")
            void products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)))
                        .andExpect(jsonPath("$[0].name").value(NAME))
                        .andExpect(jsonPath("$[0].brand").value(BRAND))
                        .andExpect(jsonPath("$[0].price").value(PRICE));
            }
        }

        @Nested
        @DisplayName("장난감이 없을 때")
        class ContextWithEmptyProducts {
            @BeforeEach
            void prepare() {
                products = new ArrayList<>();
                given(productService.fetchProducts()).willReturn(products);
            }

            @Test
            @DisplayName("빈 배열을 반환한다")
            void products() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("[]"));
            }
        }
    }

    @Nested
    @DisplayName("POST /products")
    class DescribePostProducts {
        String content;

        @Nested
        @DisplayName("유효한 장난감이라면")
        class ContextWithValidProduct {
            @BeforeEach
            void prepare() throws JsonProcessingException {
                Product newProduct = makeValidProduct(NAME, BRAND, PRICE);
                content = objectMapper.writeValueAsString(newProduct);

                given(productService.saveProduct(any(Product.class))).willReturn(newProduct);
            }

            @Test
            @DisplayName("생성된 장난감을 반환한다")
            void products() throws Exception {
                mockMvc.perform(post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$").isNotEmpty())
                        .andExpect(jsonPath("$.name").value(NAME))
                        .andExpect(jsonPath("$.brand").value(BRAND))
                        .andExpect(jsonPath("$.price").value(PRICE));
            }
        }

        @Nested
        @DisplayName("잘못된 장난감이라면")
        class ContextWithInvalidProduct {
            @BeforeEach
            void prepare() throws JsonProcessingException {
                Product newProduct = makeInvalidProduct();
                content = objectMapper.writeValueAsString(newProduct);

                given(productService.saveProduct(any(Product.class)))
                        .willThrow(new InvalidProductException());
            }

            @Test
            @DisplayName("Bad Request 에러 코드를 반환한다")
            void list() throws Exception {
                mockMvc.perform(post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("GET /products/:id")
    class DescribeGetProductById {
        @Nested
        @DisplayName("ID에 해당하는 장난감이 존재할 때")
        class ContextWithProductById {
            @BeforeEach
                void prepare() {
                    Product foundProduct = makeValidProduct(NAME, BRAND, PRICE);
                    foundProduct.setId(ID);

                    given(productService.fetchProductById(eq(ID))).willReturn(foundProduct);
            }

            @Test
            @DisplayName("해당 장난감을 반환한다")
            void product() throws Exception {
                String uri = String.format("/products/%s", ID);

                mockMvc.perform(get(uri))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").isNotEmpty())
                        .andExpect(jsonPath("$.id").value(ID))
                        .andExpect(jsonPath("$.name").value(NAME))
                        .andExpect(jsonPath("$.brand").value(BRAND))
                        .andExpect(jsonPath("$.price").value(PRICE));
            }
        }

        @Nested
        @DisplayName("ID에 해당하는 장난감이 없을 때")
        class ContextWithoutProductById {
            @BeforeEach
            void prepare() {
                given(productService.fetchProductById(eq(ID))).willThrow(new ProductNotFoundException());
            }

            @Test
            @DisplayName("Not found 에러코드를 반환한다")
            void product() throws Exception {
                String uri = String.format("/products/%s", ID);

                mockMvc.perform(get(uri))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /products/{id}")
    class DescribeDeleteProductById {
        @Nested
        @DisplayName("ID에 해당하는 장난감이 존재할 때")
        class ContextWithProductById {
            @BeforeEach
            void prepare() {
                doNothing().when(productService).deleteProductById(eq(ID));
            }

            @Test
            @DisplayName("No content 응답코드를 반환한다")
            void deleteProduct() throws Exception {
                String uri = String.format("/products/%s", ID);

                mockMvc.perform(delete(uri))
                        .andExpect(status().isNoContent());

                verify(productService).deleteProductById(ID);
            }
        }

        @Nested
        @DisplayName("ID에 해당하는 장난감이 없을 때")
        class ContextWithoutProductById {
            @BeforeEach
            void prepare() {
                doThrow(new ProductNotFoundException()).when(productService).deleteProductById(eq(INVALID_ID));
            }

            @Test
            @DisplayName("Not found 에러코드를 반환한다")
            void deleteProduct() throws Exception {
                String uri = String.format("/products/%s", INVALID_ID);

                mockMvc.perform(delete(uri))
                        .andExpect(status().isNotFound());

                verify(productService).deleteProductById(INVALID_ID);
            }
        }
    }

    public Product makeValidProduct(String name, String brand, Integer price) {
        return Product.builder()
                .name(name)
                .brand(brand)
                .price(price)
                .build();
    }

    public Product makeInvalidProduct() {
        return Product.builder().build();
    }

}
