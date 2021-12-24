package com.codesoom.controllers;

import com.codesoom.application.ProductService;
import com.codesoom.domain.Product;
import com.codesoom.domain.ProductRepository;
import com.codesoom.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductController 클래스")
class ProductControllerTest {

    private ProductController productController;
    private ProductService productService;

    private static final String PRODUCT_NAME = "고양이 장남감";
    private static final String UPDATE_POSTFIX = "new";
    private static final String PRODUCT_MAKER = "코드숨";
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(10000);
    private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(100);
    private static final String PRODUCT_IMAGE_URL = "test.jpg";

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new ProductRepository();

        productService = new ProductService(productRepository);
        productController = new ProductController(productService);
    }

    @Nested
    @DisplayName("GET 요청은")
    class Describe_get {
        @Nested
        @DisplayName("등록된 Product가 있다면")
        class Context_has_product {
            final int productCount = 3;

            @BeforeEach
            void setUp() {
                for (int i = 0; i < productCount; i++) {
                    productController.create(getProduct(i));
                }
            }

            @Test
            @DisplayName("전체 리스트를 리턴한다.")
            void it_return_list() {
                assertThat(productController.list()).isNotEmpty();
                assertThat(productController.list()).hasSize(productCount);
            }
        }

        @Nested
        @DisplayName("등록된 Product가 없다면")
        class Context_none_product {
            @BeforeEach
            void setUp() {
                List<Product> products = productController.list();
                products.forEach(product -> productController.delete(product.getId()));
            }

            @Test
            @DisplayName("빈 리스트를 리턴한다.")
            void it_return_list() {
                assertThat(productController.list()).isEmpty();
            }
        }

        @Nested
        @DisplayName("등록된 id 값이 주어졌을때")
        class Context_when_product_is_exist {
            Product product;

            @BeforeEach
            void setUp() {
                productController.create(getProduct(1));
            }

            @Test
            @DisplayName("등록된 Product 정보를 리턴한다.")
            void it_return_product() {
                product = productController.view(1L).get();

                assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
                assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
                assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
                assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL);
            }
        }

        @Nested
        @DisplayName("id에 해당하는 Product가 존재하지 않으면")
        class Context_when_product_isnot_exist {

            @Test
            @DisplayName("Product를 찾을 수 없다는 예외를 던진다.")
            void it_throw_ProductNotFoundException() {
                assertThatThrownBy(() -> productController.view(0L)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("PATCH 요청은")
    class Describe_patch {
        Product product;

        @BeforeEach
        void setUp() {
            productController.create(getProduct(1));
        }

        @Nested
        @DisplayName("등록된 id가 주어진다면")
        class Context_when_product_is_exist {
            @Test
            @DisplayName("id에 해당하는 Product 정보를 수정하고 리턴한다.")
            void it_fix_product_return() {
                Product source = new Product();

                source.setName(UPDATE_POSTFIX + PRODUCT_NAME);
                source.setMaker(UPDATE_POSTFIX + PRODUCT_MAKER);
                source.setImageUrl(UPDATE_POSTFIX + PRODUCT_IMAGE_URL);
                source.setPrice(NEW_PRICE);

                productController.update(source, 1L);

                product = productController.view(1L).get();

                assertThat(product.getName()).isEqualTo(UPDATE_POSTFIX + PRODUCT_NAME);
                assertThat(product.getMaker()).isEqualTo(UPDATE_POSTFIX + PRODUCT_MAKER);
                assertThat(product.getImageUrl()).isEqualTo(UPDATE_POSTFIX + PRODUCT_IMAGE_URL);
                assertThat(product.getPrice()).isEqualTo(NEW_PRICE);
            }
        }

        @Nested
        @DisplayName("등록되지않은 id가 주어진다면")
        class Context_when_product_isnot_exist {
            @Test
            @DisplayName("id에 해당하는 Product를 찾을 수 없어 수정할 수 없다고 예외를 던진다.")
            void it_throw_ProductNotFoundException() {
                Product source = new Product();

                source.setName(UPDATE_POSTFIX + PRODUCT_NAME);
                source.setMaker(UPDATE_POSTFIX + PRODUCT_MAKER);
                source.setImageUrl(UPDATE_POSTFIX + PRODUCT_IMAGE_URL);
                source.setPrice(NEW_PRICE);

                assertThatThrownBy(() -> productController.update(source, 0L)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("DELETE 요청은")
    class Describe_delete {

        @BeforeEach
        void setUp() {
            productController.create(getProduct(1));
        }

        @Nested
        @DisplayName("등록된 id가 주어진다면")
        class Context_when_product_is_exist {
            @Test
            @DisplayName("id에 해당하는 Product를 삭제한다.")
            void it_return_products() {
                productController.delete(1L);
                assertThat(productController.list().size()).isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("등록되지않은 id가 주어진다면")
        class Context_when_product_isnot_exist {
            @Test
            @DisplayName("id에 해당하는 Product를 찾을 수 없어 삭제할 수 없다고 예외를 던진다.")
            void it_return_products() {
                assertThatThrownBy(() -> productController.delete(0L)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    private Product getProduct(int number) {
        Product product = new Product();

        product.setName(PRODUCT_NAME + number);
        product.setMaker(PRODUCT_MAKER + number);
        product.setPrice(PRODUCT_PRICE);
        product.setImageUrl(PRODUCT_IMAGE_URL);

        return product;
    }
}
