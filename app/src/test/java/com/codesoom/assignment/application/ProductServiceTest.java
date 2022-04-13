package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@DisplayName("ProductService 에서")
class ProductServiceTest {
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "메이커1";
    private static final Integer PRODUCT_PRICE = 100000;
    private static final String PRODUCT_IMAGE_URL = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9941A1385B99240D2E";

    private static final String UPDATE_PRODUCT_NAME = "상품1000";

    @Autowired
    private ProductService productService;

    /**
     * 여러개의 Product 를 생성해 등록합니다.
     * @param createProuctSize 생성할 Product의 갯수
     */
    void createProduct(int createProuctSize) {
        for (int i = 0; i < createProuctSize; i++) {
            ProductDto productDto = new ProductDto
                    .Builder(PRODUCT_PRICE, PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();
            productService.createProduct(productDto);
        }
    }

    @AfterEach
    void tearDown() {
        productService.deleteAll();
    }

    @Nested
    @DisplayName("getProductList 메소드에서")
    class Describe_of_readAll_products {

        @Nested
        @DisplayName("Product 객체가 없을 경우")
        class Context_without_product {

            @BeforeEach
            void setUp() {
                productService.deleteAll();
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
            final int createProductSize = 3;

            @BeforeEach
            void setUp() {
                createProduct(createProductSize);
            }

            @AfterEach
            void tearDown() {
                productService.deleteAll();
            }

            @Test
            @DisplayName("Product 객체가 포함된 배열을 리턴한다")
            void it_returns_list_of_product() {
                List<Product> products = productService.getProductList();

                assertThat(products).isNotEmpty();
                assertThat(products).hasSize(createProductSize);
            }
        }
    }

    @Nested
    @DisplayName("getProduct 메소드에서")
    class Describe_of_read_product {
        final int createProductSize = 3;

        @Nested
        @DisplayName("찾는 Id와 동일한 Product가 존재할 경우")
        class Context_with_valid_id {
            final long productId = 2;

            @BeforeEach
            void setUp() {
                createProduct(createProductSize);
            }

            @Test
            @DisplayName("찾은 Product를 반환한다")
            void it_return_product() {
                Product product = productService.getProduct(productId);

                assertThat(product).isNotNull();
                assertThat(product.getId()).isEqualTo(productId);
            }
        }

        @Nested
        @DisplayName("찾는 Id와 동일한 Product가 존재하지 않을 경우")
        class Context_with_invalid_id {
            final long productId = 2;

            @BeforeEach
            void setUp() {
                productService.deleteProduct(productId);
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_productNotFoundException() {
                assertThatThrownBy(() -> productService.getProduct(productId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메소드에서")
    class Describe_of_create_product {

        @Nested
        @DisplayName("상품이 옳바르게 생성 된 경우")
        class Context_with_create_product {
            private ProductDto productDto;

            @BeforeEach
            void setUp() {
                productDto = new ProductDto
                        .Builder(PRODUCT_PRICE, PRODUCT_NAME)
                        .maker(PRODUCT_MAKER)
                        .imageUrl(PRODUCT_IMAGE_URL)
                        .build();
            }

            @Test
            @DisplayName("생성된 Product 객체를 반환한다")
            void it_return_created_product() {
                Product product = productService.createProduct(productDto);

                assertThat(product).isNotNull();
                assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
                assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
            }
        }
    }

}