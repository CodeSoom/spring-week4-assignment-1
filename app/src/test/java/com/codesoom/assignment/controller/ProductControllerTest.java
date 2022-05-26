package com.codesoom.assignment.controller;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        given(productService.createProduct(any())).willReturn(product);
    }

    @Nested
    @DisplayName("[GET] /products 요청에 대해서")
    class Describe_list {
        @Test
        @DisplayName("getProducts 메서드는 product 의 리스트를 모두 반환한다.")
        void It_returns_product_list() throws Exception {
            mockMvc.perform(get("/products"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("dogCompany")));
        }
    }

    @Nested
    @DisplayName("[POST] /products 요청에 대해서")
    class Describe_save {
        @Test
        @DisplayName("createProduct 메서드는 product 의 리스트를 저장하고 반환한다.")
        void It_returns_status_created() throws Exception {
            mockMvc.perform(post("/products")
                    .content(objectMapper.writeValueAsString(product))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().string(containsString(product.getMaker())));
        }
    }
}
