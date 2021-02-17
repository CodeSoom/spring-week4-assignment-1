package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스")
class ProductControllerTest {
    //fixture
    final Long NOT_EXIST_ID = 100L;
    final String NAME = "My Toy";
    final String MAKER = "My Home";
    final Long PRICE = 5000L;
    final String IMAGE_URL = "https://cdn.pixabay.com/photo/2016/10/01/20/54/mouse-1708347_1280.jpg";

    final String UPDATE_NAME = "My New Toy";
    final String UPDATE_MAKER = "My New Home";
    final Long UPDATE_PRICE = 7000L;
    final String UPDATE_IMAGE_URL = "https://cdn.pixabay.com/photo/2016/10/01/20/54/mouse-1708347_12801.jpg";

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProductService productService;

    //subject
    Product createProduct() {
        Product product = new Product();
        product.setName(NAME);
        product.setMaker(MAKER);
        product.setPrice(PRICE);
        product.setImageUrl(IMAGE_URL);
        return product;
    }

    Product createAndSaveProduct() {
        Product product = createProduct();
        return productService.create(product);
    }

    @BeforeEach
    void setUp() {
        productService.clearData();
    }

    @Nested
    @DisplayName("POST /products 리퀘스트는")
    class Describe_POST {
        @Nested
        @DisplayName("product가 주어진다면")
        class Context_with_product {
            Product givenProduct;
            @BeforeEach
            void setUp() {
                givenProduct = createProduct();
            }

            @DisplayName("201코드와 product를 응답한다")
            @Test
            void it_responds_201_code_and_product() throws Exception {
                mockMvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenProduct)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("name").value(NAME))
                        .andExpect(jsonPath("price").value(PRICE))
                        .andExpect(jsonPath("imageUrl").value(IMAGE_URL))
                        .andExpect(jsonPath("maker").value(MAKER));
            }
        }
    }

    @Nested
    @DisplayName("GET /products 리퀘스트는")
    class Describe_GET_Products {
        @Nested
        @DisplayName("product가 존재한다면")
        class Context_product_exist {
            @BeforeEach
            void setUp() {
                createAndSaveProduct();
            }

            @Test
            @DisplayName("product 리스트를 반환한다")
            void it_return_product_list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(NAME)))
                        .andExpect(content().string(containsString(IMAGE_URL)))
                        .andExpect(content().string(containsString(MAKER)));
            }
        }

        @Nested
        @DisplayName("product가 존재하지 않는다면")
        class Context_product_not_exist {
            @Test
            @DisplayName("비어있는 product 리스트를 반환한다")
            void it_return_product_list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("[]"));
            }
        }
    }

    @Nested
    @DisplayName("GET /products/{id} 리퀘스트는")
    class Describe_GET_Product {
        @Nested
        @DisplayName("존재하는 product id를 요청한다면")
        class Context_exist_id {
            Long givenProductId;

            @BeforeEach
            void setUp() {
                Product givenProduct = createAndSaveProduct();
                givenProductId = givenProduct.getId();
            }

            @DisplayName("200코드와 id와 일치하는 product를 반환한다")
            @Test
            void it_returns_200_code_and_product() throws Exception {
                mockMvc.perform(get("/products/" + givenProductId))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(NAME)))
                        .andExpect(content().string(containsString(IMAGE_URL)))
                        .andExpect(content().string(containsString(MAKER)));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 product id를 요청한다면")
        class Context_not_exist_id {
            Long givenProductId = NOT_EXIST_ID;

            @DisplayName("404코드를 반환한다")
            @Test
            void it_returns_200_code_and_product() throws Exception {
                mockMvc.perform(get("/products/" + givenProductId))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /products/{id} 리퀘스트는")
    class Describe_PATCH_Product {
        Product updateSource;

        @BeforeEach
        void setUp() {
            updateSource = new Product();
            updateSource.setName(UPDATE_NAME);
            updateSource.setMaker(UPDATE_MAKER);
            updateSource.setPrice(UPDATE_PRICE);
            updateSource.setImageUrl(UPDATE_IMAGE_URL);
        }

        @Nested
        @DisplayName("존재하는 product id와 수정한 product를 요청한다면")
        class Context_exist_id_with_product {
            Long givenProductId;
            @BeforeEach
            void setUp() {
                Product givenProduct = createAndSaveProduct();
                givenProductId = givenProduct.getId();
            }

            @DisplayName("200코드와 수정된 product를 응답한다")
            @Test
            void it_returns_200_code_with_product() throws Exception {
                mockMvc.perform(patch("/products/{id}", givenProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateSource)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(UPDATE_NAME))
                        .andExpect(jsonPath("$.maker").value(UPDATE_MAKER))
                        .andExpect(jsonPath("$.price").value(UPDATE_PRICE))
                        .andExpect(jsonPath("$.imageUrl").value(UPDATE_IMAGE_URL));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 product id와 수정한 product를 요청한다면")
        class Context_not_exist_id_with_product {
            Long givenProductId = NOT_EXIST_ID;

            @DisplayName("404코드를 응답한다")
            @Test
            void it_responds_not_found() throws Exception {
                mockMvc.perform(patch("/products/{id}", givenProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateSource)))
                        .andExpect(status().isNotFound());
            }
        }
    }


    @Nested
    @DisplayName("DELETE /products/{id} 리퀘스트는")
    class Describe_DELETE_Product {
        @Nested
        @DisplayName("존재하는 product id를 요청한다면")
        class Context_exist_id {
            Long givenProductId;

            @BeforeEach
            void setUp() {
                Product givenProduct = createAndSaveProduct();
                givenProductId = givenProduct.getId();
            }

            @DisplayName("204코드를 응답한다")
            @Test
            void it_responds_204() throws Exception {
                mockMvc.perform(delete("/products/{id}", givenProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 product id를 요청한다면")
        class Context_not_exist_id {
            Long givenProductId = NOT_EXIST_ID;

            @Test
            @DisplayName("404코드를 응답한다")
            void it_responds_not_found() throws Exception {
                mockMvc.perform(delete("/products/{id}", givenProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
