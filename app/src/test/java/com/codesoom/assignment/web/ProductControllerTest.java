package com.codesoom.assignment.web;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private Product product;

    public static final String NAME = "털뭉치";
    public static final String MAKER = "애옹이네 장난감";
    public static final int PRICE = 2000;
    public static final String IMAGE_URL = "https://cdn.pixabay.com/photo/2018/10/05/12/09/animal-3725762_960_720.jpg";

    @BeforeEach
    void setUp() {
        product = new Product(NAME, MAKER, PRICE, IMAGE_URL);
    }

    @Nested
    @DisplayName("POST /products 요청이 오면")
    class Describe_post_products {

        @BeforeEach
        void setUp() {
            given(productService.createTask(any()))
                    .willReturn(product);
        }

        @Test
        @DisplayName("생성된 product를 반환한다")
        void it_returns_created_product() throws Exception {
            mockMvc.perform(post("/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(productToString(product)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").value(NAME))
                    .andExpect(jsonPath("$.maker").value(MAKER))
                    .andExpect(jsonPath("$.price").value(PRICE))
                    .andExpect(jsonPath("$.imageUrl").value(IMAGE_URL));
        }
    }

    public String productToString(Product product) throws JsonProcessingException {
        return objectMapper.writeValueAsString(product);
    }
}
