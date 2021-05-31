package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@DisplayName("ProductController")
public class ProductControllerTest {
    private ProductController productController;
    private ProductService productService;
    private Product product;
    private List<Product> products;
    private ObjectMapper objectMapper;
    private String productNotFoundMessage;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
        products = new ArrayList<>();
        objectMapper = new ObjectMapper();
        productNotFoundMessage = "Product not foud : ";
    }

    Product makingProduct(Long id) {
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
        @DisplayName("고양이 장난감 목록이 있다면")
        class Context_not_empty_list {
            private int productSize = 5;

            @BeforeEach
            void setUpNotEmptyList()  {
                //given
                for (int i = 0; i < productSize; i++) {
                    products.add(makingProduct((long) i));
                }
                given(productService.getProducts()).willReturn(products);
            }

            @AfterEach
            void setUpLastNotEmptyList() {
                then(productService)
                        .should(times(1))
                        .getProducts();
            }

            @Test
            @DisplayName("고양이 장난감 목록을 반환한다.")
            void not_empty_list_return() {
                //when then
                assertThat(productController.list())
                        .isNotEmpty();
            }

            @Test
            @DisplayName("등록된 고양이 장난감 목록 수 만큼 반환한다.")
            void not_empty_list_size() {
                //when then
                assertThat(productController.list())
                        .hasSize(productSize);
            }
        }

        @Nested
        @DisplayName("고양이 장난감 목록이 없다면")
        class Context_empty_list {
            private String productJSON;

            @BeforeEach
            void setUpEmptyList() throws JsonProcessingException {
                //given
                productJSON = objectMapper.writeValueAsString(products);
                given(productService.getProducts()).willReturn(products);
            }

            @AfterEach
            void setUpLastEmptyList() {
                then(productService)
                        .should(times(1))
                        .getProducts();
            }

            @Test
            @DisplayName("비어있는 목록을 반환한다.")
            void empty_list() {
                //when then
                assertThat(productController.list())
                        .isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("detail() 메소드는")
    class Describe_detail {
        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 있다면")
        class Context_existed_id_detail {
            private Long productId = 1L;
            private String jsonProduct;

            @BeforeEach
            void setUpExistedIdDetail() throws JsonProcessingException {
                //given
                product = makingProduct(productId);
                jsonProduct = objectMapper.writeValueAsString(product);
                given(productService.getProduct(eq(productId)))
                        .willReturn(product);
            }

            @AfterEach
            void setUpLastExistedIdDetail() {
                then(productService)
                        .should(times(1))
                        .getProduct(eq(productId));
            }

            @Test
            @DisplayName("반환값은 Null이 아니다.")
            void existed_id_detail_not_null() {
                assertThat(productController.detail(productId))
                        .isNotNull();
            }

            @Test
            @DisplayName("고양이 장난감을 반환한다.")
            void existed_id_detail_product() {
                //when
                Product returnProduct = productController.detail(productId);
                //then
                assertAll(
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("name").isEqualTo(product.getName());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("id").isEqualTo(productId);
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("maker").isEqualTo(product.getMaker());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("price").isEqualTo(product.getPrice());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("imageUrl").isEqualTo(product.getImageUrl());
                        }
                );
            }
        }

        @Nested
        @DisplayName("요청한 고양이 장난감이 없다면")
        class Context_not_existed_detail {
            private Long productId = 100L;

            @BeforeEach
            void setUpNotExistedDetail() {
                //given
                productNotFoundMessage += productId;
                given(productService.getProduct(eq(productId)))
                        .willThrow(new ProductNotFoundException(productId));
            }

            @AfterEach
            void setUpLastNotExistedDetail() {
                then(productService)
                        .should(times(1))
                        .getProduct(productId);
            }

            @Test
            @DisplayName("ProductNotFoundException이 발생한다.")
            void not_existed_detail_exception() {
                //when then
                Throwable thrown = catchThrowable(() -> {
                    productService.getProduct(productId);
                });
                assertTrue(thrown
                        .getClass()
                        .isAssignableFrom(ProductNotFoundException.class));
            }

            @Test
            @DisplayName("특정 에러메시지를 리턴한다.")
            void not_existed_detail_exception_message() {
                Throwable thrown = catchThrowable(() -> {
                    productController.detail(productId);
                });
                assertThat(thrown.getMessage())
                        .isEqualTo(productNotFoundMessage);
            }
        }
    }

    @Nested
    @DisplayName("create() 메소드는")
    class Describe_create {
        @Nested
        @DisplayName("요청한 고양이 장난감을 생성하면")
        class Context_valid_create {
            private Long productId = 1L;

            @BeforeEach
            void setUpValidCreate() {
                //given
                product = makingProduct(productId);
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
            @DisplayName("생성한 동일 고양이 장난감을 반환한다.")
            void valid_create_return() {
                Product returnProduct = productController.createProduct(product);
                assertAll(
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("name")
                                    .isEqualTo(product.getName());

                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("maker")
                                    .isEqualTo(product.getMaker());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("price")
                                    .isEqualTo(product.getPrice());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("imageUrl")
                                    .isEqualTo(product.getImageUrl());
                        }
                );
            }
        }
    }

    @Nested
    @DisplayName("update() 메서드")
    class Describe_update {
        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 있다면")
        class Context_existed_id_update {
            private Long productId = 1L;

            @BeforeEach
            void setUpExistedIdUpdate() {
                //given
                product = makingProduct(productId);
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
            @DisplayName("수정한 고양이 장난감을 반환한다.")
            void existed_id_update_return() {
                Product returnProduct = productController.updateProduct(productId, product);
                assertAll(
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("id")
                                    .isEqualTo(productId);
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("name")
                                    .isEqualTo(product.getName());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("maker")
                                    .isEqualTo(product.getMaker());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("price")
                                    .isEqualTo(product.getPrice());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("imageUrl")
                                    .isEqualTo(product.getImageUrl());
                        }
                );
            }
        }

        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 없다면")
        class Context_not_existed_id_update {
            private Long productId = 100L;

            @BeforeEach
            void setUpNotExistedIdUpdate() {
                //given
                product = makingProduct(productId);
                productNotFoundMessage += productId;
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
            void not_existed_id_update_exception() {
                Throwable throwable = catchThrowable(() ->
                {
                    productController.updateProduct(productId, product);
                });
                assertTrue(throwable
                        .getClass()
                        .isAssignableFrom(ProductNotFoundException.class));
            }

            @Test
            @DisplayName("특정한 에러 메시지를 반환한다.")
            void not_existed_id_update_exception_messgae() {
                Throwable throwable = catchThrowable(() -> {
                    productController.updateProduct(productId, product);
                });
                assertThat(throwable.getMessage())
                        .isEqualTo(productNotFoundMessage);
            }
        }
    }

    @Nested
    @DisplayName("delete() 메소드는")
    class Describe_delete {
        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 있다면")
        class Context_existed_id_delete {
            private Long productId = 1L;

            @BeforeEach
            void setUpExistedIdDelete() {
                //given
                product = makingProduct(productId);
                given(productService.deleteProduct(eq(productId)))
                        .willReturn(product);
            }

            @AfterEach
            void setUpLastExistedIdDelete() {
                then(productService)
                        .should(times(1))
                        .deleteProduct(eq(productId));
            }

            @Test
            @DisplayName("고양이 장난감을 삭제한다.")
            void existed_id_delete_product() {
                productController.deleteProduct(productId);
            }
        }

        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 없다면")
        class Context_not_existed_id_delete {
            private Long productId = 100L;

            @BeforeEach
            void setUpExistedIdDelete() {
                //given
                product = makingProduct(productId);
                productNotFoundMessage += productId;
                given(productService.deleteProduct(eq(productId)))
                        .willThrow(new ProductNotFoundException(productId));
            }
            @AfterEach
            void setUpLastExistedIdDelete(){
                then(productService)
                        .should(times(1))
                        .deleteProduct(productId);
            }

            @Test
            @DisplayName("ProductNotFoundException이 발생한다.")
            void not_existed_id_delete_exception() {
                Throwable throwable = catchThrowable(() -> {
                    productController.deleteProduct(productId);
                });
                assertTrue(throwable
                        .getClass()
                        .isAssignableFrom(ProductNotFoundException.class));
            }

            @Test
            @DisplayName("특정 에러 메시지를 리턴한다.")
            void not_existed_id_delete_exception_message() {
                Throwable throwable = catchThrowable(() -> {
                    productController.deleteProduct(productId);
                });
                assertThat(throwable.getMessage())
                        .isEqualTo(productNotFoundMessage);
            }
        }
    }
}
