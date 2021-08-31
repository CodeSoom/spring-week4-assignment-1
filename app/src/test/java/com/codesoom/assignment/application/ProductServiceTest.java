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

    public static final long ID = 1L;
    public static final long INVALID_ID = 2L;

    private Product product;
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        product = new Product("name", "maker", 10000L, "imageUrl");
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

            @Test
            @DisplayName("조회한다")
            void it_read() {
                Product foundProduct = productService.getProduct(ID);

                assertThat(foundProduct).isEqualTo(product);

                verify(productRepository).findById(ID);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품일 경우")
        class Context_notExistProduct {

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() -> productService.getProduct(INVALID_ID))
                    .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(INVALID_ID);
            }
        }
    }

    @Nested
    @DisplayName("수정 시")
    class Describe_updateProduct {
        private Product source;

        @BeforeEach
        void setUp() {
            source = new Product("a", "b", 100L, "c");
        }

        @Nested
        @DisplayName("존재하는 상품일 경우")
        class Context_existProduct {

            @Test
            @DisplayName("수정한다")
            void it_update() {
                Product updatedProduct = productService.updateProduct(ID, source);

                assertThat(source).isEqualTo(updatedProduct);

                verify(productRepository).findById(ID);
                verify(productRepository).save(source);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품일 경우")
        class Context_notExistProduct {

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() -> productService.updateProduct(INVALID_ID, source))
                    .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(INVALID_ID);
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

            @Test
            @DisplayName("삭제한다")
            void it_delete() {
                productService.deleteProduct(ID);

                verify(productRepository).findById(ID);
                verify(productRepository).delete(any(Product.class));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품일 경우")
        class Context_notExistProduct {

            @Test
            @DisplayName("에러를 던진다")
            void it_throws() {
                assertThatThrownBy(() -> productService.deleteProduct(INVALID_ID))
                    .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(INVALID_ID);
                verify(productRepository, never()).delete(any(Product.class));
            }
        }
    }
}
