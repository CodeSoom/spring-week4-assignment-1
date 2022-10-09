package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.query.ProductQueryService;
import com.codesoom.assignment.common.ProductFactory;
import com.codesoom.assignment.common.exception.ProductNotFoundException;
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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스")
class ProductQueryControllerTest {
    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductQueryService productService;

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
        ResultActions subject() throws Exception {
            return mockMvc.perform(get("/products"));
        }

        @Nested
        @DisplayName("상품이 존재하면")
        class Context_with_products {
            private final List<ProductInfo> givenProducts = new ArrayList<>();

            @BeforeEach
            void prepare() {
                givenProducts.add(new ProductInfo(ProductFactory.createProduct(1L)));
                givenProducts.add(new ProductInfo(ProductFactory.createProduct(2L)));

                given(productService.getProducts()).willReturn(givenProducts);
            }

            @Test
            @DisplayName("OK(200)와 모든 상품을 리턴한다")
            void it_returns_200_and_all_products() throws Exception {
                final ResultActions resultActions = subject();

                resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].name").value(equalTo(givenProducts.get(0).getName())))
                        .andExpect(jsonPath("$[1].name").value(equalTo(givenProducts.get(1).getName())))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("상품이 존재하지 않으면")
        class Context_with_empty_db {
            @BeforeEach
            void prepare() {
                given(productService.getProducts()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("OK(200)와 빈 데이터를 리턴한다")
            void it_return_200_and_empty_array() throws Exception {
                final ResultActions resultActions = subject();

                resultActions.andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string("[]"))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("detail[/products/id::GET] 메소드는")
    class Describe_detail {
        ResultActions subject(Long id) throws Exception {
            return mockMvc.perform(get("/products/" + id));
        }

        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private final Long PRODUCT_ID = 1L;
            private final ProductInfo givenProduct = new ProductInfo(ProductFactory.createProduct(1L));

            @BeforeEach
            void prepare() {
                given(productService.getProduct(PRODUCT_ID)).willReturn(givenProduct);
            }

            @Test
            @DisplayName("OK(200)와 요청한 상품을 리턴한다")
            void it_returns_200_and_searched_product() throws Exception {
                final ResultActions resultActions = subject(PRODUCT_ID);

                resultActions.andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").value(equalTo(givenProduct.getName())))
                        .andExpect(jsonPath("maker").value(equalTo(givenProduct.getMaker())))
                        .andDo(print());


            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id {
            private final Long PRODUCT_ID = 100L;

            @BeforeEach
            void prepare() {
                given(productService.getProduct(PRODUCT_ID)).willThrow(new ProductNotFoundException(PRODUCT_ID));
            }

            @Test
            @DisplayName("NOT_FOUND(404)와 예외 메시지를 리턴한다")
            void it_returns_404_and_message() throws Exception {
                final ResultActions resultActions = subject(PRODUCT_ID);

                resultActions.andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("message", containsString("요청하신 상품이 없습니다.")))
                        .andDo(print());

            }
        }

    }
}

