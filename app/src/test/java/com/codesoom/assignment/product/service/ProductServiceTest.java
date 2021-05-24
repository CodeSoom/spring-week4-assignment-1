package com.codesoom.assignment.product.service;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

@DisplayName("ProductService 클래스의")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

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
                assertNull(createdProduct.getImage());
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
                assertEquals(name, testProduct.getName());
                assertEquals(maker, testProduct.getMaker());
                assertEquals(price, testProduct.getPrice());
                assertNull(testProduct.getImage());
            }
        }

        @Nested
        @DisplayName("만약 등록되어 있지 않는 상품의 식별자가 주어진다면")
        class Context_with_invalid_id {
            private final Long invalidProductId = 100L;

            @Test
            @DisplayName("null을 반환한다")
            void It_returns_null() {
                // when
                Product testProduct = productService.get(invalidProductId);

                // then
                assertNull(testProduct);
            }
        }
    }
}
