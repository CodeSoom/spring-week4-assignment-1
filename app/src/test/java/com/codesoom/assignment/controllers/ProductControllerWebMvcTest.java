package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(ProductController.class)
@DisplayName("ProductController 테스트")
class ProductControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final String SETUP_PRODUCT_NAME = "setupName";
    private final String SETUP_PRODUCT_MAKER = "setupMaker";
    private final int SETUP_PRODUCT_PRICE = 100;
    private final String SETUP_PRODUCT_IMAGE = "setupImage";

    private final Long EXISTED_ID = 1L;
    private final Long CREATE_ID = 2L;
    private final Long NOT_EXISTED_ID = 100L;

    private List<Product> products;
    private Product setupProduct;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();

        setupProduct = new Product(EXISTED_ID, SETUP_PRODUCT_NAME, SETUP_PRODUCT_MAKER,
                SETUP_PRODUCT_PRICE, SETUP_PRODUCT_IMAGE);

        products.add(setupProduct);
    }

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list {
        @Test
        @DisplayName("전체 고양이 장난감 목록과 OK를 리턴한다")
        void itReturnsOKHttpStatus() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("detail 메서드는")
    class Describe_detail {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 id가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 id에 해당하는 고양이 장난감과 OK를 리턴한다")
            void itReturnsOKHttpStatus() throws Exception {
                given(productService.getProduct(givenExistedId)).willReturn(setupProduct);

                mockMvc.perform(get("/products/"+ givenExistedId))
                        .andDo(print())
                        .andExpect(jsonPath("$.id").value(EXISTED_ID))
                        .andExpect(jsonPath("$.name").value(SETUP_PRODUCT_NAME))
                        .andExpect(jsonPath("$.maker").value(SETUP_PRODUCT_MAKER))
                        .andExpect(jsonPath("$.image").value(SETUP_PRODUCT_IMAGE))
                        .andExpect(status().isOk());

                verify(productService).getProduct(givenExistedId);
            }
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("만약 name, maker, price, image가 주어진다면")
        class Context_WithNameAndMakerAndPriceAndImage {
            private String name;
            private String maker;
            private int price;
            private String image;

            @BeforeEach
            void setUpCreateProduct() {
                name = "createdName";
                maker = "createdMaker";
                price = 200;
                image = "createdImage";
            }

            Product makeNewProduct() {
                return new Product(CREATE_ID, name, maker, price, image);
            }

            @Test
            @DisplayName("새로운 고양이 장난감을 생성하고 생성된 고양이 장난감과 Created를 리턴한다")
            void itCreatesProductAndReturnsCreatedProduct() throws Exception {
                Product createdProduct = makeNewProduct();

                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"name\":\"createdName\" , \"maker\":\"createdMaker\", \"prcie\":100, \"image\":\"createdImage\""))
                        .andExpect(status().isCreated());

                verify(productService).createProduct(any());
            }
        }

    }

}
