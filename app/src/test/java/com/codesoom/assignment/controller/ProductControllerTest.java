package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.service.ProductService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
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
class ProductControllerTest {

    private final long ID = 1L;
    private final long NOT_EXIST_ID = 100L;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;

    private List<Product> productList;
    private Product product;
    private Product product2;

    @BeforeEach
    void setUp() {
        product = new Product(ID, "장난감 뱀", "장난감 컴퍼니", 10000, "뱀.jpg");
        product2 = new Product(2L, "장난감 곰", "장난감 컴퍼니", 20000, "곰.jpg");
    }


    @Nested
    @DisplayName("Get /products 요청")
    class Describe_getProducts {
        @Nested
        @DisplayName("서비스에 장난감이 있으면")
        class Context_exist_product {
            @BeforeEach
            void setUp() {
                productList = new ArrayList<>();
                productList.add(product);
                productList.add(product2);
                given(productService.getProducts()).willReturn(productList);
            }

            @Test
            @DisplayName("장난감 list를 응답한다")
            void it_return_empty_list() throws Exception {
                mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(productList.size())));
            }
        }

        @Nested
        @DisplayName("저장된 장난감이 없으면")
        class Context_does_not_exist_product {
            @Test
            @DisplayName("빈 list를 응답한다")
            void it_return_empty_list() throws Exception {
                mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").doesNotExist());
            }
        }
    }

    @Nested
    @DisplayName("Get /products/{id} 요청")
    class Describe_getProduct {
        @Nested
        @DisplayName("id가 존재하는 장난감이면")
        class Context_exist_id {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(ID)).willReturn(product);
            }

            @Test
            @DisplayName("해당하는 장난감을 응답한다")
            void it_return_empty_list() throws Exception {
                mockMvc.perform(get("/products/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("name").value("장난감 뱀"));
            }
        }

        @Nested
        @DisplayName("주어진 id로 찾을수 없는 장난감이면")
        class Context_does_not_exist_id {
            @BeforeEach
            void setUp() {
                given(productService.getProduct(NOT_EXIST_ID)).willThrow(new ProductNotFoundException(NOT_EXIST_ID));
            }

            @Test
            @DisplayName("응답코드는 404이며 id가 존재하지 않는다는 메세지를 응답한다.")
            void it_return_not_found() throws Exception {
                mockMvc.perform(get("/products/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(content().string("There is no product number " + NOT_EXIST_ID));
            }
        }
    }

    @Nested
    @DisplayName("Post /products 요청")
    class Describe_addProduct {
        @Nested
        @DisplayName("장난감이 있으면")
        class Context_exist_product {
            @BeforeEach
            void setUp() {
                given(productService.addProduct(any(ProductDto.class))).willReturn(product);
            }

            @Test
            @DisplayName("응답코드 201이며 저장된 장난감을 응답한다")
            void it_return_product() throws Exception {
                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(product)))
                        .andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("name").exists())
                        .andExpect(jsonPath("maker").exists());
            }
        }

        @Nested
        @DisplayName("장난감이 없으면")
        class Context_does_not_exist_product {
            @Test
            @DisplayName("bad request를 응답한다")
            void it_return_bad_request() throws Exception {
                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("Patch /products/{id} 요청")
    class Describe_patchProduct {
        Product change;

        @Nested
        @DisplayName("id와 ProductDto가 있으면")
        class Context_exist_id_and_productDto {
            @BeforeEach
            void setUp() {
                change = new Product(ID, "바뀐 장난감", "다른 회사", 500, "바뀐이미지.jpg");
                given(productService.updateProduct(eq(ID), any(ProductDto.class))).willReturn(change);
            }

            @Test
            @DisplayName("응답코드는 200이며 변경된 장난감을 응답한다.")
            void it_return_changed_product() throws Exception {
                mockMvc.perform(patch("/products/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(change))
                )
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("name").value("바뀐 장난감"));
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않으면")
        class Context_does_not_exist_id {
            @BeforeEach
            void setUp() {
                change = new Product(ID, "바뀐 장난감", "다른 회사", 500, "바뀐이미지.jpg");
                given(productService.updateProduct(eq(NOT_EXIST_ID), any(ProductDto.class))).willThrow(new ProductNotFoundException(NOT_EXIST_ID));
            }

            @Test
            @DisplayName("응답코드는 404이며 에러메세지를 응답한다.")
            void it_return_not_foud() throws Exception {
                mockMvc.perform(patch("/products/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(change)))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(content().string("There is no product number " + NOT_EXIST_ID));
            }
        }
    }

    @Nested
    @DisplayName("Delete /products/{id} 요청")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("id가 존재하는 장난감이면")
        class Context_exist_id {
            @Test
            @DisplayName("응답코드 204를 응답한다.")
            void it_return_no_content() throws Exception {
                mockMvc.perform(delete("/products/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("id가 존재하지 않는 장난감이면")
        class Context_does_not_exist_id {
            @BeforeEach
            void setUp() {
                willThrow(new ProductNotFoundException(NOT_EXIST_ID)).given(productService).deleteProduct(NOT_EXIST_ID);
            }

            @Test
            @DisplayName("응답코드는 404이며 id가 존재하지 않는다는 메세지를 응답한다.")
            void it_return_not_found() throws Exception {
                mockMvc.perform(delete("/products/{id}", NOT_EXIST_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(content().string("There is no product number " + NOT_EXIST_ID));
            }
        }
    }

    @Test
    void productNotFoundExceptionHandler() {
        //TODO
    }
}
