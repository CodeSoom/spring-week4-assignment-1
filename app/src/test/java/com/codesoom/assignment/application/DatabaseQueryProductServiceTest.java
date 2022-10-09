package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class DatabaseQueryProductServiceTest {
    private Long INVALID_PRODUCT_ID = 0L;

    @Autowired
    private DatabaseQueryProductService databaseQueryProductService;
    @Autowired
    private DatabaseCommandProductService databaseCommandProductService;

    @Nested
    @DisplayName("getProducts 메서드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("저장되어 있는 product 가 여러 개 있을 때")
        class Context_with_products {
            private List<Product> givenProducts;

            @BeforeEach
            void setUp() {
                givenProducts = List.of(
                        Product.builder()
                                .id(null)
                                .name("장난감1")
                                .maker("M")
                                .price(1000)
                                .imageUrl("http://images/1")
                                .build(),
                        Product.builder()
                                .id(null)
                                .name("장난감2")
                                .maker("K")
                                .price(1000)
                                .imageUrl("http://images/2")
                                .build()
                );
                givenProducts.forEach(product -> databaseCommandProductService.createProduct(product));
            }

            @AfterEach
            void after() {
                databaseCommandProductService.deleteAll();
            }

            @Test
            @DisplayName("모든 product 를 리턴한다")
            void it_returns_products() {
                List<Product> products = databaseQueryProductService.getProducts();
                assertThat(products.size()).isEqualTo(givenProducts.size());
            }
        }

        @Nested
        @DisplayName("저장되어 있는 product 가 없을 때")
        class Context_without_product {
            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_empty() {
                assertThat(databaseQueryProductService.getProducts()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("저장되어 있는 product 의 id가 주어지면")
        class Context_with_existing_product_id {
            private Product testProduct;

            @BeforeEach
            void setUp() {
                testProduct = databaseCommandProductService.createProduct(
                        Product.builder()
                                .id(null)
                                .name("장난감1")
                                .maker("M")
                                .price(1000)
                                .imageUrl("http://images/1")
                                .build()
                );
            }

            @AfterEach
            void after() {
                databaseCommandProductService.deleteAll();
            }

            @Test
            @DisplayName("요청에 맞는 product 객체를 리턴한다")
            void it_returns_product() {
                Product product = databaseQueryProductService.getProduct(testProduct.getId());

                assertThat(product.getId()).isEqualTo(testProduct.getId());
                assertThat(product.getName()).isEqualTo(testProduct.getName());
                assertThat(product.getMaker()).isEqualTo(testProduct.getMaker());
                assertThat(product.getPrice()).isEqualTo(testProduct.getPrice());
                assertThat(product.getImageUrl()).isEqualTo(testProduct.getImageUrl());
            }
        }

        @Nested
        @DisplayName("저장되어 있지 않은 product id가 주어지면")
        class Context_with_non_existent_product_id {
            @Test
            @DisplayName("제품을 찾을 수 없는 예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(
                        () -> databaseQueryProductService.getProduct(INVALID_PRODUCT_ID)
                ).isExactlyInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
