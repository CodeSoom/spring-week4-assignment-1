package com.codesoom.assignment.web;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.dto.ProductCommandRequest;
import com.codesoom.assignment.dto.ProductResponse;
import com.codesoom.assignment.exception.ProductNotFoundException;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private ProductResponse productResponse;
    private ProductCommandRequest productCommandRequest;

    public static final Long VALID_ID = 1L;
    public static final Long INVALID_ID = 199L;
    public static final String NAME = "털뭉치";
    public static final String MAKER = "애옹이네 장난감";
    public static final int PRICE = 2000;
    public static final String IMAGE_URL = "https://cdn.pixabay.com/photo/2018/10/05/12/09/animal-3725762_960_720.jpg";

    @BeforeEach
    void setUp() {
        productCommandRequest = ProductCommandRequest.builder()
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .imageUrl(IMAGE_URL)
                .build();
        productResponse = new ProductResponse(VALID_ID, NAME, MAKER, PRICE, IMAGE_URL);
    }

    @Nested
    @DisplayName("POST /products 요청은")
    class Describe_post_products {

        @BeforeEach
        void setUp() {
            given(productService.createTask(any()))
                    .willReturn(productResponse);
        }

        @Test
        @DisplayName("생성된 product를 반환한다")
        void it_returns_created_product() throws Exception {
            mockMvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objToString(productResponse)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").value(NAME))
                    .andExpect(jsonPath("$.maker").value(MAKER))
                    .andExpect(jsonPath("$.price").value(PRICE))
                    .andExpect(jsonPath("$.imageUrl").value(IMAGE_URL));
        }
    }

    @Nested
    @DisplayName("PATCH /products/{id} 요청은")
    class Describe_patch_product_by_id {

        @Nested
        @DisplayName("아이디가 존재하면")
        class Context_when_id_exists {

            @BeforeEach
            void setUp() {
                given(productService.updateProduct(eq(VALID_ID), any(ProductCommandRequest.class)))
                        .will(invocation -> {
                            Long id = invocation.getArgument(0);
                            ProductCommandRequest productCommandRequest = invocation.getArgument(1);
                            return ProductResponse.builder()
                                    .id(id)
                                    .name(productCommandRequest.getName())
                                    .price(productCommandRequest.getPrice())
                                    .maker(productCommandRequest.getMaker())
                                    .imageUrl(productCommandRequest.getImageUrl())
                                    .build();
                        });
            }

            @Test
            @DisplayName("변경된 product를 반환한다.")
            void it_returns_updated_product() throws Exception {
                mockMvc.perform(patch("/products/{id}", VALID_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objToString(productCommandRequest)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value(productCommandRequest.getName()))
                        .andExpect(jsonPath("$.maker").value(productCommandRequest.getMaker()))
                        .andExpect(jsonPath("$.price").value(productCommandRequest.getPrice()))
                        .andExpect(jsonPath("$.imageUrl").value(productCommandRequest.getImageUrl()));
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않으면")
        class Context_when_id_non_exists {

            @BeforeEach
            void setUp() {
                given(productService.updateProduct(eq(INVALID_ID), any()))
                        .willThrow(new ProductNotFoundException(INVALID_ID));
            }

            @Test
            @DisplayName("404 status를 응답한다.")
            void it_responses_404_status() throws Exception {
                mockMvc.perform(patch("/products/{id}", INVALID_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objToString(productCommandRequest)))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("GET /products/{id} 요청은")
    class Describe_get_products_by_id {

        @Nested
        @DisplayName("id가 존재하면")
        class Context_when_id_exist {
            private final Long VALID_ID = 1L;
            @BeforeEach
            void setUp() {
                given(productService.getProduct(VALID_ID))
                        .willReturn(productResponse);
            }

            @Test
            @DisplayName("productResponse를 응답한다.")
            void it_responses_productResponse() throws Exception {
                mockMvc.perform(get("/products/{id}", VALID_ID))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(productResponse.getId()))
                        .andExpect(jsonPath("$.name").value(productResponse.getName()))
                        .andExpect(jsonPath("$.maker").value(productResponse.getMaker()))
                        .andExpect(jsonPath("$.price").value(productResponse.getPrice()))
                        .andExpect(jsonPath("$.imageUrl").value(productResponse.getImageUrl()));
            }
        }
    }

    public String objToString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
