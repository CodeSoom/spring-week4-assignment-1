package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductController class")
class ProductControllerTest {

    private ProductController productController;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Nested
    @DisplayName("list 메소드는")
    class Describe_of_list {

        @Nested
        @DisplayName("등록된 상품이 존재하지 않는다면")
        class Context_of_empty_registered_products {

            @BeforeEach
            void setUp() {
                given(productService.getAllProducts())
                        .willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("비어있는 상품목록을 반환한다")
            void it_returns_products_as_list() {
               assertThat(productController.list())
                       .isEmpty();
            }
        }

        @Nested
        @DisplayName("등록된 상품이 존재한다면")
        class Context_of_registered_products_exist {

            private Long productsSize;

            @BeforeEach
            void setUp() {
                productsSize = 2L;
                List<Product> products = generateProducts(productsSize);
                given(productService.getAllProducts())
                        .willReturn(products);
            }

            @Test
            @DisplayName("등록된 모든 상품의 목록을 반환한다")
            void it_returns_products_list() {
                assertThat(productController.list())
                        .hasSize(Math.toIntExact(productsSize));
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_of_getProduct {

        @Nested
        @DisplayName("등록된 상품이 존재하고")
        class Context_of_registered_product_exist {

            private Product registeredProduct;
            private Long existentId;

            @BeforeEach
            void setUp() {
                existentId = 1L;
                registeredProduct = generateProduct(existentId);
                given(productService.getProduct(existentId))
                        .willReturn(registeredProduct);
            }

            @Nested
            @DisplayName("존재하는 상품의 id가 주어지면")
            class Context_of_existent_id {

                private Long givenId;

                @BeforeEach
                void setUp() {
                    givenId = existentId;
                }

                @Test
                @DisplayName("상품을 반환한다")
                void it_returns_product() {
                    assertThat(productController.getProduct(givenId))
                            .isEqualTo(registeredProduct);
                }
            }

            @Nested
            @DisplayName("존재하지 않는 상품의 id가 주어지면")
            class Context_of_non_existent_id {

                private Long givenId;

                @BeforeEach
                void setUp() {
                    Product notRegisteredProduct = generateProduct(-1L);
                    givenId = notRegisteredProduct.getId();

                    given(productService.getProduct(givenId))
                            .willThrow(EmptyResultDataAccessException.class);
                }

                @Test
                @DisplayName("상품을 찾지 못 했다는 예외를 던진다")
                void it_throws_exception() {
                    assertThatThrownBy(() -> productController.getProduct(givenId))
                            .isInstanceOf(EmptyResultDataAccessException.class);
                }
            }
        }
    }

    @Nested
    @DisplayName("addProduct 메소드는")
    class Describe_of_addProduct {

        @Nested
        @DisplayName("상품이 주어지면")
        class Context_of_product {

            private Product givenProduct;

            @BeforeEach
            void setUp() {
                givenProduct = generateProduct(1L);

                given(productService.addProduct(any(Product.class)))
                        .will(invocation -> invocation.getArgument(0));
            }

            @Test
            @DisplayName("상품을 상품 목록에 추가하고, 추가한 상품을 반환한다")
            void it_appends_product_to_list() {
                Product product = productController.addProduct(givenProduct);

                verify(productService).addProduct(givenProduct);

                assertThat(product)
                        .isEqualTo(givenProduct);
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_of_updateProduct {

        @Nested
        @DisplayName("등록된 상품이 존재할 때")
        class Context_of_registered_product_exist {

            private Product registeredProduct;
            private Long existentId;

            @BeforeEach
            void setUp() {
                existentId = 1L;
                registeredProduct = generateProduct(existentId);
            }

            @Nested
            @DisplayName("존재하는 상품의 id와 수정할 내용이 주어지면")
            class Context_of_existent_id_with_source {

                private Long givenId;
                private Product source;
                final private String SOURCE_NAME = "SOURCE";

                @BeforeEach
                void setUp() {
                    givenId = existentId;
                    source = generateProduct(42L);
                    source.setName(SOURCE_NAME);

                    given(productService.updateProduct(eq(givenId), any(Product.class)))
                            .will(invocation -> invocation.getArgument(1));
                }

                @Test
                @DisplayName("상품을 갱신하고, 갱신한 상품을 반환한다")
                void it_updates_and_returns_product() {
                    Product product = productController.updateProduct(givenId, source);

                    verify(productService).updateProduct(givenId, source);

                    assertThat(product.getName())
                            .isEqualTo(SOURCE_NAME);
                }
            }

            @Nested
            @DisplayName("존재하지 않는 상품의 id와 수정할 내용이 주어지면")
            class Context_of_non_existent_id_with_source {

                private Long givenId;
                private Product source;
                final private String SOURCE_NAME = "SOURCE";

                @BeforeEach
                void setUp() {
                    Product nonRegisteredProduct = generateProduct(-1L);
                    givenId = nonRegisteredProduct.getId();
                    source = generateProduct(42L);
                    source.setName(SOURCE_NAME);

                    doThrow(new EmptyResultDataAccessException(Math.toIntExact(givenId)))
                            .when(productService)
                            .updateProduct(givenId, source);
                }

                @Test
                @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
                void it_throws_exception() {
                    assertThatThrownBy(() -> productController.updateProduct(givenId, source))
                            .isInstanceOf(EmptyResultDataAccessException.class);
                }
            }
        }
    }

    @Nested
    @DisplayName("removeProduct 메소드는")
    class Describe_of_removeProduct {

        @Nested
        @DisplayName("등록된 상품이 존재할 때")
        class Context_of_registered_product_exist {

            private Product registeredProduct;
            private Long existentId;

            @BeforeEach
            void setUp() {
                existentId = 1L;
                registeredProduct = generateProduct(existentId);
            }

            @Nested
            @DisplayName("존재하는 상품의 id가 주어지면")
            class Context_of_existent_id_with_source {

                private Long givenId;

                @BeforeEach
                void setUp() {
                    givenId = existentId;
                }

                @Test
                @DisplayName("상품을 상품 목록에서 제거한다")
                void it_removes_product_from_product_list() {
                    productController.removeProduct(givenId);

                    verify(productService).deleteProduct(givenId);
                }
            }

            @Nested
            @DisplayName("존재하지 않는 상품의 id가 주어지면")
            class Context_of_non_existent_id {

                private Long givenId;

                @BeforeEach
                void setUp() {
                    Product nonRegisteredProduct = generateProduct(-1L);
                    givenId = nonRegisteredProduct.getId();

                    doThrow(new EmptyResultDataAccessException(Math.toIntExact(givenId)))
                            .when(productService)
                            .deleteProduct(givenId);
                }

                @Test
                @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
                void it_throws_exception() {
                    assertThatThrownBy(() -> productController.removeProduct(givenId))
                            .isInstanceOf(EmptyResultDataAccessException.class);
                }
            }
        }
    }

    private Product generateProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("product" + id);
        product.setMaker("maker" + id);
        product.setPrice(100 * id);
        product.setImageUrl("product" + id + ".jpg");
        return product;
    }

    private List<Product> generateProducts(long size) {
       List<Product> products = new ArrayList<>();

        for (long i = 1; i < size + 1; i++) {
            products.add(generateProduct(i));
        }
        return products;
    }
}
