package com.codesoom.assignment.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.codesoom.assignment.domain.ProductConstant.NAME;
import static com.codesoom.assignment.domain.ProductConstant.MAKER;
import static com.codesoom.assignment.domain.ProductConstant.IMAGE_URL;
import static com.codesoom.assignment.domain.ProductConstant.PRICE;
import static com.codesoom.assignment.domain.ProductConstant.ID;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@DisplayName("장난감 리소스")
public final class ProductControllerWebTest {
    @MockBean
    private ProductService productServiceMock;

    @Autowired
    private MockMvc mockMvc;

    private final ProductDto productDto = new ProductDto(NAME, MAKER, IMAGE_URL, PRICE);
    private final Product product = new Product(productDto);

    @Nested
    @DisplayName("전체 목록 조회 엔드포인트는")
    class Describe_products_get {
        @AfterEach
        void tearDown() {
            verify(productServiceMock, atLeastOnce()).listProduct();
        }

        @Nested
        @DisplayName("저장된 Product가 없다면")
        class Context_product_empty {
            @BeforeEach
            void setUp() {
                when(productServiceMock.listProduct())
                    .thenReturn(Lists.newArrayList());
            }

            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_returns_a_empty_list() throws Exception {
                mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("[]"));
            }
        }

        @Nested
        @DisplayName("저장된 Product가 있다면")
        class Context_product_exist {
            @BeforeEach
            void setUp() {
                when(productServiceMock.listProduct())
                    .thenReturn(Lists.newArrayList(product));
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            void it_returns_a_product_list() throws Exception {
                mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(startsWith("[")))
                    .andExpect(content().string(endsWith("]")))
                    .andExpect(content().string(containsString("{")))
                    .andExpect(content().string(containsString("}")));
            }
        }
    }

    @Nested
    @DisplayName("생성 엔드포인트는")
    class Describe_products_post {
        @BeforeEach
        void setUp() {
            when(productServiceMock.createProduct(any(Product.class)))
                .thenReturn(product);
        }

        @AfterEach
        void tearDown() {
            verify(productServiceMock)
                .createProduct(any(Product.class));
        }

        class Context_request_create_product {
            @Test
            @DisplayName("장난감을 생성하고 리턴한다.")
            void it_returns_a_product() throws Exception {
                mockMvc.perform(
                        post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            "{"
                                + "\"name\":\"name\","
                                + "\"maker\":\"maker\","
                                + "\"imageUrl\":\"imageUrl\","
                                + "\"price\":1000" +
                            "}"
                        )
                    )
                    .andExpect(status().isCreated())
                    .andExpect(content().string(containsString(NAME)))
                    .andExpect(content().string(containsString(MAKER)))
                    .andExpect(content().string(containsString(IMAGE_URL)))
                    .andExpect(content().string(containsString(PRICE.toString())));
            }
        }
    }

    @Nested
    @DisplayName("검색 엔드포인트는")
    class Describe_products_id_get {
        @Nested
        @DisplayName("장난감 데이터 요청 시")
        class Contest_request_product {
            @AfterEach
            void tearDown() {
                verify(productServiceMock, atLeastOnce())
                    .detailProduct(anyLong());
            }

            @Nested
            @DisplayName("장난감을 찾을 수 있다면")
            class Context_find_success {
                @BeforeEach
                void setUp() {
                    doReturn(product)
                        .when(productServiceMock).detailProduct(anyLong());
                }

                @Test
                @DisplayName("찾은 장난감을 리턴한다.")
                void it_returns_a_find_product() throws Exception {
                    mockMvc.perform(get("/products/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(NAME)))
                        .andExpect(content().string(containsString(MAKER)))
                        .andExpect(content().string(containsString(IMAGE_URL)))
                        .andExpect(content().string(containsString(PRICE.toString())));
                }
            }

            @Nested
            @DisplayName("장난감을 찾을 수 없다면")
            class Context_find_fail {
                @BeforeEach
                void setUp() {
                    doThrow(new ProductNotFoundException(ID))
                        .when(productServiceMock).detailProduct(anyLong());
                }

                @Test
                @DisplayName("404(NOT FOUND) http status code를 리턴한다.")
                void it_notify_a_find_fail() throws Exception {
                    mockMvc.perform(get("/products/1"))
                        .andExpect(status().isNotFound());
                }
            }
        }
    }

    @Nested
    @DisplayName("수정 엔드포인트는")
    class Describe_product_id_put_patch {
        @Nested
        @DisplayName("장난감 업데이트 요청 시")
        class Context_request_update_product {
            @AfterEach
            void tearDown() {
                verify(productServiceMock, atLeastOnce())
                    .updateProduct(anyLong(), any(Product.class));
            }

            @Nested
            @DisplayName("장난감을 찾을 수 있다면")
            class Context_find_success {
                @BeforeEach
                void setUp() {
                    final Long UPDATED_PRICE = PRICE.longValue() + PRICE.longValue();
                    final ProductDto updateProductDto = new ProductDto(
                        "updated" + NAME, "updated" + MAKER,
                        "updated" + IMAGE_URL, UPDATED_PRICE
                    );
                    when(productServiceMock.updateProduct(anyLong(), any(Product.class)))
                        .thenReturn(new Product(updateProductDto));
                }

                @Test
                @DisplayName("업데이트한 장난감을 리턴한다.")
                void it_returns_a_updated_product() throws Exception {
                    mockMvc.perform(
                        put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            "{"
                                + "\"name\":\"updated name\","
                                + "\"maker\":\"updated maker\","
                                + "\"imageUrl\":\"updated imageUrl\","
                                + "\"price\":2000" +
                            "}"
                        )
                    ).andExpect(status().isOk())
                    .andExpect(content().string(containsString("updated")));

                    mockMvc.perform(
                        patch("/products/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            "{"
                                + "\"name\":\"updated name\","
                                + "\"maker\":\"updated maker\","
                                + "\"imageUrl\":\"updated imageUrl\","
                                + "\"price\":2000" +
                            "}"
                        )
                    ).andExpect(status().isOk())
                    .andExpect(content().string(containsString("updated")));
                }
            }

            @Nested
            @DisplayName("장난감을 찾을 수 없다면")
            class Context_find_fail {
                @BeforeEach
                void setUp() {
                    when(productServiceMock.updateProduct(anyLong(), any(Product.class)))
                        .thenThrow(new ProductNotFoundException(ID));
                }

                @Test
                @DisplayName("404(NOT FOUND) http status code를 리턴한다.")
                void it_notify_a_find_fail() throws Exception {
                    mockMvc.perform(
                        put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            "{"
                                + "\"name\":\"updated name\","
                                + "\"maker\":\"updated maker\","
                                + "\"imageUrl\":\"updated imageUrl\","
                                + "\"price\":2000" +
                            "}"
                        )
                    ).andExpect(status().isNotFound());

                    mockMvc.perform(
                        patch("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            "{"
                                + "\"name\":\"updated name\","
                                + "\"maker\":\"updated maker\","
                                + "\"imageUrl\":\"updated imageUrl\","
                                + "\"price\":2000" +
                            "}"
                        )
                    ).andExpect(status().isNotFound());
                }
            }
        }
    }

    @Nested
    @DisplayName("삭제 엔드포인트는")
    class Describe_product_id_delete {
        @Nested
        @DisplayName("장난감 삭제 요청 시")
        class Context_request_delete_product {
            @AfterEach
            void tearDown() {
                verify(productServiceMock, atLeastOnce())
                    .deleteProduct(anyLong());
            }

            @Nested
            @DisplayName("장난감을 찾을 수 있다면")
            class Context_find_success {
                @BeforeEach
                void setUp() {
                    doNothing()
                        .when(productServiceMock).deleteProduct(anyLong());
                }

                @Test
                @DisplayName("Product를 삭제한다.")
                void it_deletes_a_product() throws Exception {
                    mockMvc.perform(delete("/products/1"))
                        .andExpect(status().isNoContent());
                }
            }

            @Nested
            @DisplayName("장난감을 찾을 수 없다면")
            class Context_find_fail {
                @BeforeEach
                void setUp() {
                    doThrow(new ProductNotFoundException(ID))
                        .when(productServiceMock).deleteProduct(anyLong());
                }

                @Test
                @DisplayName("404(NOT FOUND) http status code를 리턴한다.")
                void it_notify_a_find_fail() throws Exception {
                    mockMvc.perform(delete("/products/1"))
                        .andExpect(status().isNotFound());
                }
            }
        }
    }
}
