package com.codesoom.assignment.product.service;

import com.codesoom.assignment.error.exception.ProductNotFoundException;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import org.assertj.core.api.ThrowableAssert;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

@DisplayName("ProductService 클래스의")
@ExtendWith(MockitoExtension.class)
public class ProductServiceMockTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {

        @Nested
        @DisplayName("만약 1개의 상품이 주어진다면")
        class Context_with_one_product {
            private final String name = "cat1";
            private final String maker = "codesoom";
            private final Long price = 33_000L;
            private Product product = new Product(name, maker, price);

            @BeforeEach
            void mocking() {
                given(productRepository.save(product))
                        .willReturn(product);
            }

            @Test
            @DisplayName("새로운 상품을 생성한 후 생성된 상품을 반환한다")
            void It_creates_the_product_and_returns_it() {
                // when
                Product createdProduct = productService.create(product);

                // then
                assertEquals(name, createdProduct.getName());
                assertEquals(maker, createdProduct.getMaker());
                assertEquals(price, createdProduct.getPrice());
                assertNull(createdProduct.getImageUrl());
            }
        }
    }

    @Nested
    @DisplayName("get 메서드는")
    class Describe_get {

        @Nested
        @DisplayName("만약 등록되어 있는 상품의 유효한 식별자가 주어진다면")
        class Context_with_valid_id {
            private final Long validProductId = 1L;
            private final String name = "cat1";
            private final String maker = "codesoom";
            private final Long price = 33_000L;

            @BeforeEach
            void mocking() {
                final Product product = new Product(name, maker, price);

                given(productRepository.findById(validProductId))
                        .willReturn(Optional.ofNullable(product));
            }

            @Test
            @DisplayName("해당하는 상품을 반환한다")
            void It_returns_the_product() {
                // when
                Product testProduct = productService.get(validProductId);

                // then
                assertThat(name).isEqualTo(testProduct.getName());
                assertThat(maker).isEqualTo(testProduct.getMaker());
                assertThat(price).isEqualTo(testProduct.getPrice());
                assertNull(testProduct.getImageUrl());
            }
        }

        @Nested
        @DisplayName("만약 등록되어 있지 않는 상품의 식별자가 주어진다면")
        class Context_with_invalid_id {
            private final Long invalidProductId = 100L;

            @Test
            @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
            void It_returns_null() {
                // when
                ThrowableAssert.ThrowingCallable throwable =
                        () -> productService.get(invalidProductId);

                // then
                assertThatThrownBy(throwable)
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list {

        @Nested
        @DisplayName("만약 등록되어 있는 상품이 2개 있다면")
        class Context_with_one_product {
            private final List<Product> products = new ArrayList<>();
            private final int totalProductCount = 2;
            private final String name = "cat";
            private final String maker = "codesoom";
            private final Long price = 33_000L;

            @BeforeEach
            void mocking() {
                for (int index = 1; index <= totalProductCount; index++) {
                    Product product = new Product(name, maker, price);
                    products.add(product);
                }

                given(productRepository.findAll())
                        .willReturn(products);
            }

            @Test
            @DisplayName("상품이 2개 담긴 리스트를 반환한다")
            void It_returns_empty_list() {
                // when
                final List<Product> testProductList = productService.list();

                // then
                assertThat(totalProductCount)
                        .isEqualTo(testProductList.size());
                MatcherAssert.assertThat(testProductList.get(0)
                                                        .getName(),
                                         CoreMatchers.containsString("cat"));
                assertThat(maker)
                        .isEqualTo(testProductList.get(0)
                                                  .getMaker());
                assertThat(price)
                        .isEqualTo(testProductList.get(0)
                                                  .getPrice());
            }
        }

        @Nested
        @DisplayName("만약 등록되어 있는 상품이 없다면")
        class Context_with_empty_product {
            private final List<Product> products = new ArrayList<>();

            @BeforeEach
            void mocking() {
                given(productRepository.findAll())
                        .willReturn(products);
            }

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void It_returns_empty_list() {
                // when
                List<Product> testProductList = productService.list();

                // then
                assertThat(testProductList).isEqualTo(products);
            }
        }
    }

}
