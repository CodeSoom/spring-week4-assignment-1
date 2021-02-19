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
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private static final String NAME = "뱀 장난감";

    private static final String UPDATE_NAME = "물고기 장난감";
    private static final String UPDATE_MAKER = "애옹이네 장난감";
    private static final Integer UPDATE_PRICE = 5000;
    private static final String UPDATE_IMAGE = "https://bit.ly/2M4YXkw";

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);

        productService = new ProductService(productRepository);

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setName(NAME);

        given(productRepository.findAll()).willReturn(products);

        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));

        given(productRepository.save(any(Product.class)))
                .will(invocation -> invocation.<Product>getArgument(0));

        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));
    }

    void updateTest(Product update) {
        assertThat(update.getName()).isEqualTo(UPDATE_NAME);
        assertThat(update.getMaker()).isEqualTo(UPDATE_MAKER);
        assertThat(update.getPrice()).isEqualTo(UPDATE_PRICE);
        assertThat(update.getImage()).isEqualTo(UPDATE_IMAGE);
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
                product = new Product();
            }

            @Test
            @DisplayName("장난감 목록을 반환한다 ")
            void it_returns_list() {
                List<Product> products = productService.getProducts();

                products.add(product);

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

            @Test
            @DisplayName("해당 ID를 갖는 장난감을 반환한다")
            void it_returns_product() {
                product = productService.getProduct(1L);

                verify(productRepository).findById(1L);

                assertThat(product.getName()).isEqualTo(NAME);            }
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

            productService.createProduct(product);

            verify(productRepository).save(any(Product.class));
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {
        Product update;

        @Nested
        @DisplayName("등록된 장난감의 ID가 주어진다면")
        class Context_with_valid_id_and_product {

            @BeforeEach
            void setUp() {
                update = Product.builder()
                        .name(UPDATE_NAME)
                        .maker(UPDATE_MAKER)
                        .price(UPDATE_PRICE)
                        .image(UPDATE_IMAGE)
                        .build();

                given(productRepository.findById(1L))
                        .willReturn(ofNullable(update));
            }

            @Test
            @DisplayName("해당 ID를 갖는 장난감의 정보를 수정하고 반환한다")
            void it_returns_updated_product() {
                productService.updateProduct(1L, update);

                verify(productRepository).findById(1L);

                updateTest(update);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 장난감의 ID가 주어진다면")
        class Context_with_invalid_id {

            @Test
            @DisplayName("수정할 장난감을 찾을 수 없다는 예외를 던진다")
            void it_returns_warning_message() {
                assertThatThrownBy(() -> productService.updateProduct(100L, update))
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
