package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("ProductRepository 인터페이스")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        @Nested
        @DisplayName("등록된 Product가 있다면")
        class Context_has_product {
            private final int givenProductCnt = 5;

            @BeforeEach
            void prepare() {
                productRepository.deleteAll();
                IntStream.range(0, givenProductCnt).forEach((i) -> productRepository.save(getProduct()));
            }

            @Test
            @DisplayName("Product의 전체 리스트를 리턴한다.")
            void it_return_products() {
                assertThat(productRepository.findAll()).hasSize(givenProductCnt);
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
        @Nested
        @DisplayName("등록된 Product의 id가 주어진다면")
        class Context_with_id {
            private Long givenProductId;

            @BeforeEach
            void prepare() {
                givenProductId = productRepository.save(getProduct()).getId();
            }

            @Test
            @DisplayName("Product의 정보를 리턴한다.")
            void it_return_products() {
                assertThat(productRepository.findById(givenProductId)).isNotNull();
            }
        }

        @Nested
        @DisplayName("등록되지 않은 Product의 id가 주어진다면")
        class Context_with_invalid_id {
            private Long givenProductInvalidId = 100L;

            @BeforeEach
            void prepare() {
                productRepository.deleteAll();
            }

            @Test
            @DisplayName("Product의 정보를 리턴한다.")
            void it_return_products() {
                assertThat(productRepository.findById(givenProductInvalidId)).isNotNull();
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("등록할 Product가 주어진다면")
        class Context_with_product {
            Product givenProduct = getProduct();

            @Test
            @DisplayName("Product가 저장되고, 리턴됩니다.")
            void it_create_product_return_product() {
                Product product = productRepository.save(givenProduct);

                assertThat(product).isNotNull();
                assertThat(product.getName()).isEqualTo(givenProduct.getName());
            }
        }

        @Nested
        @DisplayName("null이 주어진다면")
        class Context_with_null {
            Product givenNullProduct = null;

            @Test
            @DisplayName("IllegalArgumentException이 리턴됩니다.")
            void it_return_InvalidDataAccessApiUsageException() {
                assertThatThrownBy(() -> productRepository.save(givenNullProduct)).isInstanceOf(InvalidDataAccessApiUsageException.class);
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {
        @Nested
        @DisplayName("등록할 Product가 주어진다면")
        class Context_with_product {
            Product givenProduct;

            @BeforeEach
            void prepare() {
                productRepository.deleteAll();
                givenProduct = productRepository.save(getProduct());
            }

            @Test
            @DisplayName("Product가 삭제되고, 빈값이 리턴됩니다.")
            void it_delete_product_return() {
                productRepository.delete(givenProduct);

                assertThat(productRepository.findAll()).hasSize(0);
            }
        }
    }


    private Product getProduct() {
        return Product.builder()
                .name("테스트 제품")
                .maker("테스트 메이커")
                .price(1000)
                .image("http://test.com/test.jpg")
                .build();
    }
}
