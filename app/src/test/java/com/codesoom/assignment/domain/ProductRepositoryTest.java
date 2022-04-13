package com.codesoom.assignment.domain;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.entity.ProductRepository;
import com.codesoom.assignment.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductRepository 에서")
@DataJpaTest
class ProductRepositoryTest {
    private static final String PRODUCT_NAME = "상품1";
    private static final String PRODUCT_MAKER = "메이커1";
    private static final Integer PRODUCT_PRICE = 100000;
    private static final String PRODUCT_IMAGE_URL = "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F9941A1385B99240D2E";

    private static final String UPDATE_PRODUCT_NAME = "상품1000";

    @Autowired
    private ProductRepository productRepository;

    /**
     * 여러개의 Product 를 생성해 등록합니다.
     * @param createProuctSize 생성할 Product의 갯수
     */
    private void createProduct(int createProuctSize) {
        for (int i = 0; i < createProuctSize; i++) {
            Product product = new Product
                    .Builder(PRODUCT_PRICE, PRODUCT_NAME)
                    .maker(PRODUCT_MAKER)
                    .imageUrl(PRODUCT_IMAGE_URL)
                    .build();
            productRepository.save(product);
        }
    }

    @Nested
    @DisplayName("findAll 메소드를 호출할 때")
    class Describe_readAll_of_product {
        final int createProductSize = 4;

        @BeforeEach
        void setUp() {
            createProduct(createProductSize);
        }

        @AfterEach
        void tearDown() {
            productRepository.deleteAll();
        }

        @Nested
        @DisplayName("상품이 존재할 경우")
        class Context_with_products {

            @Test
            @DisplayName("상품이 존재하는 배열을 리턴합니다")
            void it_returns_list_of_product() {
                List<Product> products = productRepository.findAll();

                assertThat(products).isNotEmpty();
                assertThat(products).hasSize(createProductSize);
            }
        }

        @Nested
        @DisplayName("상품이 존재하지 않을 경우")
        class Context_with_empty_list {

            @BeforeEach
            void setUp() {
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
        @DisplayName("Id 와 동일한 상품이 존재하지 않을 경우")
        class Context_without_product {
            final int createProductSize = 3;
            final long productId = 1L;

            @BeforeEach
            void setUp() {
                createProduct(createProductSize);
                productRepository.deleteById(productId);
            }

            @Test
            @DisplayName("비어있는 객체가 반환된다.")
            void it_throw_productNotFoundException() {
                Optional<Product> product = productRepository.findById(productId);

                assertThat(product).isEmpty();
            }
        }

        @Nested
        @DisplayName("Id 와 동일한 상품이 존재할 경우")
        class Context_with_product {
            final int createProductSize = 5;
            final long productId = 1L;

            @BeforeEach
            void setUp() {
                createProduct(createProductSize);
            }

            @Test
            @DisplayName("상품을 반환한다")
            void it_return_product() {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException(productId));
                assertThat(product).isNotNull();
            }
        }
    }

    @Nested
    @DisplayName("save 메소드를 호출할 때")
    class Describe_save_of_product {

        @Nested
        @DisplayName("상품을 생성하는 경우")
        class Context_with_create_of_product {
            private Product createdProduct;

            @BeforeEach
            void setUp() {
                Product product = new Product
                        .Builder(PRODUCT_PRICE, PRODUCT_NAME)
                        .maker(PRODUCT_MAKER)
                        .imageUrl(PRODUCT_IMAGE_URL)
                        .build();
                createdProduct = productRepository.save(product);
            }

            @Test
            @DisplayName("생성한 상품을 반환합니다")
            void it_return_new_product() {
                assertThat(createdProduct).isNotNull();
                assertThat(createdProduct.getPrice()).isEqualTo(PRODUCT_PRICE);
            }
        }

        @Nested
        @DisplayName("상품을 수정하는 경우")
        class Context_with_update_of_product {
            final int createProductSize = 2;
            final long productId = 1L;
            private Product updatedProduct;

            @BeforeEach
            void setUp() {
                createProduct(createProductSize);
                Optional<Product> product = productRepository.findById(productId);
                if (product.isPresent()) {
                    product.get().setName(UPDATE_PRODUCT_NAME);
                    updatedProduct = productRepository.save(product.get());
                }
            }

            @Test
            @DisplayName("수정된 상품을 반환합니다")
            void it_return_updated_product() {
                assertThat(updatedProduct).isNotNull();
                assertThat(updatedProduct.getId()).isEqualTo(productId);
                assertThat(updatedProduct.getName()).isEqualTo(UPDATE_PRODUCT_NAME);
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드를 호출할 때")
    class Describe_delete_of_product {

        @Nested
        @DisplayName("삭제할 상품이 있다면")
        class Context_with_delete_of_product {
            final int createProductSize = 3;
            final long productId = 2L;

            @BeforeEach
            void setUp() {
                createProduct(createProductSize);
            }

            @Test
            @DisplayName("삭제를 진행한다")
            void it_delete_product() {
                Optional<Product> product = productRepository.findById(productId);
                product.ifPresent(value -> productRepository.delete(value));

                Optional<Product> found = productRepository.findById(productId);
                assertThat(found).isEmpty();
            }
        }
    }

}
