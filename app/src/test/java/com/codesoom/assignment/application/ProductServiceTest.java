package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.entity.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@DisplayName("ProductServiceImpl 에서")
class ProductServiceTest {
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "메이커1";
    private static final Integer PRODUCT_PRICE = 100000;
    private static final String PRODUCT_IMAGE_URL = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9941A1385B99240D2E";

    private static final String UPDATE_PRODUCT_NAME = "상품1000";

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService = new ProductService(productRepository);

    /**
     * 여러개의 Product 를 생성해 등록합니다.
     * @param createProuctSize 생성할 Product의 갯수
     */
    private void createProduct(long createProuctSize) {
        for (long i = 0; i < createProuctSize; i++) {
            ProductDto productDto = new ProductDto
                    .Builder(PRODUCT_PRICE, PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();
            productService.createProduct(productDto);
        }
    }

    @Nested
    @DisplayName("모든 Product 객체를 불러올 때")
    class Describe_of_readAll_products {

        @Nested
        @DisplayName("Product 객체가 없을 경우")
        class Context_without_product {
            final long createProductSize = 1L;

            @BeforeEach
            void setUp() {
                createProduct(createProductSize);
                productRepository.deleteAll();
            }

            @Test
            @DisplayName("빈 배열을 리턴한다")
            void it_returns_emtpy_list() {
                List<Product> products = productService.getProductList();

                assertThat(products).isEmpty();
                assertThat(products).hasSize(0);
            }
        }

        @Nested
        @DisplayName("Product 객체가 있을 경우")
        class Context_with_product {
            final long createProductSize = 3L;

            @BeforeEach
            void setUp() {
                createProduct(createProductSize);
            }

            @Test
            @DisplayName("Product 객체가 포함된 배열을 리턴한다")
            void it_returns_list_of_product() {
                List<Product> products = productService.getProductList();
                assertThat(products).hasSize((int) createProductSize);
            }
        }
    }
}