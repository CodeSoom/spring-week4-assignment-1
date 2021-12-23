package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController 클래스")
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    private static final String PRODUCTS_URI = "/products";
    private static final String PRODUCTS_URI_SLASH = "/products/";

    @Nested
    @DisplayName("list() 메소드는")
    class Describe_list {
        @Nested
        @DisplayName("등록된 Product들이 존재하면")
        class Context_has_product {
            final int givenProductsCount = 5;

            @BeforeEach
            void prepare() {
                List<Product> products = new ArrayList<>();
                IntStream.range(0, givenProductsCount).forEach((i) -> products.add(getTestProduct()));

                given(productService.getProducts()).willReturn(products);
            }

            @Test
            @DisplayName("200(Ok)와 Product의 전체 리스트를 응답합니다.")
            void it_return_ok_and_product() throws Exception {
                mockMvc.perform(get(PRODUCTS_URI))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(givenProductsCount)))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("등록된 Product들이 없다면")
        class Context_has_not_product {
            final int givenProductsCount = 0;

            @BeforeEach
            void prepare() {
                List<Product> products = new ArrayList<>();

                given(productService.getProducts()).willReturn(products);
            }

            @Test
            @DisplayName("200(Ok)와 빈 리스트를 응답합니다.")
            void it_return_ok_and_products() throws Exception {
                mockMvc.perform(get(PRODUCTS_URI))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(givenProductsCount)))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("detail() 메소드는")
    class Describe_detail {
        @Nested
        @DisplayName("등록된 Product의 id가 주어진다면")
        class Context_with_id {
            Long givenId = 1L;

            @BeforeEach
            void prepare() {
                given(productService.getProduct(givenId)).willReturn(getTestProduct());
            }

            @Test
            @DisplayName("200(Ok)와 Product의 정보를 응답합니다.")
            void it_return_ok_and_product() throws Exception {
                mockMvc.perform(get(PRODUCTS_URI_SLASH + givenId))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").isNotEmpty())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("등록되지 않은 Product의 id가 주어진다면")
        class Context_with_invaild_id {
            Long givenInvalidId = 100L;

            @BeforeEach
            void prepare() {
                given(productService.getProduct(givenInvalidId)).willThrow(new ProductNotFoundException(givenInvalidId));
            }

            @Test
            @DisplayName("404(Not found)를 응답합니다.")
            void it_return_not_fount() throws Exception {
                mockMvc.perform(get(PRODUCTS_URI_SLASH + givenInvalidId))
                        .andExpect(status().isNotFound())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("create() 메소드는")
    class Describe_create {
        @Nested
        @DisplayName("Product가 주어진다면")
        class Context_with_product {
            final Long givenId = 1L;
            Product givenProduct = getTestProduct();

            @BeforeEach
            void prepare() {
                when(productService.createProduct(any(Product.class)))
                        .then((arg) -> {
                            Optional<Product> source = Optional.of(arg.getArgument(0, Product.class));

                            Product product = source.orElseThrow(() -> new NullPointerException());
                            product.setId(givenId);

                            return product;
                        });
            }

            @Test
            @DisplayName("201(Created)와 Product를 응답합니다.")
            void it_create_product_return_created_and_product() throws Exception {
                mockMvc.perform(post(PRODUCTS_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productToContent(givenProduct)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").value(givenId))
                        .andExpect(jsonPath("$.name").value(givenProduct.getName()))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("Product가 없다면")
        class Context_without_product {
            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_return_bad_request() throws Exception {
                mockMvc.perform(post(PRODUCTS_URI)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("update() 메소드는")
    class Describe_update {
        @Nested
        @DisplayName("등록된 Product의 Id와 수정할 Product가 주어진다면")
        class Context_with_id_and_product {
            Long givenId = 1L;
            Product givenProduct = getProductToBeUpdated();

            @BeforeEach
            void prepare() {
                when(productService.updateProduct(eq(givenId), any(Product.class)))
                        .then(arg -> {
                            Product product = arg.getArgument(1, Product.class);

                            product.setId(givenId);
                            return product;
                        });
            }

            @Test
            @DisplayName("PUT요청 / 200(Ok)과 Product를 응답합니다.")
            void it_put_update_product_return_ok_and_product() throws Exception {
                mockMvc.perform(put(PRODUCTS_URI_SLASH + givenId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(productToContent(givenProduct)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(givenId))
                        .andExpect(jsonPath("$.name").value(givenProduct.getName()))
                        .andDo(print());
            }

            @Test
            @DisplayName("PATCH요청 / 200(Ok)과 Product를 응답합니다.")
            void it_patch_update_product_return_ok_and_product() throws Exception {
                mockMvc.perform(patch(PRODUCTS_URI_SLASH + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(productToContent(givenProduct)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(givenId))
                        .andExpect(jsonPath("$.name").value(givenProduct.getName()))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("등록된 Product의 Id만 주어진다면")
        class Context_with_id {
            Long givenId = 1L;

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_return_badRequest() throws Exception {
                mockMvc.perform(put(PRODUCTS_URI_SLASH + givenId)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("수정할 Product만 주어진다면")
        class Context_with_product {
            Product givenProduct = getProductToBeUpdated();

            @Test
            @DisplayName("405(Method not allowed)를 응답합니다.")
            void it_return_badRequest() throws Exception {
                mockMvc.perform(put(PRODUCTS_URI_SLASH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(productToContent(givenProduct)))
                        .andExpect(status().isMethodNotAllowed())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("delete() 메소드는")
    class Describe_delete {
        @Nested
        @DisplayName("등록된 Product가 주어진다면")
        class Context_with_product {
            Long givenId = 1L;

            @Test
            @DisplayName("204(No Content)과 빈값을 응답합니다.")
            void it_delete_task_return_noContent() throws Exception {
                mockMvc.perform(delete(PRODUCTS_URI_SLASH + givenId))
                        .andExpect(status().isNoContent())
                        .andDo(print());
            }
        }
    }

    private Product getTestProduct() {
        return Product.builder()
                .name("테스트 제품")
                .maker("테스트 메이커")
                .price(1000)
                .image("http://test.com/test.jpg")
                .build();
    }

    private Product getProductToBeUpdated() {
        return Product.builder()
                .name("업데이트 제품")
                .maker("업데이트 메이커")
                .price(2000)
                .image("http://update.com/update.jpg")
                .build();
    }

    private String productToContent(Product product) throws JsonProcessingException {
        return objectMapper.writeValueAsString(product);
    }
}
