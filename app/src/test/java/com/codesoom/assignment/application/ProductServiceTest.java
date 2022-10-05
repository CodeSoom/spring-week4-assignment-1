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
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    private Long INVALID_PRODUCT_ID = 0L;

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("저장되어 있는 product가 여러 개 있을 때")
        class Context_with_products {
            private List<Product> givenProducts;

            @BeforeEach
            void setUp() {
                givenProducts = List.of(
                        new Product(null, "장난감1", "M", 1000, "http://images/1"),
                        new Product(null, "장난감2", "K", 1000, "http://images/2")
                );
                givenProducts.forEach(product -> productService.save(product));
            }

            @AfterEach
            void after() {
                productService.deleteAll();
            }

            @Test
            @DisplayName("모든 product 를 리턴한다")
            void it_returns_products() {
                List<Product> products = productService.findAll();
                assertThat(products.size()).isEqualTo(givenProducts.size());
            }
        }

        @Nested
        @DisplayName("저장되어 있는 product가 없을 때")
        class Context_without_product {
            @Test
            @DisplayName("빈 값을 리턴한다")
            void it_returns_empty() {
                assertThat(productService.findAll()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        @Nested
        @DisplayName("저장되어 있는 product의 id가 주어지면")
        class Context_with_existing_product_id {
            private Product testProduct;

            @BeforeEach
            void setUp() {
                testProduct = productService.save(new Product("장난감1", "M", 1000, "http://image.com"));
            }

            @AfterEach
            void after() {
                productService.deleteAll();
            }

            @Test
            @DisplayName("요청에 맞는 product 객체를 리턴한다")
            void it_returns_product() {
                Product product = productService.findById(testProduct.getId());

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
                        () -> productService.findById(INVALID_PRODUCT_ID)
                ).isExactlyInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("findByI 메서드는")
    class Describe_createProduct {
        @Nested
        @DisplayName("product가 주어진다면")
        class Context_with_product {
            private Product requestProduct;

            @BeforeEach
            void setUp() {
                requestProduct = new Product("장난감1", "M", 1000, "http://image.com");
            }

            @AfterEach
            void after() {
                productService.deleteAll();
            }

            @Test
            @DisplayName("product를 저장하고 리턴한다")
            void it_returns_product() {
                Product savedProduct = productService.createProduct(requestProduct);

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
        private Product requestProduct;

        @BeforeEach
        void setUp() {
            requestProduct = new Product("장난감1after", null, 1000, null);
        }

        @Nested
        @DisplayName("요청하는 product의 일부 필드가 null 인 경우")
        class Context_with_partial_value {
            private Product savedProduct;

            @BeforeEach
            void setUp() {
                savedProduct = productService.createProduct(new Product("장난감1", "M", 2000, "http://image.com"));
            }

            @Test
            @DisplayName("필드가 null 인 경우 수정하지 않고, 값이 있는 경우 수정 후 리턴한다")
            void it_returns_partial_updated_product() {
                Product updatedProduct = productService.updateProduct(1L, requestProduct);

                assertThat(updatedProduct.getName()).isEqualTo(requestProduct.getName());
                assertThat(updatedProduct.getMaker()).isEqualTo(savedProduct.getMaker());
                assertThat(updatedProduct.getPrice()).isEqualTo(requestProduct.getPrice());
                assertThat(updatedProduct.getImageUrl()).isEqualTo(savedProduct.getImageUrl());
            }
        }
    }
}