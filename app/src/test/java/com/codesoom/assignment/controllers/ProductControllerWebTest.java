package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ProductController")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductControllerWebTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    private Product product;
    List<Product> products;
    private String reuqestUrl;
    private ObjectMapper objectMapper;
    private String productNotFoundMessage;

    @BeforeEach
    public void setUp() {
        reuqestUrl = "/products";
        products = new ArrayList<>();
        objectMapper = new ObjectMapper();
        productNotFoundMessage = "Product not foud : ";
        reset(productService);
    }

    public Product makingProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Name" + id);
        product.setMaker("Maker " + id);
        product.setPrice(id * 1000L);
        product.setImageUrl("http://localhost:8080/fish" + id);
        return product;
    }

    @Nested
    @DisplayName("list() 메소드는")
    class Describe_list {
        @Nested
        @DisplayName("고양이 장난감 목록이 존재한다면")
        @TestMethodOrder(MethodOrderer.OrderAnnotation.class)

        class Context_existed_product_list {
            private Long productId = 1L;
            private String productJSON;

            @BeforeEach
            void setUpExsitedList() throws JsonProcessingException {
                //given
                product = makingProduct(productId);
                products.add(product);
                productJSON = objectMapper.writeValueAsString(products);
                given(productService.getProducts()).willReturn(products);
            }

            @AfterEach
            void setUpLastExsitedList() {
                then(productService)
                        .should(times(1))
                        .getProducts();
            }

            @Test
            @Order(1)
            @DisplayName("목록에 기존에 생성된  메이커 내용을 포함한다.")
            void not_empty_product_list_contain() throws Exception {
                //when then
                mockMvc.perform(get(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString(product.getMaker())))
                        .andDo(print());
            }

            @Test
            @Order(2)
            @DisplayName("목록에 기존에 생성된  JSON을 반환한다.")
            void not_empty_product_list_json() throws Exception {
                //when then
                mockMvc.perform(get(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString(productJSON)))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("고양이 장난감 목록이 비어 있지 않다면")
        class Context_empty_list {
            private String productsJSON;

            @BeforeEach
            void setUpEmptyList() throws JsonProcessingException {
                //given
                productsJSON = objectMapper.writeValueAsString(products);
                given(productService.getProducts()).willReturn(products);
            }

            @AfterEach
            void setUpLastEmptyList() {
                then(productService)
                        .should(times(1))
                        .getProducts();
            }

            @Test
            @DisplayName("비어있는 JSON을 반환한다.")
            void empty_product_list() throws Exception {
                //when then
                mockMvc.perform(get(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString(productsJSON)))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("detail() 메소드는")
    class Describe_detail {
        @Nested
        @DisplayName("요청한 고양이 장난감이  목록에 있다면")
        class Context_existed_id_detail {
            private Long productId = 1L;
            private String productJSON;

            @BeforeEach
            void setUpExistedId() throws JsonProcessingException {
                //given
                product = makingProduct(productId);
                reuqestUrl += ("/" + productId);
                productJSON = objectMapper.writeValueAsString(product);
                given(productService.getProduct(productId))
                        .willReturn(product);
            }

            @Test
            @DisplayName("요청한 고양이 장난감을 반환한다.")
            void existed_id_detail_product_return() throws Exception {
                //when then
                mockMvc.perform(get(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString(productJSON)))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 없다면")
        class Context_not_existed_id_detail {
            private Long productId = 100L;

            @BeforeEach
            void setUpNotExistedDetail() {
                //given
                reuqestUrl += ("/" + productId);
                productNotFoundMessage += productId;
                given(productService.getProduct(productId))
                        .willThrow(new ProductNotFoundException(productId));
            }

            @AfterEach
            void setUpLastExistedDetail() {
                then(productService)
                        .should(times(1))
                        .getProduct(productId);
            }

            @Test
            @DisplayName("ProductNotFoundException이 발생한다.")
            void not_existed_id_detail_exeption() throws Exception {
                //when then
                mockMvc.perform(get(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(result -> {
                            assertTrue(result
                                    .getResolvedException()
                                    .getClass()
                                    .isAssignableFrom(ProductNotFoundException.class));
                        });
            }

            @Test
            @DisplayName("특정 에러 메시지를 반환한다.")
            void not_existed_id_detail_exeption_message() throws Exception {
                //when then
                mockMvc.perform(get(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(result -> {
                            assertThat(result
                                    .getResolvedException()
                                    .getMessage())
                                    .isEqualTo(productNotFoundMessage);
                        });
            }
        }
    }

    @Nested
    @DisplayName("createProduct() 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("요청한 고양이 장난감이 등록된다면")
        class Context_valid_create {
            private Long productId = 1L;
            private String productJSON;

            @BeforeEach
            void setUpValidCreate() throws JsonProcessingException {
                //given
                product = makingProduct(productId);
                productJSON = objectMapper.writeValueAsString(product);
                given(productService.saveProduct(any(Product.class)))
                        .willReturn(product);
            }

            @AfterEach
            void setUpLastValidCreate() {
                then(productService)
                        .should(times(1))
                        .saveProduct(any(Product.class));
            }

            @Test
            @DisplayName("Response Status가 201이다.")
            void valid_create_status() throws Exception {
                //when then
                mockMvc.perform(post(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJSON))
                        .andExpect(status().isCreated())
                        .andDo(print());
            }

            @Test
            @DisplayName("생성한 고양이 장난감을 반환한다.")
            void valid_create_return() throws Exception {
                //when then
                mockMvc.perform(post(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJSON))
                        .andExpect(content().string(containsString(productJSON)))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("update() 메서드는")
    class Describe_update {
        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 있다면")
        class Context_existed_id_update {
            private Long productId = 1L;
            private String productJSON;

            @BeforeEach
            void setUpExistedIdUpdate() throws JsonProcessingException {
                //given
                product = makingProduct(productId);
                reuqestUrl += ("/" + productId);
                productJSON = objectMapper.writeValueAsString(product);
                given(productService.updateProduct(eq(productId), any(Product.class)))
                        .willReturn(product);
            }

            @AfterEach
            void setUpLastExistedIdUpdate() {
                then(productService)
                        .should(times(1))
                        .updateProduct(eq(productId), any(Product.class));
            }

            @Test
            @DisplayName("목록의 고양이 장난감을 수정한다.")
            void existed_id_update_return() throws Exception {
                //when then
                mockMvc.perform(put(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJSON))
                        .andExpect(content().string(containsString(String.valueOf(productId))))
                        .andDo(print());
            }

            @Test
            @DisplayName("수정 요청한 고양이 장난감을 반환한다.")
            void existed_id_update_json() throws Exception {
                //when then
                mockMvc.perform(put(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJSON))
                        .andExpect(content().string(containsString(productJSON)))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 없다면")
        class Context_not_existed_id_update {
            private Long productId = 100L;
            private String productJSON;

            @BeforeEach
            void setUpNotExistedIdUpdate() throws JsonProcessingException {
                //given
                product = makingProduct(productId);
                reuqestUrl += ("/" + productId);
                productNotFoundMessage += productId;
                productJSON = objectMapper.writeValueAsString(product);
                given(productService.updateProduct(eq(productId), any(Product.class)))
                        .willThrow(new ProductNotFoundException(productId));
            }

            @AfterEach
            void setUpLastNotExistedIdUpdate() {
                then(productService)
                        .should(times(1))
                        .updateProduct(eq(productId), any(Product.class));
            }

            @Test
            @DisplayName("ProductNotFoundException을 발생한다.")
            void not_existed_id_update_exception() throws Exception {
                //when then
                mockMvc.perform(put(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJSON))
                        .andExpect(result -> {
                            assertTrue(result
                                    .getResolvedException()
                                    .getClass()
                                    .isAssignableFrom(ProductNotFoundException.class));
                        })
                        .andDo(print());
            }

            @Test
            @DisplayName("특정 에러 메시지를 반환한다.")
            void not_existed_id_update_exception_message() throws Exception {
                //when then
                mockMvc.perform(put(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJSON))
                        .andExpect(result -> {
                            assertThat(result
                                    .getResolvedException()
                                    .getMessage())
                                    .isEqualTo(productNotFoundMessage);
                        });
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct() 메소드는")
    class Describe_delete {
        @Nested
        @DisplayName("요청한 고양이 장난감이 있다면")
        class Context_existed_id_delete {
            private Long productId = 1L;

            @BeforeEach
            void setUpExistedIdDelete() {
                //given
                product = makingProduct(productId);
                reuqestUrl += ("/" + productId);
                given(productService.deleteProduct(eq(productId)))
                        .willReturn(null);
            }

            @AfterEach
            void setUpLastExistedIdDelete() {
                then(productService)
                        .should(times(1))
                        .deleteProduct(eq(productId));
            }

            @Test
            @DisplayName("ReponseStatus 가 204이다.")
            void existed_id_delete_status() throws Exception {
                //when then
                mockMvc.perform(delete(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            }

            @Test
            @DisplayName("목록이 삭제 되었다면 결과는 비어있다.")
            void existed_id_delete_return() throws Exception {
                //when then
                mockMvc.perform(delete(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(""));
            }
        }

        @Nested
        @DisplayName("요청한 고양이 장난감이 없다면")
        class Context_not_existed_id_delete {
            private Long productId = 100L;

            @BeforeEach
            void setUpNotExistedIdDelete() {
                //given
                product = makingProduct(productId);
                reuqestUrl += ("/" + productId);
                productNotFoundMessage += productId;
                given(productService.deleteProduct(eq(productId)))
                        .willThrow(new ProductNotFoundException(productId));
            }

            @AfterEach
            void setUpLastNotExistedIdDelete() {
                then(productService)
                        .should(times(1))
                        .deleteProduct(eq(productId));
            }

            @Test
            @DisplayName("ProductNotFoundException을 발생한다.")
            void not_existed_id_delete_exeption() throws Exception {
                //when then
                mockMvc.perform(delete(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(result -> {
                            result
                                    .getResolvedException()
                                    .getClass()
                                    .isAssignableFrom(ProductNotFoundException.class);
                        });
            }

            @Test
            @DisplayName("특정 에러 메시지를 반환한다.")
            void not_existed_id_delete_exeption_message() throws Exception {
                //when then
                mockMvc.perform(delete(reuqestUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(result -> {
                            assertThat(result
                                    .getResolvedException()
                                    .getMessage())
                                    .isEqualTo(productNotFoundMessage);
                        });
            }
        }
    }
}
