package com.codesoom.assignment.controller;

import com.codesoom.assignment.error.NotFoundException;
import com.codesoom.assignment.interfaces.ProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private Product product;

    private final Long PRODUCT_ID = 1L;
    private final String PRODUCT_NAME = "dog";
    private final String PRODUCT_MAKER = "dogCompany";
    private final Integer PRODUCT_PRICE = 200000;
    private final String PRODUCT_URI = "https://media.nature.com/lw800/magazine-assets/d41586-020-01430-5/d41586-020-01430-5_17977552.jpg";

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        product = new Product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_URI);
        products.add(product);

        given(productService.findProducts()).willReturn(products);
    }

    @Nested
    @DisplayName("[GET] /products 요청에 대해서")
    class Describe_list {
        @Nested
        @DisplayName("등록된 product 가 있으면")
        class Context_has_product {
            @Test
            @DisplayName("getProducts 메서드는 product 의 리스트를 모두 반환한다.")
            void It_returns_product_list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("dogCompany")));
            }
        }
    }

    @Nested
    @DisplayName("[POST] /products 요청에 대해서")
    class Describe_save {
        @BeforeEach
        void setUp() {
            given(productService.createProduct(any())).willReturn(product);
        }

        @Test
        @DisplayName("createProduct 메서드는 product 의 리스트를 저장하고 반환한다.")
        void It_returns_status_created() throws Exception {
            mockMvc.perform(post("/products")
                            .content(objectMapper.writeValueAsString(product))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string(containsString("dogCompany")));
        }
    }

    @Nested
    @DisplayName("[GET] /products/{id} 요청에 대해서")
    class Describe_get_product_by_id {
        @Nested
        @DisplayName("요청하는 id 가 있으면")
        class Context_when_exist_product_id {
            @BeforeEach
            void setUp() {
                given(productService.findProduct(PRODUCT_ID)).willReturn(product);
            }

            @Test
            @DisplayName("getProduct 메서드는 요청하는 id 에 대한 product 를 반환한다.")
            void It_returns_product() throws Exception {
                mockMvc.perform(get("/products/{id}", product.getId()))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("dogCompany")));
            }
        }

        @Nested
        @DisplayName("요청하는 id 가 없으면")
        class Context_when_not_exist_product_id {
            private final Long UNKNOWN_PRODUCT_ID = 100L;

            @BeforeEach
            void setUp() {
                given(productService.findProduct(UNKNOWN_PRODUCT_ID))
                        .willThrow(new NotFoundException(UNKNOWN_PRODUCT_ID));
            }

            @Test
            @DisplayName("getProduct 메서드는 NotFoundException 을 반환한다.")
            void It_returns_NotFoundException() throws Exception {
                mockMvc.perform(get("/products/{id}", UNKNOWN_PRODUCT_ID))
                        .andDo(print())
                        .andExpect(status().isNotFound());
            }
        }

    }

    @Nested
    @DisplayName("[PATCH] /products/{id} 요청에 대하여")
    class Describe_update_product {
        @Nested
        @DisplayName("요청하는 id 가 있으면")
        class Context_when_exist_product_id {
            private final String PREFIX = "new_";

            @BeforeEach
            void setUp() {
                product = new Product(product.getId(), PRODUCT_NAME, PREFIX + PRODUCT_MAKER, PRODUCT_PRICE, PRODUCT_URI);

                given(productService.updateProduct(product.getId(), product)).willReturn(product);

                System.out.println("productService.getMaker: " + productService.findProduct(product.getId()).getMaker());
//                given(productService.findProducts()).willReturn(products);

            }

            @Test
            @DisplayName("updateProduct 메서드는 요청하는 id 에 대한 product 를 반환한다.")
            void It_returns_patch_product() throws Exception {
                System.out.println("maker: " + product.getMaker());

                mockMvc.perform(patch("/products/{id}", PRODUCT_ID)
                                .content(objectMapper.writeValueAsString(product))
                                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("new_dogCompany")));
            }
        }
    }
}
