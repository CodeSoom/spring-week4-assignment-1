package com.codesoom.assignment.controllers;


import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.models.Product;
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
@DisplayName("ProductController 에서")
class ProductControllerTest {
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "메이커1";
    private static final Integer PRODUCT_PRICE = 100000;
    private static final String PRODUCT_IMAGE_URL = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9941A1385B99240D2E";

    private static final String UPDATE_PRODUCT_NAME = "상품1000";
    private static final Integer UPDATE_PRODUCT_PRICE = 100;

    @Autowired
    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productService);
        productService.deleteAll();
    }

    /**
     * 여러개의 Product 를 생성해 등록합니다.
     * @param createProuctSize 생성할 Product의 갯수
     */
    void createProducts(int createProuctSize) {
        for (int i = 0; i < createProuctSize; i++) {
            ProductDto productDto = new ProductDto
                    .Builder(PRODUCT_PRICE, PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();
            productService.createProduct(productDto);
        }
    }

    /**
     * 하나의 Product 를 생성해 등록합니다.
     * @return 생성한 Product를 리턴
     */
    private Product createProduct() {
        ProductDto productDto = new ProductDto
                .Builder(PRODUCT_PRICE, PRODUCT_NAME)
                .maker(PRODUCT_MAKER)
                .imageUrl(PRODUCT_IMAGE_URL)
                .build();
        return productService.createProduct(productDto);
    }

    @Nested
    @DisplayName("list 메소드는")
    class Describe_of_list {
        final int createProductsize = 3;

        @BeforeEach
        void setUp() {
            createProducts(createProductsize);
        }

        @Nested
        @DisplayName("Product 객체가 없을 경우")
        class Context_with_empty_list {

            @BeforeEach
            void setUp() {
                productService.deleteAll();
            }

            @Test
            @DisplayName("빈 리스트를 반환한다")
            void it_return_empty_list() {
                List<Product> products = productController.list();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("Product 객체가 있을 경우")
        class Context_with_product_list {

            @Test
            @DisplayName("Product 객체가 포함된 배열을 반환한다")
            void it_returns_product_list() {
                List<Product> products = productController.list();
                assertThat(products).isNotEmpty();
            }
        }

    }

    @Nested
    @DisplayName("detail 메소드는")
    class Describe_of_detail {
        private Product product;

        @BeforeEach
        void setUp() {
            product = createProduct();
        }

        @Nested
        @DisplayName("제품이 있을 경우")
        class Context_with_product {
            private long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
            }

            @Test
            @DisplayName("제품을 반환한다")
            void it_return_product() {
                Product found = productController.detail(productId);

                assertThat(found).isNotNull();
            }
        }

        @Nested
        @DisplayName("제품이 없을 경우")
        class Context_with_invalid_id {
            private long productId;

            @BeforeEach
            void setUp() {
                productId = product.getId();
                productService.deleteProduct(productId);
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_productNotFoundException() {
                assertThatThrownBy(() -> productController.detail(productId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_of_create {

        @Nested
        @DisplayName("제품이 생성되었다면")
        class Context_with_created {
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
            @DisplayName("생성된 제품을 반환한다")
            void it_return_created_product() {
                Product product = productController.create(productDto);

                assertThat(product).isNotNull();
                assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
                assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_of_update {
        private Product product;

        @BeforeEach
        void setUp() {
            product = createProduct();
        }

        @Nested
        @DisplayName("Id 에 맞는 제품을 업데이트 했을 경우")
        class Context_with_valid_id {
            private final long productId = product.getId();
            private final ProductDto productDto = new ProductDto
                    .Builder(UPDATE_PRODUCT_PRICE, UPDATE_PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();

            @Test
            @DisplayName("업데이트 된 제품을 반환한다")
            void it_return_updated_product() {
                Product updatedProduct = productController.update(productId, productDto);

                assertThat(updatedProduct).isNotNull();
                assertThat(updatedProduct.getPrice()).isEqualTo(UPDATE_PRODUCT_PRICE);
                assertThat(updatedProduct.getName()).isEqualTo(UPDATE_PRODUCT_NAME);
            }
        }
    }

}