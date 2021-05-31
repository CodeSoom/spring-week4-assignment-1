package com.codesoom.assignment.product.service;

import com.codesoom.assignment.error.exception.ProductNotFoundException;
import com.codesoom.assignment.product.ProductFixtures;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@DisplayName("ProductService 클래스의")
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ProductServiceMockTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    private List<Product> products;
    private Product productLaser;
    private Product productHelm;

    @BeforeEach
    void prepareProduct() {
        products = new ArrayList<>();
        productLaser = ProductFixtures.laser();
        productHelm = ProductFixtures.helm();
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {

        @BeforeEach
        void mocking() {
            given(productRepository.save(productLaser))
                    .willReturn(productLaser);
        }

        @Test
        @DisplayName("새로운 상품을 생성한 후 생성된 상품을 반환한다")
        void It_creates_the_product_and_returns_it() {
            // when
            Product createdProduct = productService.create(productLaser);

            // then
            assertThat(createdProduct.getName())
                    .isEqualTo(productLaser.getName());
            assertThat(createdProduct.getMaker())
                    .isEqualTo(productLaser.getMaker());
            assertThat(createdProduct.getPrice())
                    .isEqualTo(productLaser.getPrice());
            assertThat(createdProduct.getImageUrl())
                    .isEqualTo(productLaser.getImageUrl());
        }
    }

    @Nested
    @DisplayName("get 메서드는")
    class Describe_get {

        @Nested
        @DisplayName("만약 등록되어 있는 상품의 유효한 식별자가 주어진다면")
        class Context_with_valid_id {

            @BeforeEach
            void mocking() {
                given(productRepository.findById(productLaser.getId()))
                        .willReturn(Optional.ofNullable(productLaser));
            }

            @Test
            @DisplayName("해당하는 상품을 반환한다")
            void It_returns_the_product() {
                // when
                Product testProduct = productService.get(productLaser.getId());

                // then
                assertThat(testProduct.getName()).isEqualTo(productLaser.getName());
                assertThat(testProduct.getMaker()).isEqualTo(productLaser.getMaker());
                assertThat(testProduct.getPrice()).isEqualTo(productLaser.getPrice());
                assertThat(testProduct.getImageUrl()).isEqualTo(productLaser.getImageUrl());
            }
        }

        @Nested
        @DisplayName("만약 등록되어 있지 않는 상품의 식별자가 주어진다면")
        class Context_with_invalid_id {
            private final Long invalidProductId = 100L;

            @Test
            @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
            void It_returns_null() {
                assertThatThrownBy(() -> productService.get(invalidProductId))
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
            private final int totalProductCount = 2;

            @BeforeEach
            void mocking() {
                for (int index = 1; index <= totalProductCount; index++) {
                    products.add(productLaser);
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
                assertThat(testProductList.size())
                        .isEqualTo(totalProductCount);
                assertThat(testProductList.get(0)
                                          .getName()).contains(productLaser.getName());
                assertThat(testProductList.get(0)
                                          .getMaker()).isEqualTo(productLaser.getMaker());
                assertThat(testProductList.get(0)
                                          .getPrice()).isEqualTo(productLaser.getPrice());
                assertThat(testProductList.get(0)
                                          .getImageUrl()).isEqualTo(productLaser.getImageUrl());
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

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {

        @Nested
        @DisplayName("만약 등록되어 있는 상품의 식별자와 변경시키려는 상품 데이터가 주어진다면")
        class Context_with_valid_product_id_and_new_product_data {
            private final Long validProductId = 1L;

            @BeforeEach
            void mocking() {
                given(productRepository.findById(validProductId))
                        .willReturn(Optional.ofNullable(productHelm));
            }

            @Test
            @DisplayName("새로운 상품을 생성한 후 생성된 상품을 반환한다")
            void It_returns_updated_product() {
                // when
                Product updatedProduct =
                        productService.update(validProductId, productHelm);

                // then
                assertThat(updatedProduct.getName())
                        .isEqualTo(productHelm.getName());
                assertThat(updatedProduct.getMaker())
                        .isEqualTo(productHelm.getMaker());
                assertThat(updatedProduct.getPrice())
                        .isEqualTo(productHelm.getPrice());
                assertThat(updatedProduct.getImageUrl())
                        .isEqualTo(productHelm.getImageUrl());

                verify(productRepository).findById(validProductId);
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {

        @Nested
        @DisplayName("만약 등록되어 있는 상품의 식별자가 주어진다면")
        class Context_with_valid_product_id {
            private Long validProductId;

            @BeforeEach
            void mocking() {
                validProductId = productHelm.getId();

                // given(productRepository.existsById(validProductId))
                //         .willReturn(true);
                // doNothing().when(productRepository)
                //            .deleteById(validProductId);
                given(productRepository.findById(validProductId))
                        .willReturn(Optional.ofNullable(productHelm));
                doNothing()
                        .when(productRepository)
                        .delete(productHelm);
            }

            @Test
            @DisplayName("주어진 ID의 상품을 제거한다")
            void It_deletes_one_product() {
                productService.delete(validProductId);

                // verify(productRepository).existsById(validProductId);
                // verify(productRepository).deleteById(validProductId);
                verify(productRepository).findById(any(Long.class));
                verify(productRepository).delete(productHelm);
            }
        }

        @Nested
        @DisplayName("만약 등록되어 있지 않은 상품의 식별자가 주어진다면")
        class Context_with_invalid_product_id {
            private final Long invalidProductId = -1L;

            @BeforeEach
            void mocking() {
                doThrow(new ProductNotFoundException())
                        .when(productRepository)
                        .findById(invalidProductId);
            }

            @Test
            @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
            void It_throws_product_not_found() {
                assertThatThrownBy(() -> productService.delete(invalidProductId))
                        .isInstanceOf(ProductNotFoundException.class)
                        .hasMessageContaining("Product Not Found");

                verify(productRepository).findById(invalidProductId);
            }
        }
    }
}
