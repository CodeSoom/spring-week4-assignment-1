package com.codesoom.assignment.application;

import com.codesoom.assignment.application.query.ProductQueryService;
import com.codesoom.assignment.application.query.ProductQueryServiceImpl;
import com.codesoom.assignment.common.ProductFactory;
import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductInfo;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@DisplayName("ProductQueryService 클래스")
class ProductQueryServiceMockTest {

    private ProductQueryService productService;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductQueryServiceImpl(productRepository);
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("데이터가 존재한다면")
        class Context_with_non_empty_data {
            private final List<Product> givenProducts = new ArrayList<>();

            @BeforeEach
            void prepare() {

                givenProducts.add(ProductFactory.createProduct(1L));
                givenProducts.add(ProductFactory.createProduct(2L));

                given(productRepository.findAll()).willReturn(givenProducts);
            }

            @Test
            @DisplayName("모든 상품을 리턴한다")
            void it_returns_all_products() {
                final List<ProductInfo> actualProducts = productService.getProducts();

                assertThat(actualProducts).hasSize(givenProducts.size());
            }
        }

        @Nested
        @DisplayName("데이터가 존재하지 않는다면")
        class Context_with_empty_data {
            @BeforeEach
            void prepare() {
                given(productRepository.findAll()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("빈 컬렉션을 리턴한다")
            void it_returns_empty_data() {
                final List<ProductInfo> actualProducts = productService.getProducts();

                assertThat(actualProducts).hasSize(0);
            }
        }

    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private final Long PRODUCT_ID = 1L;
            private final Product givenProduct = ProductFactory.createProduct(PRODUCT_ID);

            @BeforeEach
            void prepare() {
                given(productRepository.findById(PRODUCT_ID)).willReturn(Optional.of(givenProduct));
            }

            @Test
            @DisplayName("상품을 찾아 리턴한다")
            void it_returns_searched_product() {
                final ProductInfo actualProduct = productService.getProduct(PRODUCT_ID);

                assertThat(actualProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(actualProduct.getMaker()).isEqualTo(givenProduct.getMaker());
                assertThat(actualProduct.getPrice()).isEqualTo(givenProduct.getPrice());
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id {
            private final Long PRODUCT_ID = 100L;

            @BeforeEach
            void prepare() {
                given(productRepository.findById(any(Long.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.getProduct(PRODUCT_ID)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}

