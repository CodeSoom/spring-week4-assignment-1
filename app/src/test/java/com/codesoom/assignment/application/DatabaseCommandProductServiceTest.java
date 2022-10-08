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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class DatabaseCommandProductServiceTest {
    private Long INVALID_PRODUCT_ID = 0L;

    @Autowired
    private DatabaseCommandProductService databaseCommandProductService;

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_createProduct {
        @Nested
        @DisplayName("product 가 주어진다면")
        class Context_with_product {
            private Product requestProduct;

            @BeforeEach
            void setUp() {
                requestProduct = Product.builder()
                        .id(null)
                        .name("장난감1")
                        .maker("M")
                        .price(1000)
                        .imageUrl("http://images/1")
                        .build();
            }

            @AfterEach
            void after() {
                databaseCommandProductService.deleteAll();
            }

            @Test
            @DisplayName("product 를 저장하고 리턴한다")
            void it_returns_product() {
                Product savedProduct = databaseCommandProductService.createProduct(requestProduct);

                assertThat(savedProduct.getId()).isEqualTo(requestProduct.getId());
                assertThat(savedProduct.getName()).isEqualTo(requestProduct.getName());
                assertThat(savedProduct.getMaker()).isEqualTo(requestProduct.getMaker());
                assertThat(savedProduct.getPrice()).isEqualTo(requestProduct.getPrice());
                assertThat(savedProduct.getImageUrl()).isEqualTo(requestProduct.getImageUrl());
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_updateProduct {
        @Nested
        @DisplayName("요청하는 product의 일부 필드가 null 인 경우")
        class Context_with_partial_value {
            private Product requestProduct;
            private Product savedProduct;


            @BeforeEach
            void setUp() {
                requestProduct = Product.builder()
                        .id(null)
                        .name("장난감1")
                        .maker(null)
                        .price(1000)
                        .imageUrl(null)
                        .build();

                savedProduct = databaseCommandProductService.createProduct(
                        Product.builder()
                                .id(null)
                                .name("장난감1")
                                .maker("M")
                                .price(2000)
                                .imageUrl("http://image.com")
                                .build()
                );
            }

            @Test
            @DisplayName("필드가 null 인 경우 수정하지 않고, 값이 있는 경우 수정 후 리턴한다")
            void it_returns_partial_updated_product() {
                Product updatedProduct = databaseCommandProductService.updateProduct(savedProduct.getId(), requestProduct);

                assertThat(updatedProduct.getName()).isEqualTo(requestProduct.getName());
                assertThat(updatedProduct.getMaker()).isEqualTo(savedProduct.getMaker());
                assertThat(updatedProduct.getPrice()).isEqualTo(requestProduct.getPrice());
                assertThat(updatedProduct.getImageUrl()).isEqualTo(savedProduct.getImageUrl());
            }
        }

        @Nested
        @DisplayName("요청하는 product 의 필드가 모두 있는 경우")
        class Context_with_full_value {
            private Product requestProduct;
            private Product savedProduct;

            @BeforeEach
            void setUp() {
                requestProduct = Product.builder()
                        .id(null)
                        .name("장난감1after")
                        .maker("K")
                        .price(3000)
                        .imageUrl("http://image10.com")
                        .build();

                savedProduct = databaseCommandProductService.createProduct(
                        Product.builder()
                                .id(null)
                                .name("장난감1")
                                .maker("M")
                                .price(2000)
                                .imageUrl("http://image.com")
                                .build()
                );
            }

            @Test
            @DisplayName("모든 필드를 수정 후 리턴한다")
            void it_returns_partial_updated_product() {
                Product updatedProduct = databaseCommandProductService.updateProduct(savedProduct.getId(), requestProduct);

                assertThat(updatedProduct.getName()).isEqualTo(requestProduct.getName());
                assertThat(updatedProduct.getMaker()).isEqualTo(requestProduct.getMaker());
                assertThat(updatedProduct.getPrice()).isEqualTo(requestProduct.getPrice());
                assertThat(updatedProduct.getImageUrl()).isEqualTo(requestProduct.getImageUrl());
            }
        }

        @Nested
        @DisplayName("저장되어 있지 않은 product 의 id로 요청한 경우")
        class Context_with_non_existence_id {
            private Product requestProduct;

            @BeforeEach
            void setUp() {
                requestProduct = Product.builder()
                        .id(null)
                        .name("장난감1after")
                        .maker("K")
                        .price(3000)
                        .imageUrl("http://image10.com")
                        .build();
            }

            @Test
            @DisplayName("제품을 찾을 수 없는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(
                        () -> databaseCommandProductService.updateProduct(INVALID_PRODUCT_ID, requestProduct)
                ).isExactlyInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_deleteProduct {

        @Nested
        @DisplayName("저장되어있는 product 의 id가 주어진다면 ")
        @SpringBootTest
        class Context_with_existing_product_id {

            @Autowired
            private DatabaseQueryProductService databaseQueryProductService;

            private Long deleteId;

            @BeforeEach
            void setUp() {
                Product savedProduct = databaseCommandProductService.createProduct(
                        Product.builder()
                                .id(null)
                                .name("장난감1after")
                                .maker("K")
                                .price(3000)
                                .imageUrl("http://image10.com")
                                .build()
                );
                deleteId = savedProduct.getId();
            }

            @Test
            @DisplayName("product 를 삭제한다")
            void it_delete_product() {
                databaseCommandProductService.deleteProduct(deleteId);

                assertThatThrownBy(
                        () -> databaseQueryProductService.getProduct(deleteId)
                ).isExactlyInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("저장되어 있지 않는 product 의 id가 주어진다면 ")
        class Context_with_non_existence_product_id {

            @Test
            @DisplayName("제품을 찾을 수 없는 예외를 던진다")
            void it_delete_product() {
                assertThatThrownBy(
                        () -> databaseCommandProductService.deleteProduct(INVALID_PRODUCT_ID)
                ).isExactlyInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
