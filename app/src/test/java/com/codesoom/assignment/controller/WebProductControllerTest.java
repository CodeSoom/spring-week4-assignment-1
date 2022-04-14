package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductSaveDto;
import com.codesoom.assignment.dto.ProductUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.LongStream;

import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_IMAGE_PATH;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_MAKER;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_NAME;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_PRICE;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_UPDATE_IMAGE_PATH;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_UPDATE_MAKER;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_UPDATE_NAME;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_UPDATE_PRICE;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("상품에 대한 HTTP 요청")
public class WebProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("GET - /products 요청시")
    class Describe_list {

        @Nested
        @DisplayName("상품 수 만큼")
        class Context_givenCount {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {
                LongStream.rangeClosed(1, givenCount)
                        .mapToObj(index -> new Product())
                        .forEach(product -> productRepository.save(product));
            }

            @Test
            @DisplayName("상품 목록을 응답한다. [200]")
            void it_response_products_and_http_status_200() throws Exception {

                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(givenCount)));
            }
        }
    }

    @Nested
    @DisplayName("GET - /products/{productId} 요청시")
    class Describe_detail {

        @Nested
        @DisplayName("{productId} 와 일치하는 상품이 있다면")
        class Context_existsProduct {

            Long productId;

            final Product product = new Product(
                    TEST_PRODUCT_NAME, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH
            );

            @BeforeEach
            void setUp() {
                productRepository.save(product);
                productId = product.getId();
            }

            @Test
            @DisplayName("상품 정보를 응답한다. [200]")
            void it_response_product() throws Exception {
                mockMvc.perform(get("/products/{productId}", productId)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("maker").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imageUrl").exists());
            }
        }

        @Nested
        @DisplayName("{productId} 와 일치하는 상품이 없다면")
        class Context_notExistsProduct {

            final Long notExistsProductId = 999L;

            @Test
            @DisplayName("NotFound 를 응답한다. [404]")
            void it_response_400() throws Exception {
                mockMvc.perform(get("/products/{productId}", notExistsProductId)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("POST - /products 요청시")
    class Describe_save {

        @Nested
        @DisplayName("상품 등록에 필요한 데이터가 주어진다면")
        class Context_valid {

            final ProductSaveDto source = new ProductSaveDto(
                    TEST_PRODUCT_NAME, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

            @Test
            @DisplayName("상품을 등록하고 응답한다. [201]")
            void it_save_and_return_product() throws Exception {

                mockMvc.perform(post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(source)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").value(TEST_PRODUCT_NAME))
                        .andExpect(jsonPath("maker").value(TEST_PRODUCT_MAKER))
                        .andExpect(jsonPath("price").value(TEST_PRODUCT_PRICE))
                        .andExpect(jsonPath("imageUrl").value(TEST_PRODUCT_IMAGE_PATH));
            }
        }
    }

    @Nested
    @DisplayName("PUT - /products/{productId} 요청시")
    class Describe_replace {

        @Nested
        @DisplayName("대체될 상품이 있다면")
        class Context_existsProduct {

            final Product product = new Product(
                    TEST_PRODUCT_NAME, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

            final ProductUpdateDto updateDto = new ProductUpdateDto(
                    TEST_PRODUCT_UPDATE_NAME,
                    TEST_PRODUCT_UPDATE_MAKER,
                    TEST_PRODUCT_UPDATE_PRICE,
                    TEST_PRODUCT_UPDATE_IMAGE_PATH);

            Long productId;

            @BeforeEach
            void setUp() {
                productRepository.save(product);
                productId = product.getId();
            }

            @Test
            @DisplayName("상품을 대체하고 응답한다. [200]")
            void it_replace_and_return_product() throws Exception {

                mockMvc.perform(put("/products/{productId}", productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto))
                        )
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").value(productId))
                        .andExpect(jsonPath("name").value(TEST_PRODUCT_UPDATE_NAME))
                        .andExpect(jsonPath("maker").value(TEST_PRODUCT_UPDATE_MAKER))
                        .andExpect(jsonPath("price").value(TEST_PRODUCT_UPDATE_PRICE))
                        .andExpect(jsonPath("imageUrl").value(TEST_PRODUCT_UPDATE_IMAGE_PATH));
            }
        }
    }

    @Nested
    @DisplayName("DELETE - /products/{productId}")
    class Describe_delete {

        @Nested
        @DisplayName("{productId} 와 일치하는 상품이 있다면")
        class Context_existsProduct {

            Long productId;

            @BeforeEach
            void setUp() {
                final Product product = new Product();
                productRepository.save(product);
                productId = product.getId();
            }

            @Test
            @DisplayName("상품을 삭제하고 No Content 를 응답한다. [204]")
            void it_response_204() throws Exception {
                mockMvc.perform(delete("/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("{productId} 와 일치하는 상품이 없다면")
        class Context_notExistsProduct {

            final Long notExistsProductId = 999L;

            @Test
            @DisplayName("Not Found 를 응답한다. [404]")
            void it_response_404() throws Exception {
                mockMvc.perform(delete("/products/{productId}", notExistsProductId)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound());
            }
        }
    }
}
