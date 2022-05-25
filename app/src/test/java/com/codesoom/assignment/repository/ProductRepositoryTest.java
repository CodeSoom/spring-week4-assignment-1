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
    void setUp() {
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
        @DisplayName("상품이 저장돼 있다면")
        class Context_with_products {

            @BeforeEach
            void setUp(){
                productRepository.deleteAll();
                Product product1 = createProduct("p1");
                Product product2 = createProduct("p2");
                productRepository.save(product1);
                productRepository.save(product2);
            }

            @Test
            @DisplayName("상품 리스트를 리턴한다")
            void it_returns_product_list() {
                List<Product> products = productRepository.findAll();
                assertThat(products).hasSize(2);
            }
        }

        @Nested
        @DisplayName("상품이 없다면")
        class Context_without_product {
            @BeforeEach
            void setUp() {
                productRepository.deleteAll();
            }

            @Test
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
        @DisplayName("저장된 id가 주어진다면")
        class Context_with_id {

            @Test
            @DisplayName("상품을 리턴한다")
            void it_returns_product() {
                Long id = product.getId();
                Optional<Product> product = productRepository.findById(id);
                assertThat(product.isPresent()).isTrue();
            }
        }

        @Nested
        @DisplayName("저장되지 않은 id가 주어진다면")
        class Context_with_not_stored_id {
            private final Long NOT_EXIST_ID = 100L;

            @Test
            @DisplayName("빈 Optional 상품을 리턴한다")
            void it_returns_product() {
                Optional<Product> product = productRepository.findById(NOT_EXIST_ID);
                assertThat(product.isEmpty()).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("save")
    class Describe_save {

        @DisplayName("상품을 저장하고 상품을 리턴한다")
        @Test
        void it_returns_product() {
            Product newProduct = createProduct("new product");
            Product savedProduct = productRepository.save(newProduct);
            assertThat(savedProduct).isEqualTo(newProduct);
        }
    }

    @Nested
    @DisplayName("delete")
    class Describe_delete {

        @Nested
        @DisplayName("저장된 상품이 주어진다면")
        class Context_with_product {

            @Test
            @DisplayName("상품을 삭제한다")
            void it_deletes_product() {
                productRepository.delete(product);
                assertThat(productRepository.findAll()).hasSize(0);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 상품이 주어진다면")
        class Context_with_not_stored_product {

            @Test
            @DisplayName("상품을 삭제하지 않는다.")
            void it_returns_product() {
                Product notStoredProduct = new Product();
                productRepository.delete(notStoredProduct);
                assertThat(productRepository.findAll()).hasSize(1);
            }
        }
    }

    private Product createProduct(String name) {
        return Product.builder()
                .name(name)
                .price(1)
                .imageUrl("www")
                .maker("maker")
                .build();
    }
}
