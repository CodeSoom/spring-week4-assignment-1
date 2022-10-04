package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductServiceImpl 클래스")
class ProductServiceImplTest {

    private final String name = "My toy";
    private final String maker = "toy shop";
    private final int price = 15000;
    private final String imageUrl = "toy.jpg";

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Nested
    @DisplayName("get 메소드는")
    class Describe_get {

        @Nested
        @DisplayName("만약 조회 가능한 id가 주어지면")
        class Context_with_valid_id {
            private final Long id = 1L;

            @BeforeEach
            void setUp() {
                given(productRepository.findById(id)).willReturn(
                        Optional.of(new Product(name, maker, price, imageUrl))
                );
            }

            @Test
            @DisplayName("장난감을 반환한다")
            void it_returns_product() {
                Product product = productService.get(id);

                assertThat(product).isNotNull();

                verify(productRepository).findById(id);
            }
        }

        @Nested
        @DisplayName("만약 조회 불가능한 id가 주어지면")
        class Context_with_invalid_id {
            private final Long invalidId = 1000L;

            @BeforeEach
            void setUp() {
                given(productRepository.findById(invalidId))
                        .willThrow(ProductNotFoundException.class);
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.get(invalidId))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(invalidId);
            }
        }
    }

    @Nested
    @DisplayName("getAll 메소드는")
    class Describe_getAll {

        @Nested
        @DisplayName("만약 저장된 장난감이 없으면")
        class Context_with_no_products {

            @BeforeEach
            void setUp() {
                given(productRepository.findAll()).willReturn(Collections.emptyList());
            }

            @Test
            @DisplayName("빈 목록을 반환한다")
            void it_returns_empty_list() {
                List<Product> products = productService.getAll();

                assertThat(products).isEmpty();

                verify(productRepository).findAll();
            }
        }

        @Nested
        @DisplayName("만약 저장된 장난감이 있으면")
        class Context_with_products {

            @BeforeEach
            void setUp() {
                given(productRepository.findAll()).willReturn(
                        List.of(new Product(name, maker, price, imageUrl))
                );
            }

            @Test
            @DisplayName("모든 장난감을 반환한다")
            void it_returns_all_products() {
                List<Product> products = productService.getAll();

                assertThat(products).isNotEmpty();

                verify(productRepository).findAll();
            }
        }
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {
        private final Product product = new Product(name, maker, price, imageUrl);

        @BeforeEach
        void setUp() {
            given(productRepository.save(any(Product.class))).willReturn(product);
        }

        @Test
        @DisplayName("장난감을 생성해 반환한다")
        void it_returns_new_product() {
            Product createdProduct = productService.create(product);

            assertThat(createdProduct).isNotNull();

            verify(productRepository).save(product);
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("만약 수정이 가능한 경우")
        class Context_with_valid_id {
            private final Long id = 1L;
            private Product source;

            @BeforeEach
            void setUp() {
                final String updatePrefix = "updated_";
                final int updatedPrice = 20000;

                source = new Product(updatePrefix + name,
                        updatePrefix + maker,
                        updatedPrice,
                        updatePrefix + imageUrl);

                given(productRepository.findById(id)).willReturn(Optional.of(source));
            }

            @Test
            @DisplayName("수정된 장난감을 반환한다")
            void it_returns_updated_task() {
                Product updatedProduct = productService.update(id, source);

                assertThat(updatedProduct).isNotNull();
                assertThat(updatedProduct.getName()).isEqualTo(source.getName());

                verify(productRepository).findById(any());
            }
        }

        @Nested
        @DisplayName("만약 수정이 불가능한 경우")
        class Context_with_invalid_id {
            private final Long invalidId = 1000L;
            private Product source;

            @BeforeEach
            void setUp() {
                source = new Product(name, maker, price, imageUrl);

                given(productRepository.findById(invalidId)).willThrow(ProductNotFoundException.class);
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.update(invalidId, source))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

        @Nested
        @DisplayName("만약 삭제 가능한 id가 주어지면")
        class Context_with_valid_id {
            private final Long id = 1L;

            @BeforeEach
            void setUp() {
                given(productRepository.findById(id))
                        .willReturn(Optional.of(new Product(name, maker, price, imageUrl)));
            }

            @Test
            @DisplayName("삭제를 수행한다")
            void it_removes_product() {
                productService.deleteById(id);

                verify(productRepository).delete(any(Product.class));
            }
        }

        @Nested
        @DisplayName("만약 삭제 불가능한 id가 주어지면")
        class Context_with_invalid_id {
            private final Long invalidId = 1000L;

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.deleteById(invalidId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
