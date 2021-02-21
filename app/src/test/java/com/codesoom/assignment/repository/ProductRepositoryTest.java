package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
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

@DisplayName("ProductRepository 클래스")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void prepareProduct() {
        product = createProduct("product");

        productRepository.save(product);
    }

    @AfterEach
    void clear() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("findAll")
    class Describe_findAll {
        @Nested
        @DisplayName("저장된 상품이 여러개 있다면")
        class Context_with_products {
            @BeforeEach
            void prepareProducts() {
                productRepository.deleteAll();

                Product product1 = createProduct("product1");
                Product product2 = createProduct("product2");

                productRepository.save(product1);
                productRepository.save(product2);
            }

            @Test
            @DisplayName("모든 상품 목록을 리턴한다.")
            void it_returns_all_product_list() {
                List<Product> products = productRepository.findAll();

                assertThat(products).hasSize(2);
            }
        }

        @Nested
        @DisplayName("저장된 상품이 없다면")
        class Context_without_products {
            @BeforeEach
            void prepareProducts() {
                productRepository.deleteAll();
            }

            @Test
            @DisplayName("비어있는 목록을 리턴한다.")
            void it_returns_empty_product_list() {
                List<Product> products = productRepository.findAll();

                assertThat(products).hasSize(0);
            }
        }
    }

    @Nested
    @DisplayName("findById")
    class Describe_findById {
        @Nested
        @DisplayName("존재하는 상품 id가 주어진다면")
        class Context_with_an_existing_product_id {
            private Long existingId;

            @BeforeEach
            void prepareExistingId() {
                existingId = product.getId();
            }

            @Test
            @DisplayName("값이 존재하는 optional 상품을 리턴한다.")
            void it_returns_optional_product_with_value() {
                Optional<Product> foundProduct = productRepository.findById(existingId);

                assertThat(foundProduct.isPresent()).isTrue();
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품 id가 주어진다면")
        class Context_with_not_existing_product_id {
            private final Long notExistingId = 100L;

            @BeforeEach
            void prepareNotExistingId() {
                productRepository.findById(notExistingId)
                        .ifPresent(product -> productRepository.delete(product));
            }

            @Test
            @DisplayName("비어있는 optional 상품을 리턴한다.")
            void it_returns_optional_product_with_value() {
                Optional<Product> foundProduct = productRepository.findById(notExistingId);

                assertThat(foundProduct.isEmpty()).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("save")
    class Describe_save {
        @DisplayName("상품을 저장하고 저장된 상품을 리턴한다.")
        @Test
        void it_saves_a_product_and_returns_the_saved_product() {
            Product newProduct = createProduct("new product");

            Product savedProduct = productRepository.save(newProduct);

            assertThat(savedProduct).isEqualTo(newProduct);
        }
    }

    @Nested
    @DisplayName("delete")
    class Describe_delete {
        @Nested
        @DisplayName("존재하는 상품이 주어진다면")
        class Context_with_an_existing_product {
            @Test
            @DisplayName("상품을 삭제한다.")
            void it_deletes_the_product() {
                productRepository.delete(product);

                assertThat(productRepository.findAll()).hasSize(0);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품이 주어진다면")
        class Context_with_not_existing_product {
            @Test
            @DisplayName("상품을 삭제하지 않는다.")
            void it_returns_optional_product_with_value() {
                Product notExistingProduct = createProduct("not existing product");

                productRepository.delete(notExistingProduct);

                assertThat(productRepository.findAll()).hasSize(1);
            }
        }
    }

    private Product createProduct(String name) {
        return Product.builder()
                .name(name)
                .maker("장난감 메이커")
                .price(10000)
                .imageUrl("url")
                .build();
    }

}
