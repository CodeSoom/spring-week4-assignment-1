package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private static final String PRODUCT_NAME = "test";

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);

        productService = new ProductService(productRepository);
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        Product product;

        @Nested
        @DisplayName("장난감이 존재한다면")
        class Context_with_product {

            @BeforeEach
            void setUp() {
                List<Product> products = new ArrayList<>();

                product = new Product();
                products.add(product);

                given(productRepository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("장난감 목록을 반환한다 ")
            void it_returns_list() {
                List<Product> products = productService.getProducts();

                verify(productRepository).findAll();

                assertThat(products).hasSize(1);
            }
        }

        @Nested
        @DisplayName("장난감이 존재하지 않는다면")
        class Context_without_product {

            @Test
            @DisplayName("빈 목록을 반환한다")
            void it_returns_list() {
                List<Product> products = productService.getProducts();

                assertThat(products).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {
        Product product;

        @Nested
        @DisplayName("등록된 장난감의 ID가 주어진다면")
        class Context_with_valid_id {

            @BeforeEach
            void setUp() {
                product = new Product();
                product.setName(PRODUCT_NAME);

                given(productRepository.findById(1L))
                        .willReturn(ofNullable(product));
            }

            @Test
            @DisplayName("해당 ID를 갖는 장난감을 반환한다")
            void it_returns_product() {
                Product product = productService.getProduct(1L);

                verify(productRepository).findById(1L);

                assertThat(product.getName()).isEqualTo("test");
            }
        }

        @Nested
        @DisplayName("등록되지 않은 장난감의 ID가 주어진다면")
        class Context_with_invalid_id {

            @Test
            @DisplayName("해당 장난감을 찾을 수 없다는 예외를 던진다")
            void it_returns_warning_message() {
                assertThatThrownBy(() -> productService.getProduct(100L))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(100L);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {
        Product product;

        @Test
        @DisplayName("새로운 장난감을 등록한다")
        void it_returns_product() {
            product = new Product();
            Product createdProduct = productService.createProduct(product);

            given(productRepository.save(any(Product.class)))
                    .will(invocation -> invocation.<Product>getArgument(0));

            verify(productRepository).save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {
        Product product;

        @Nested
        @DisplayName("등록된 장난감의 ID가 주어진다면")
        class Context_with_valid_id_and_product {

            @BeforeEach
            void setUp() {
                product = new Product();
                product.setName("물고기 장난감");
                product.setMaker("애옹이네 장난감");
                product.setPrice(5000);
                product.setImage("https://bit.ly/2M4YXkw");

                given(productRepository.findById(1L))
                        .willReturn(ofNullable(product));
            }

            @Test
            @DisplayName("해당 ID를 갖는 장난감의 정보를 수정하고 반환한다")
            void it_returns_updated_product() {
                Product updatedProduct = productService.updateProduct(1L, product);

                verify(productRepository).findById(1L);

                assertThat(product.getName()).isEqualTo("물고기 장난감");
                assertThat(product.getMaker()).isEqualTo("애옹이네 장난감");
                assertThat(product.getPrice()).isEqualTo(5000);
                assertThat(product.getImage()).isEqualTo("https://bit.ly/2M4YXkw");
            }
        }

        @Nested
        @DisplayName("등록되지 않은 장난감의 ID가 주어진다면")
        class Context_with_invalid_id {

            @Test
            @DisplayName("수정할 장난감을 찾을 수 없다는 예외를 던진다")
            void it_returns_warning_message() {
                assertThatThrownBy(() -> productService.updateProduct(100L, product))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(100L);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {
        Product product;

        @Nested
        @DisplayName("등록된 장난감의 ID가 주어진다면")
        class Context_with_valid_id {

            @BeforeEach
            void setUp() {
                product = new Product();

                given(productRepository.findById(1L))
                        .willReturn(ofNullable(product));
            }

            @Test
            @DisplayName("해당 ID를 갖는 장난감을 삭제하고 반환한다")
            void it_returns_deleted_product() {
                productService.deleteProduct(1L);

                verify(productRepository).findById(1L);
                verify(productRepository).delete(any(Product.class));

                assertThat(productRepository.findAll()).isNotIn(1L);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 장난감의 ID가 주어진다면")
        class Context_without_invalid_id {

            @Test
            @DisplayName("삭제할 장난감을 찾을 수 없다는 예외를 던진다")
            void it_returns_warning_message() {
                assertThatThrownBy(() -> productService.deleteProduct(100L))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(productRepository).findById(100L);
            }
        }
    }
}
