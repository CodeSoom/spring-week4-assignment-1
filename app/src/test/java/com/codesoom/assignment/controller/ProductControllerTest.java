package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        product.setImageURL(IMAGE_URL);
        return product;
    }

    Product createAndSaveProduct() {
        Product product = createProduct();
        return productService.save(product);
    }

    @BeforeEach
    void setUp() {
        productService.clearData();
    }

    @Nested
    @DisplayName("POST 메소드 리퀘스트는")
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
                        .andExpect(jsonPath("imageURL").value(IMAGE_URL))
                        .andExpect(jsonPath("maker").value(MAKER));
            }
        }
    }
}
