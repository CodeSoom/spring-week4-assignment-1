package com.codesoom.assignment.domain;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.infra.InMemoryProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
    @DisplayName("상품을 조회할 때")
    class Describe_read_of_product {

        @Nested
        @DisplayName("정상적으로 등록되었을 경우")
        class Context_with_create {

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
            @DisplayName("ID 로 상품을 조회할수 있다")
            void it_return_product() {
                Product product = productRepository.findById(1L)
                        .orElseThrow(() -> new ProductNotFoundException(1L));

                assertThat(product).isNotNull();
            }
        }
    }
}
