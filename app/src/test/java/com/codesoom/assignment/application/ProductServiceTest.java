package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@DisplayName("ProductService")
class ProductServiceTest {
    private ProductService productService;
    private ProductRepository productRepository;
    private Product product;
    private List<Product> products;
    private ObjectMapper objectMapper;
    private String productNotFoundMessage;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
        products = new ArrayList<>();
        objectMapper = new ObjectMapper();
        productNotFoundMessage = "Product not foud : ";
    }

    Product saveProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("Name" + id);
        product.setMaker("Maker " + id);
        product.setPrice(id * 1000L);
        product.setImageUrl("http://localhost:8080/fish" + id);
        return product;
    }

    @Nested
    @DisplayName("getProducts() 메서드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("등록된 고양이 장난감 목록이 있으면")
        class Context_not_empty_getProducts {
            private Long productId = 1L;

            @BeforeEach
            void setUpNotEmptyGetProducts() {
                //given
                products.add(saveProduct(productId));
                given(productRepository.findAll())
                        .willReturn(products);
            }

            @Test
            @DisplayName("장난감 목록을 반환한다.")
            void not_empty_getProducts() {
                //when then
                assertThat(productService.getProducts())
                        .isNotEmpty();

                then(productRepository)
                        .should(times(1))
                        .findAll();
            }
        }

        @Nested
        @DisplayName("등록된 고양이 장난감 목록이 없다면")
        class Context_empty_getProducts {
            @BeforeEach
            void setUpEmptyGetProducts() {
                //given
                given(productRepository.findAll())
                        .willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("비어있는 목록을 반환한다.")
            void empty_getProducts() {
                //when then
                assertThat(productService.getProducts())
                        .isEmpty();

                then(productRepository)
                        .should(times(1))
                        .findAll();
            }
        }
    }

    @Nested
    @DisplayName("getProduct() 메소드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 있으면")
        class Context_existed_getProduct {
            private Long productId = 1L;

            @BeforeEach
            void setUpExistedGetProduct() {
                //given
                product = saveProduct(productId);
                given(productRepository.findById(eq(productId)))
                        .willReturn(Optional.of(product));
            }

            @Test
            @DisplayName("요청한 고양이 장난감을 반환한다.")
            void existed_getProduct_return() {
                product = productService.getProduct(productId);
                assertThat(product)
                        .extracting("id")
                        .isEqualTo(productId);
                then(productRepository)
                        .should(times(1))
                        .findById(eq(productId));
            }
        }

        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 없으면")
        class Context_not_existed_getProduct {
            private Long productId = 100L;

            @BeforeEach
            void setUpNotExistedGetProduct() {
                //given
                productNotFoundMessage += productId;
                given(productRepository
                        .findById(eq(productId)))
                        .willThrow(new ProductNotFoundException(productId));
            }

            @Test
            @DisplayName("ProductNotFoundException이 발생한다.")
            void not_existed_getProduct_exception() {
                Throwable throwable = catchThrowable(() -> {
                    productService.getProduct(productId);
                });
                assertTrue(throwable
                        .getClass()
                        .isAssignableFrom(ProductNotFoundException.class));
                then(productRepository)
                        .should(times(1))
                        .findById(productId);
            }

            @Test
            @DisplayName("특정 에러 메시지를 반환한다.")
            void not_existed_getProduct_exception_message() {
                Throwable throwable = catchThrowable(() -> {
                    productService.getProduct(productId);
                });
                assertThat(throwable.getMessage())
                        .isEqualTo(productNotFoundMessage);
                then(productRepository)
                        .should(times(1))
                        .findById(productId);
            }
        }
    }

    @Nested
    @DisplayName("saveProduct() 메서드는")
    class Describe_saveProduct {
        @Nested
        @DisplayName("성공적으로 고양이 장난감이 등록된다면")
        class Context_valid_saveProduct {
            private Long productId = 1L;

            @BeforeEach
            void setUpValidSaveProduct() {
                //given
                product = saveProduct(productId);
                given(productRepository.save(any(Product.class)))
                        .willReturn(product);
            }

            @Test
            @DisplayName("등록한 고양이 장난감을 반환한다.")
            void valid_saveProduct() {
                Product returnProduct = productService.saveProduct(product);
                assertAll(
                        () -> {
                            assertThat(returnProduct).extracting("name").isEqualTo(product.getName());
                        },
                        () -> {
                            assertThat(returnProduct).extracting("maker").isEqualTo(product.getMaker());
                        },
                        () -> {
                            assertThat(returnProduct).extracting("price").isEqualTo(product.getPrice());
                        },
                        () -> {
                            assertThat(returnProduct).extracting("imageUrl").isEqualTo(product.getImageUrl());
                        }
                );
                then(productRepository)
                        .should(times(1))
                        .save(any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("updateProduct() 메소드는")
    class Describe_updateProduct {
        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 있다면")
        class Context_existed_updateProduct {
            private Long productId = 1L;
            private Product preProduct;
            private Product nextProduct;

            @BeforeEach
            void setUpExistedUpdateProduct() {
                //given
                preProduct = saveProduct(productId);
                nextProduct = saveProduct(productId + 1);
                nextProduct.setId(productId);
                given(productRepository.findById(eq(productId)))
                        .willReturn(Optional.of(preProduct));
                given(productRepository.save(any(Product.class)))
                        .willReturn(nextProduct);
            }

            @Test
            @DisplayName("수정한 고양이 장난감을 반환한다.")
            void existed_update_product() {
                Product returnProduct = productService.updateProduct(productId, nextProduct);
                assertAll(
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("id")
                                    .isEqualTo(productId);
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("name")
                                    .isEqualTo(nextProduct.getName());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("maker")
                                    .isEqualTo(nextProduct.getMaker());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("price")
                                    .isEqualTo(nextProduct.getPrice());
                        },
                        () -> {
                            assertThat(returnProduct)
                                    .extracting("imageUrl")
                                    .isEqualTo(nextProduct.getImageUrl());
                        }
                );
                then(productRepository).should(times(1)).findById(eq(productId));
                then(productRepository).should(times(1)).save(any(Product.class));
            }
        }

        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 없다면")
        class Context_not_existed_updateProduct {
            private Long productId = 100L;

            @BeforeEach
            void setUpNotExistedUpdateProduct() {
                product = saveProduct(productId);
                productNotFoundMessage += productId;
                given(productRepository.findById(eq(productId)))
                        .willThrow(new ProductNotFoundException(productId));
            }

            @Test
            @DisplayName("ProductNotFoundException을 발생한다.")
            void not_existed_update_exception() {
                Throwable throwable = catchThrowable(() -> {
                    productService
                            .updateProduct(productId, product);

                });
                assertTrue(throwable
                        .getClass()
                        .isAssignableFrom(ProductNotFoundException.class));

                then(productRepository)
                        .should(times(1))
                        .findById(eq(productId));
                then(productRepository)
                        .should(times(0))
                        .save(any(Product.class));
            }

            @Test
            @DisplayName("특정 에러메시지를 반환한다.")
            void not_existed_update_exception_message() {
                Throwable throwable = catchThrowable(() -> {
                    productService.updateProduct(productId, product);
                });
                assertThat(throwable.getMessage())
                        .isEqualTo(productNotFoundMessage);

                then(productRepository)
                        .should(times(1))
                        .findById(eq(productId));
                then(productRepository)
                        .should(times(0))
                        .save(any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct() 메소드는")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 있다면")
        class Context_existed_deleteProduct {
            private Long productId = 1L;

            @BeforeEach
            void setUpExistedDeleteProduct() {
                //given
                product = saveProduct(productId);
                given(productRepository.findById(eq(productId)))
                        .willReturn(Optional.of(product));
                doNothing().when(productRepository).delete(product);
            }

            @Test
            @DisplayName("고양이 장난감을 삭제한다.")
            void existed_deleteProduct() {
                productService.deleteProduct(productId);
                then(productRepository)
                        .should(times(1))
                        .findById(eq(productId));
                then(productRepository)
                        .should(times(1))
                        .delete(any(Product.class));
            }
        }

        @Nested
        @DisplayName("요청한 고양이 장난감이 목록에 없다면")
        class Context_not_existed_deleteProduct {
            private Long productId = 100L;

            @BeforeEach
            void setUpNotExistedDeleteProduct() {
                product = saveProduct(productId);
                productNotFoundMessage += productId;
                given(productRepository.findById(eq(productId)))
                        .willThrow(new ProductNotFoundException(productId));
                doNothing().when(productRepository).delete(product);
            }

            @Test
            @DisplayName("ProductNotFoundException을 발생한다.")
            void not_existed_deleteProduct_exception() {
                Throwable throwable = catchThrowable(() -> {
                    productService.deleteProduct(productId);
                });
                assertTrue(throwable
                        .getClass()
                        .isAssignableFrom(ProductNotFoundException.class));
                then(productRepository)
                        .should(times(1))
                        .findById(eq(productId));
                then(productRepository)
                        .should(times(0))
                        .delete(any(Product.class));
            }

            @Test
            @DisplayName("특정 에러메시지를 반환한다.")
            void not_existed_deleteProduct_exception_message() {
                Throwable throwable = catchThrowable(() -> {
                    productService.deleteProduct(productId);
                });
                assertThat(throwable.getMessage())
                        .isEqualTo(productNotFoundMessage);
                then(productRepository)
                        .should(times(1))
                        .findById(eq(productId));
                then(productRepository)
                        .should(times(0))
                        .delete(any(Product.class));
            }
        }
    }
}
