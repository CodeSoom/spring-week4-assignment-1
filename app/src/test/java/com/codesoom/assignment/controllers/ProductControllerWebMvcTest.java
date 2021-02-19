package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
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
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private final String CREATED_PRODUCT_NAME = "createdName";
    private final String CREATED_PRODUCT_MAKER = "createdMaker";
    private final int CREATED_PRODUCT_PRICE = 200;
    private final String CREATED_PRODUCT_IMAGE = "createdImage";

    private final String UPDATED_PRODUCT_NAME = "updatedName";
    private final String UPDATED_PRODUCT_MAKER = "updatedMaker";
    private final int UPDATED_PRODUCT_PRICE = 300;
    private final String UPDATED_PRODUCT_IMAGE = "updatedImage";

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
        @DisplayName("만약 저장되어 있는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감과 OK를 리턴한다")
            void itReturnsProductAndOKHttpStatus() throws Exception {
                given(productService.getProduct(givenExistedId)).willReturn(setupProduct);

                mockMvc.perform(get("/products/"+ givenExistedId))
                        .andDo(print())
                        .andExpect(jsonPath("$.id").value(givenExistedId))
                        .andExpect(jsonPath("$.name").value(SETUP_PRODUCT_NAME))
                        .andExpect(jsonPath("$.maker").value(SETUP_PRODUCT_MAKER))
                        .andExpect(jsonPath("$.image").value(SETUP_PRODUCT_IMAGE))
                        .andExpect(status().isOk());

                verify(productService).getProduct(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 예외와 NOT_FOUND를 리턴한다")
            void itReturnsProductAndOKHttpStatus() throws Exception {
                given(productService.getProduct(givenNotExistedId))
                        .willThrow(ProductNotFoundException.class);

                mockMvc.perform(get("/products/"+ givenNotExistedId))
                        .andDo(print())
                        .andExpect(status().isNotFound());

                verify(productService).getProduct(givenNotExistedId);
            }
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("만약 이름, 메이커, 가격, 이미지가 주어진다면")
        class Context_WithNameAndMakerAndPriceAndImage {
            private final String givenName = CREATED_PRODUCT_NAME;
            private final String givenMaker = CREATED_PRODUCT_MAKER;
            private final int givenPrice = CREATED_PRODUCT_PRICE;
            private final String givenImage = CREATED_PRODUCT_IMAGE;

            Product createProduct() {
                return new Product(CREATE_ID, givenName, givenMaker, givenPrice, givenImage);
            }

            @Test
            @DisplayName("새로운 고양이 장난감을 생성하고 생성된 고양이 장난감과 Created를 리턴한다")
            void itCreatesProductAndReturnsCreatedProductAndCreatedHttpStatus() throws Exception {
                Product createdProduct = createProduct();
                given(productService.createProduct(any(Product.class))).willReturn(createdProduct);

                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"createdName\" , \"maker\":\"createdMaker\", \"price\":100, \"image\":\"createdImage\"}"))
                        .andDo(print())
                        .andExpect(jsonPath("$.id").value(CREATE_ID))
                        .andExpect(jsonPath("$.name").value(givenName))
                        .andExpect(jsonPath("$.maker").value(givenMaker))
                        .andExpect(jsonPath("$.price").value(givenPrice))
                        .andExpect(jsonPath("$.image").value(givenImage))
                        .andExpect(status().isCreated());

                verify(productService).createProduct(any());
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {
        @Nested
        @DisplayName("만약 저징되어 있는 고양이 장난감의 아이디와 업데이트 될 이름, 메이커, 가격, 이미지가 주어진다면")
        class Context_WithExistedIdAndNameAndMakerAndPriceAndImage {
            private final Long givenExistedId = EXISTED_ID;
            private final String givenName = UPDATED_PRODUCT_NAME;
            private final String givenMaker = UPDATED_PRODUCT_MAKER;
            private final int givenPrice = UPDATED_PRODUCT_PRICE;
            private final String givenImage = UPDATED_PRODUCT_IMAGE;

            Product updateProduct() {
                return new Product(givenExistedId, givenName, givenMaker, givenPrice, givenImage);
            }

            @Test
            @DisplayName("주어진 id에 해당하는 고양이 장난감을 업데이트하고 수정된 고양이 장난감과 OK를 리턴한다")
            void itUpdatesProductAndReturnsUpdatedProductAndOKHttpStatus() throws Exception {
                Product updatedProduct = updateProduct();
                given(productService.updateProduct(eq(givenExistedId), any(Product.class))).willReturn(updatedProduct);

                mockMvc.perform(patch("/products/" + givenExistedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"updatedName\" , \"maker\":\"updatedMaker\", \"price\":300, \"image\":\"updatedImage\"}"))
                        .andDo(print())
                        .andExpect(jsonPath("$.id").value(givenExistedId))
                        .andExpect(jsonPath("$.name").value(givenName))
                        .andExpect(jsonPath("$.maker").value(givenMaker))
                        .andExpect(jsonPath("$.price").value(givenPrice))
                        .andExpect(jsonPath("$.image").value(givenImage))
                        .andExpect(status().isOk());

                verify(productService).updateProduct(eq(givenExistedId), any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {
        @Nested
        @DisplayName("만약 저장되어 있는 고양이 장난감의 아이디가 주어진다면")
        class Context_WithExistedId {
            private final Long givenExistedId = EXISTED_ID;

            @Test
            @DisplayName("주어진 아이디에 해당하는 고양이 장난감을 삭제하고 삭제된 고양이 장난감과 NO_CONTENT를 리턴한다")
            void itDeleteProductAndReturnsNO_CONTENTHttpStatus() throws Exception {
                given(productService.deleteProduct(givenExistedId)).willReturn(setupProduct);

                mockMvc.perform(delete("/products/" + givenExistedId))
                        .andDo(print())
                        .andExpect(jsonPath("$.id").value(givenExistedId))
                        .andExpect(jsonPath("$.name").value(SETUP_PRODUCT_NAME))
                        .andExpect(jsonPath("$.maker").value(SETUP_PRODUCT_MAKER))
                        .andExpect(jsonPath("$.price").value(SETUP_PRODUCT_PRICE))
                        .andExpect(jsonPath("$.image").value(SETUP_PRODUCT_IMAGE))
                        .andExpect(status().isNoContent());

                verify(productService).deleteProduct(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 예외와 NOT_FOUND를 리턴한다")
            void itDeleteProductAndReturnsNO_CONTENTHttpStatus() throws Exception {
                given(productService.deleteProduct(givenNotExistedId)).willReturn(setupProduct);

                mockMvc.perform(delete("/products/" + givenNotExistedId))
                        .andDo(print())
                        .andExpect(status().isNotFound());

                verify(productService).deleteProduct(givenNotExistedId);
            }
        }
    }
}
