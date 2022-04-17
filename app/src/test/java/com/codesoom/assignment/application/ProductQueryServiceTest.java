package com.codesoom.assignment.application;

import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.*;

@DisplayName("ProductQueryService 클래스")
@DataJpaTest
@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Mock
    private ProductQueryService productQueryService;

    private Long NOT_SAVED_ID = 100L;
    private static final String TEST_PRODUCT_MAKER = "MAKER";
    private static final String TEST_PRODUCT_NAME = "NAME";
    private static final BigDecimal TEST_PRODUCT_PRICE = BigDecimal.TEN;
    private static final String TEST_PRODUCT_IMAGE_URL = "/image.png";

    @BeforeEach
    void setUp() {
        productQueryService = new ProductQueryService(productRepository);

        Product savedProduct = new Product.ProductBuilder()
                .name(TEST_PRODUCT_NAME)
                .maker(TEST_PRODUCT_MAKER)
                .price(TEST_PRODUCT_PRICE)
                .imageUrl(TEST_PRODUCT_IMAGE_URL)
                .build();
        productRepository.save(savedProduct);
    }

    @Test
    @DisplayName("getProducts 메소드는 모든 상품의 목록을 반환한다.")
    void getProducts() {
        assertThat(productQueryService.getProducts()).isNotEmpty();
    }

    @Nested
    @DisplayName("getProductById 메소드는")
    class Describe_getProductById {

        @Nested
        @DisplayName("아이디가 상품 목록에 존재한다면")
        class Context_with_a_valid_id {

            private Long productId;

            @BeforeEach
            void prepareProduct() {
                Product productInfo = new Product.ProductBuilder()
                        .name(TEST_PRODUCT_NAME)
                        .build();
                Product product = productRepository.save(productInfo);
                productId = product.getId();
            }

            @Test
            @DisplayName("상품을 찾아서 반환한다.")
            void it_returns_a_product() {
                Product foundProduct = productQueryService.getProductById(productId);
                assertThat(foundProduct).isNotNull();
                assertThat(foundProduct.getName()).isEqualTo(TEST_PRODUCT_NAME);
            }
        }

        @Nested
        @DisplayName("아이디가 상품 목록에 존재하지 않는다면")
        class Context_with_a_invalid_id {

            @Test
            @DisplayName("예외를 던진다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> productQueryService.getProductById(NOT_SAVED_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}