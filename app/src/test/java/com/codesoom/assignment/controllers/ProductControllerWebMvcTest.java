package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.hamcrest.core.StringContains;
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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private final String SETUP_PRODUCT_IMAGEURL = "setupImage";

    private final String CREATED_PRODUCT_NAME = "createdName";
    private final String CREATED_PRODUCT_MAKER = "createdMaker";
    private final int CREATED_PRODUCT_PRICE = 200;
    private final String CREATED_PRODUCT_IMAGEURL = "createdImage";

    private final String UPDATED_PRODUCT_NAME = "updatedName";
    private final String UPDATED_PRODUCT_MAKER = "updatedMaker";
    private final int UPDATED_PRODUCT_PRICE = 300;
    private final String UPDATED_PRODUCT_IMAGEURL = "updatedImage";

    private final Long EXISTED_ID = 1L;
    private final Long CREATED_ID = 2L;
    private final Long NOT_EXISTED_ID = 100L;

    private List<Product> products;
    private Product setupProduct;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();

        setupProduct = new Product(EXISTED_ID, SETUP_PRODUCT_NAME, SETUP_PRODUCT_MAKER,
                SETUP_PRODUCT_PRICE, SETUP_PRODUCT_IMAGEURL);

        products.add(setupProduct);
    }

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list {
        private final Product mockProduct = Product.builder()
                .id(CREATED_ID)
                .name(CREATED_PRODUCT_NAME)
                .maker(CREATED_PRODUCT_MAKER)
                .price(CREATED_PRODUCT_PRICE)
                .imageUrl(CREATED_PRODUCT_IMAGEURL)
                .build();

        @BeforeEach
        void setUp() {
            productService.createProduct(mockProduct);
            products.add(mockProduct);
        }

        @Test
        @DisplayName("전체 고양이 장난감 목록과 OK를 리턴하며 목록의 내용 중 이름이 일치하는지 확인한다")
        void itReturnsProductsAndOKHttpStatus() throws Exception {
            given(productService.getProducts()).willReturn(products);

            mockMvc.perform(get("/products"))
                    .andExpect(content().string(containsString(SETUP_PRODUCT_NAME)))
                    .andExpect(content().string(containsString(CREATED_PRODUCT_NAME)))
                    .andExpect(status().isOk());

            verify(productService).getProducts();
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
                System.out.println(setupProduct+"===");
                mockMvc.perform(get("/products/"+ givenExistedId))
                        .andDo(print())
                        .andExpect(jsonPath("$.id").value(givenExistedId))
                        .andExpect(jsonPath("$.name").value(SETUP_PRODUCT_NAME))
                        .andExpect(jsonPath("$.maker").value(SETUP_PRODUCT_MAKER))
                        .andExpect(jsonPath("$.imageUrl").value(SETUP_PRODUCT_IMAGEURL))
                        .andExpect(status().isOk());

                verify(productService).getProduct(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 메세지와 NOT_FOUND를 리턴한다")
            void itThrowsProductNotFoundExceptionAndReturnsErrorResponseAndNOT_FOUNDHttpStatus() throws Exception {
                given(productService.getProduct(givenNotExistedId))
                        .willThrow(ProductNotFoundException.class);

                mockMvc.perform(get("/products/"+ givenNotExistedId))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(content().string(containsString("Product Not Found")));

                verify(productService).getProduct(givenNotExistedId);
            }
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("만약 고양이 장난감 객체가 주어진다면")
        class Context_WithNameAndMakerAndPriceAndImage {
            Product createProduct() {
                return new Product(CREATED_ID, CREATED_PRODUCT_NAME, CREATED_PRODUCT_MAKER, CREATED_PRODUCT_PRICE, CREATED_PRODUCT_IMAGEURL);
            }

            @Test
            @DisplayName("객체를 저장하고 저장된 객체와 Created를 리턴한다")
            void itCreatesProductAndReturnsCreatedProductAndCreatedHttpStatus() throws Exception {
                Product createdProduct = createProduct();
                given(productService.createProduct(any(Product.class))).willReturn(createdProduct);

                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"createdName\" , \"maker\":\"createdMaker\", \"price\":100, \"image\":\"createdImage\"}"))
                        .andDo(print())
                        .andExpect(jsonPath("$.id").value(CREATED_ID))
                        .andExpect(jsonPath("$.name").value(CREATED_PRODUCT_NAME))
                        .andExpect(jsonPath("$.maker").value(CREATED_PRODUCT_MAKER))
                        .andExpect(jsonPath("$.price").value(CREATED_PRODUCT_PRICE))
                        .andExpect(jsonPath("$.imageUrl").value(CREATED_PRODUCT_IMAGEURL))
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
            private final String givenImage = UPDATED_PRODUCT_IMAGEURL;

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
                        .andExpect(jsonPath("$.imageUrl").value(givenImage))
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
                        .andExpect(jsonPath("$.imageUrl").value(SETUP_PRODUCT_IMAGEURL))
                        .andExpect(status().isNoContent());

                verify(productService).deleteProduct(givenExistedId);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 고양이 장난감의 아이디가 주어진다면")
        class Context_WithNotExistedId {
            private final Long givenNotExistedId = NOT_EXISTED_ID;

            @Test
            @DisplayName("고양이 장난감을 찾을 수 없다는 메세지와 NOT_FOUND를 리턴한다")
            void itThrowsProductNotFoundExceptionAndReturnsErrorResponseAndNOT_FOUNDHttpStatus() throws Exception {
                given(productService.deleteProduct(givenNotExistedId))
                        .willThrow(ProductNotFoundException.class);

                mockMvc.perform(delete("/products/" + givenNotExistedId))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(content().string(containsString("Product Not Found")));


                verify(productService).deleteProduct(givenNotExistedId);
            }
        }
    }
}
