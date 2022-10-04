package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.common.exception.InvalidParamException;
import com.codesoom.assignment.controller.ProductDto.RequestParam;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductCommand.Register;
import com.codesoom.assignment.domain.ProductInfo;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스")
class ProductControllerTest {
    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setup() {
        // ResponseBody JSON에 한글이 깨지는 문제로 추가
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Nested
    @DisplayName("list[/products::GET] 메소드는")
    class Describe_list {
        @Nested
        @DisplayName("상품이 존재하면")
        class Context_with_products {
            private List<ProductInfo> givenProducts = new ArrayList<>();

            @BeforeEach
            void prepare() {
                Product product1 = Product.builder()
                        .id(1L)
                        .name("고양이 장난감1")
                        .maker("삼성")
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();
                Product product2 = Product.builder()
                        .id(2L)
                        .name("고양이 장난감2")
                        .maker("애플")
                        .price(12000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                givenProducts.add(new ProductInfo(product1));
                givenProducts.add(new ProductInfo(product2));

                given(productService.getProducts()).willReturn(givenProducts);
            }

            @Test
            @DisplayName("OK(200)와 모든 상품을 리턴한다")
            void it_returns_200_and_all_products() throws Exception {
                ResultActions resultActions = mockMvc.perform(get("/products"));
                ;

                resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].name").value(equalTo(givenProducts.get(0).getName())))
                        .andExpect(jsonPath("$[1].name").value(equalTo(givenProducts.get(1).getName())))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("detail[/products/id::GET] 메소드는")
    class Describe_detail {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private final Long PRODUCT_ID = 1L;
            private ProductInfo givenProduct;

            @BeforeEach
            void prepare() {
                Product product = Product.builder()
                        .id(PRODUCT_ID)
                        .name("고양이 장난감1")
                        .maker("삼성")
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                givenProduct = new ProductInfo(product);

                given(productService.getProduct(any(Long.class))).willReturn(givenProduct);
            }

            @Test
            @DisplayName("OK(200)와 요청한 상품을 리턴한다")
            void it_returns_200_and_searched_product() throws Exception {
                ResultActions resultActions = mockMvc.perform(get("/products/" + PRODUCT_ID));

                resultActions.andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").value(equalTo(givenProduct.getName())))
                        .andExpect(jsonPath("maker").value(equalTo(givenProduct.getMaker())))
                        .andDo(print());


            }
        }
    }

    @Nested
    @DisplayName("registerProduct[/products::POST] 메소드는")
    class Describe_registerProduct {
        @Nested
        @DisplayName("새로운 상품을 등록하면")
        class Context_with_new_product {
            private RequestParam givenRequest;

            @BeforeEach
            void prepare() {
                givenRequest = new RequestParam();
                givenRequest.setName("고양이 장난감1");
                givenRequest.setMaker("삼성");
                givenRequest.setPrice(10000L);
                givenRequest.setImageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png");

                Product product = Product.builder()
                        .id(1L)
                        .name("고양이 장난감1")
                        .maker("삼성")
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                ProductInfo givenProduct = new ProductInfo(product);

                given(productService.createProduct(any(Register.class))).willReturn(givenProduct);
            }

            @Test
            @DisplayName("CREATED(201)와 등록된 상품을 리턴한다")
            void it_returns_201_and_registered_product() throws Exception {
                ResultActions resultActions = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)));

                resultActions.andExpect(status().isCreated())
                        .andExpect(jsonPath("name").value(equalTo(givenRequest.getName())))
                        .andExpect(jsonPath("maker").value(equalTo(givenRequest.getMaker())))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("updateProduct[/products/id::PATCH] 메소드는")
    class Describe_updateProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private RequestParam givenRequest;
            private ProductInfo modifiedProduct;

            @BeforeEach
            void prepare() {
                givenRequest = new RequestParam();
                givenRequest.setId(1L);
                givenRequest.setName("고양이 장난감1");
                givenRequest.setMaker("삼성");
                givenRequest.setPrice(10000L);
                givenRequest.setImageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png");

                Product product = Product.builder()
                        .id(1L)
                        .name("수정된 고양이 장난감1")
                        .maker("삼성")
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build();

                modifiedProduct = new ProductInfo(product);

                given(productService.updateProduct(any(Register.class))).willReturn(modifiedProduct);
            }

            @Test
            @DisplayName("상품을 수정하고 OK(200)와 수정된 상품을 리턴한다")
            void it_returns_modified_product() throws Exception {
                ResultActions resultActions = mockMvc.perform(patch("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenRequest)));

                resultActions.andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").value(equalTo(modifiedProduct.getName())))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct[/products/id::DELETE] 메소드는")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            @Test
            @DisplayName("상품을 삭제하고 NO_CONTENT(204)를 리턴한다")
            void it_returns_204() throws Exception {
                ResultActions resultActions = mockMvc.perform(delete("/products/1")
                        .contentType(MediaType.APPLICATION_JSON));

                resultActions.andExpect(status().isNoContent())
                        .andDo(print());
            }
        }
    }


}
