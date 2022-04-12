package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductQueryService 클래스")
class ProductQueryServiceTest {

    @InjectMocks
    ProductQueryService productQueryService;

    @Mock
    ProductRepository productRepository;

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {

        @Nested
        @DisplayName("주어진 상품 수 만큼")
        class Context_hasProducts {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {
                Iterable<Product> products = LongStream.rangeClosed(1, givenCount)
                        .mapToObj(Product::new)
                        .collect(Collectors.toList());

                given(productRepository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("상품 목록을 리턴한다.")
            void it_return_products() {

                List<Product> products = productQueryService.getProducts();

                assertThat(products).hasSize(givenCount);
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드는")
    class Describe_getProduct {

        final Long productId = 1L;

        final Long notExistsProductId = 100L;

        @Nested
        @DisplayName("주어진 아이디와 일치하는 상품이 있다면")
        class Context_existsProduct {

            final Product givenProduct = new Product(productId);

            @BeforeEach
            void setUp() {
                given(productRepository.findById(productId)).willReturn(Optional.of(givenProduct));
            }

            Product subject() {
                return productQueryService.getProduct(productId);
            }

            @Test
            @DisplayName("상품을 리턴한다.")
            void it_return_product() {
                assertThat(subject()).isEqualTo(givenProduct);
            }
        }

        @Nested
        @DisplayName("주어진 아이디와 일치하는 상품이 없다면")
        class Context_notExistsProduct {

            @BeforeEach
            void setUp() {
                given(productRepository.findById(notExistsProductId)).willThrow(ProductNotFoundException.class);
            }

            @Test
            @DisplayName("예외를 던진다.")
            void it_throw_exception() {
                assertThatThrownBy(
                        () -> productQueryService.getProduct(notExistsProductId)
                ).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
