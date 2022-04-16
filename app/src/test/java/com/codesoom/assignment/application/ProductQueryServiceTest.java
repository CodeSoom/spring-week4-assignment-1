package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.factories.ProductFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.LongStream;

import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_IMAGE_PATH;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_MAKER;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_NAME;
import static com.codesoom.assignment.ProductTestFixture.TEST_PRODUCT_PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("ProductQueryService 클래스")
class ProductQueryServiceTest {

    ProductQueryService productQueryService;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productQueryService = new ProductQueryService(productRepository);
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {

        @Nested
        @DisplayName("주어진 상품 수 만큼")
        class Context_hasProducts {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {
                LongStream.rangeClosed(1, givenCount)
                        .mapToObj(index -> ProductFactory.getEmptyProduct())
                        .forEach(product -> productRepository.save(product));
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

        @Nested
        @DisplayName("주어진 아이디와 일치하는 상품이 있다면")
        class Context_existsProduct {

            Long productId;

            @BeforeEach
            void setUp() {
                final Product product = ProductFactory.getProduct(
                        TEST_PRODUCT_NAME, TEST_PRODUCT_MAKER, TEST_PRODUCT_PRICE, TEST_PRODUCT_IMAGE_PATH);

                productRepository.save(product);
                productId = product.getId();
            }

            @Test
            @DisplayName("상품을 리턴한다.")
            void it_return_product() {

                Product product = productQueryService.getProduct(productId);

                assertThat(product.getId()).isEqualTo(productId);
            }
        }

        @Nested
        @DisplayName("주어진 아이디와 일치하는 상품이 없다면")
        class Context_notExistsProduct {

            final Long notExistsProductId = 100L;

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
