package com.codesoom.assignment.controller;

import com.codesoom.assignment.Utf8MockMvc;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Utf8MockMvc
@DisplayName("ProductController Web 계층")
public class ProductControllerWebTest {
    @Autowired MockMvc mockMvc;
    @Autowired ProductController controller;
    @Autowired ProductService service;
    @Autowired ProductRepository repository;

    private final String productControllerPath = "/products";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ModelMapper modelMapper = new ModelMapper();

    @Nested
    @DisplayName("루트 (/) 경로는")
    class Describe_root_path {
        private final String rootPath = productControllerPath + "/";

        @Nested
        @DisplayName("GET 요청을 받는다면")
        class Context_with_get_request {
            MockHttpServletRequestBuilder requestBuilder;

            public Context_with_get_request() {
                requestBuilder = get(rootPath);
            }

            @Nested
            @DisplayName("Product 가 존재하지 않을 때")
            class Context_zero_product {
                @Test
                @DisplayName("200 OK, 빈 리스트를 리턴한다.")
                void it_returns_empty_list() throws Exception {
                    mockMvc.perform(requestBuilder)
                            .andExpect(status().isOk())
                            .andExpect(content().string("[]"));
                }
            }

            @Nested
            @DisplayName("Product 가 존재할 때")
            class Context_one_product {
                Product savedProduct;

                @BeforeEach
                void setUp() {
                    savedProduct = saveSampleProduct();
                }

                @Test
                @DisplayName("200 OK, Product 리스트를 리턴한다.")
                void it_returns_empty_list() throws Exception {
                    mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().string("[" + toJson(savedProduct) + "]"));
                }
            }

        }

        @Nested
        @DisplayName("POST 요청을 받는다면")
        class Context_with_post_request {
            private final MockHttpServletRequestBuilder requestBuilder;

            public Context_with_post_request() {
                requestBuilder = post(rootPath);
            }

            @Nested
            @DisplayName("name, maker, price, image 의 정보를 받았을 때")
            class Context_full_input {
                ResultActions resultActions;
                ProductDto productDto;
                Product product;

                public Context_full_input() throws Exception {
                    productDto = new ProductDto();

                    productDto.setName("고양이 용품1");
                    productDto.setPrice(2000);
                    productDto.setMaker("중국산");
                    productDto.setImage("대충 고양이용품 이미지");

                    resultActions = mockMvc.perform(
                            requestBuilder
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(toJson(productDto))
                    );

                    product = modelMapper.map(productDto, Product.class);
                    product.setId(1L);
                }

                @Test
                @DisplayName("201 CREATED, Product 를 추가한다.")
                void it_creates_product() throws Exception {
                    resultActions
                            .andExpect(status().isCreated())
                            .andExpect(content().json(toJson(product)));
                }

            }
        }

    }

    public String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    private Product saveSampleProduct() {
        Product product = new Product();

        product.setName("고양이 용품1");
        product.setPrice(2000);
        product.setMaker("중국산");
        product.setImage("대충 고양이용품 이미지");

        return repository.save(product);
    }
}
