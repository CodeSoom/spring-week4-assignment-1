package com.codesoom.assignment.domain;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.infra.InMemoryProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductRepository 에서")
class ProductRepositoryTest {
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "메이커1";
    private static final Integer PRODUCT_PRICE = 100000;
    private static final String PRODUCT_IMAGE_URL = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9941A1385B99240D2E";

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        this.productRepository = new InMemoryProductRepository();
    }

    @Nested
    @DisplayName("findAll 메소드를 호출할 때")
    class Describe_readAll_of_product {

        @Nested
        @DisplayName("상품이 존재할 경우")
        class Context_with_products {
            final long createProductSize = 4L;

            @BeforeEach
            void setUp() {
                for (int i = 0; i < createProductSize; i++) {
                    Product product = new Product();
                    product.setName(PRODUCT_NAME);
                    product.setMaker(PRODUCT_MAKER);
                    product.setPrice(PRODUCT_PRICE);
                    product.setImageUrl(PRODUCT_IMAGE_URL);
                    productRepository.save(product);
                }
            }

            @AfterEach
            void tearDown() {
                productRepository.deleteAll();
            }

            @Test
            @DisplayName("상품이 존재하는 배열을 리턴합니다")
            void it_returns_list_of_product() {
                List<Product> products = productRepository.findAll();

                assertThat(products).isNotEmpty();
                assertThat(products).hasSize((int) createProductSize);
            }
        }

        @Nested
        @DisplayName("상품이 존재하지 않을 경우")
        class Context_with_empty_list {
            final long createProductSize = 3L;

            @BeforeEach
            void setUp() {
                for (int i = 0; i < createProductSize; i++) {
                    Product product = new Product();
                    product.setName(PRODUCT_NAME);
                    product.setMaker(PRODUCT_MAKER);
                    product.setPrice(PRODUCT_PRICE);
                    product.setImageUrl(PRODUCT_IMAGE_URL);
                    productRepository.save(product);
                }
                productRepository.deleteAll();
            }


            @Test
            @DisplayName("빈 배열을 반환합니다")
            void it_return_empty_list() {
                List<Product> products = productRepository.findAll();

                assertThat(products).isEmpty();
                assertThat(products).hasSize(0);
            }
        }
    }

   @Nested
    @DisplayName("findById 메소드를 호출할 때")
    class Describe_read_of_product {

        @Nested
        @DisplayName("Id 에 맞는 상품이 존재할 경우")
        class Context_with_product {

            @BeforeEach
            void setUp() {
                Product product = new Product();
                product.setName(PRODUCT_NAME);
                product.setMaker(PRODUCT_MAKER);
                product.setPrice(PRODUCT_PRICE);
                product.setImageUrl(PRODUCT_IMAGE_URL);
                productRepository.save(product);
            }

            @AfterEach
            void tearDown() {
                productRepository.deleteAll();
            }

            @Test
            @DisplayName("상품을 반환한다")
            void it_return_product() {
                Product product = productRepository.findById(1L)
                        .orElseThrow(() -> new ProductNotFoundException(1L));

                assertThat(product).isNotNull();
            }
        }
        @Nested
        @DisplayName("Id 에 맞는 상품이 존재하지 않을 경우")
        class Context_without_product {
            final long createProductSize = 3L;

            @BeforeEach
            void setUp() {
                for (long i = 0; i < createProductSize; i++) {
                    Product product = new Product();
                    product.setName(PRODUCT_NAME);
                    product.setMaker(PRODUCT_MAKER);
                    product.setPrice(PRODUCT_PRICE);
                    product.setImageUrl(PRODUCT_IMAGE_URL);
                    productRepository.save(product);
                }
                productRepository.deleteAll();
            }

            @Test
            @DisplayName("ProductNotFoundException을 던진다")
            void it_throw_productNotFoundException() {
                assertThatThrownBy(() -> productRepository.findById(1L))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
