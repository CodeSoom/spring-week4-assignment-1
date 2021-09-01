package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ProductServiceTest {

    private static final long ID = 1L;
    private static final long INVALID_ID = 2L;

    private Product product;
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        product = Product.builder()
            .name("name")
            .maker("maker")
            .price(10000L)
            .imageUrl("imageUrl")
            .build();
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        given(productRepository.save(product))
            .willReturn(product);

        given(productRepository.findAll())
            .willReturn(Collections.singletonList(product));

        given(productRepository.findById(ID))
            .willReturn(Optional.of(product));

        given(productRepository.findById(INVALID_ID))
            .willThrow(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("상품 하나를 생성한다")
    void createProduct() {
        Product createdProduct = productService.createProduct(product);

        assertThat(createdProduct).isEqualTo(product);

        verify(productRepository).save(createdProduct);
    }

    @Test
    @DisplayName("모든 상품을 조회한다")
    void getAllProducts() {
        Iterable<Product> products = productService.getAllProducts();

        assertThat(products).isEqualTo(Collections.singletonList(product));

        verify(productRepository).findAll();
    }

    @Nested
    @DisplayName("단일 조회 시")
    class Describe_getProduct {

        @Nested
        @DisplayName("존재하는 상품일 경우")
        class Context_existProduct {

            Product foundProduct;

            @BeforeEach
            void setUp() {
                foundProduct = productService.getProduct(ID);
            }

            @Test
            @DisplayName("조회한다")
            void it_read() {
                assertThat(foundProduct).isEqualTo(product);

                verify(productRepository).findById(ID);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품일 경우")
        class Context_notExistProduct {

            private final long notExistId = INVALID_ID;

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() -> productService.getProduct(notExistId))
                    .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(notExistId);
            }
        }
    }

    @Nested
    @DisplayName("수정 시")
    class Describe_updateProduct {

        private Product source;

        @BeforeEach
        void setUp() {
            source = Product.builder()
                .name("a")
                .maker("b")
                .price(100L)
                .imageUrl("c")
                .build();
        }

        @Nested
        @DisplayName("존재하는 상품일 경우")
        class Context_existProduct {

            Product updatedProduct;

            @BeforeEach
            void setUp() {
                updatedProduct = productService.updateProduct(ID, source);
            }

            @Test
            @DisplayName("수정한다")
            void it_update() {
                assertThat(source).isEqualTo(updatedProduct);

                verify(productRepository).findById(ID);
                verify(productRepository).save(source);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품일 경우")
        class Context_notExistProduct {

            private final long notExistId = INVALID_ID;

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() -> productService.updateProduct(notExistId, source))
                    .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(notExistId);
                verify(productRepository, never()).save(source);
            }
        }
    }

    @Nested
    @DisplayName("삭제 시")
    class Describe_deleteProduct {

        @Nested
        @DisplayName("존재하는 상품일 경우")
        class Context_existProduct {

            private final long existId = ID;

            @Test
            @DisplayName("삭제한다")
            void it_delete() {
                productService.deleteProduct(existId);

                verify(productRepository).findById(existId);
                verify(productRepository).delete(any(Product.class));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품일 경우")
        class Context_notExistProduct {

            private final long notExistId = INVALID_ID;

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() -> productService.deleteProduct(notExistId))
                    .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(notExistId);
                verify(productRepository, never()).delete(any(Product.class));
            }
        }
    }
}
