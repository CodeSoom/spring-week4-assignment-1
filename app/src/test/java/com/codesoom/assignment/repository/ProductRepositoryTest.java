package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DisplayName("ProductRepository JPA 테스트")
class ProductRepositoryTest {

    private final String name = "My toy";
    private final String maker = "toy shop";
    private final int price = 15000;
    private final String imageUrl = "toy.jpg";

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void clear() {
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        private final Product product = new Product(name, maker, price, imageUrl);

        @Test
        @DisplayName("장난감을 저장하고 반환한다")
        void it_returns_saved_product() {
            Product savedProduct = productRepository.save(product);

            assertThat(savedProduct.getId()).isNotNull();
            assertThat(savedProduct.getName()).isEqualTo(name);
            assertThat(savedProduct.getMaker()).isEqualTo(maker);
            assertThat(savedProduct.getPrice()).isEqualTo(price);
            assertThat(savedProduct.getImageUrl()).isEqualTo(imageUrl);
        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {

        @Nested
        @DisplayName("만약 저장된 장난감이 없으면")
        class Context_without_product {

            @Test
            @DisplayName("빈 목록을 반환한다")
            void it_returns_empty_list() {
                List<Product> products = productRepository.findAll();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 저장된 장난감이 있으면")
        class Context_with_product {
            private final int productSize = 3;

            @BeforeEach
            void setUp() {
                for (int i = 0; i < productSize; i++) {
                    Product product = new Product(name, maker, price, imageUrl);
                    productRepository.save(product);
                }
            }

            @Test
            @DisplayName("모든 장난감을 반환한다")
            void it_returns_products() {
                List<Product> products = productRepository.findAll();

                assertThat(products).hasSize(productSize);
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {

        @Nested
        @DisplayName("조회 가능한 id가 주어지면")
        class Context_with_valid_id {
            private Long id;

            @BeforeEach
            void setUp() {
                Product source = new Product(name, maker, price, imageUrl);
                Product savedProduct = productRepository.save(source);
                id = savedProduct.getId();
            }

            @Test
            @DisplayName("장난감을 찾아 반환한다")
            void it_returns_product() {
                Optional<Product> product = productRepository.findById(id);

                assertThat(product).isNotEmpty();
                assertThat(product.get().getId()).isEqualTo(id);
                assertThat(product.get().getName()).isEqualTo(name);
            }
        }

        @Nested
        @DisplayName("조회할 수 없는 id가 주어지면")
        class Context_with_invalid_id {
            private Long invalidId;

            @BeforeEach
            void setUp() {
                Product product = productRepository.save(new Product(name, maker, price, imageUrl));
                invalidId = product.getId();

                productRepository.delete(product);
            }

            @Test
            @DisplayName("빈 값을 반환한다")
            void it_returns_product() {
                Optional<Product> product = productRepository.findById(invalidId);

                assertThat(product).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_deleteById {

        @Nested
        @DisplayName("삭제 가능한 id가 주어지면")
        class Context_with_valid_id {
            private Long id;

            @BeforeEach
            void setUp() {
                Product product = new Product(name, maker, price, imageUrl);
                Product savedProduct = productRepository.save(product);
                id = savedProduct.getId();
            }

            @Test
            @DisplayName("삭제를 수행한다")
            void it_removes_product() {
                productRepository.deleteById(id);
            }
        }

        @Nested
        @DisplayName("삭제 불가능한 id가 주어지면")
        class Context_with_invalid_id {
            private Long invalidId;

            @BeforeEach
            void setUp() {
                Product product = productRepository.save(new Product(name, maker, price, imageUrl));
                invalidId = product.getId();

                productRepository.delete(product);
            }

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productRepository.deleteById(invalidId))
                        .isInstanceOf(EmptyResultDataAccessException.class);
            }
        }
    }

    @Nested
    @DisplayName("removeById 메소드는")
    class Describe_removeById {

        @Nested
        @DisplayName("id가 주어지면")
        class Context_with_id {
            private Long id;
            private Long invalidId;

            @BeforeEach
            void setUp() {
                Product product1 = productRepository.save(new Product(name, maker, price, imageUrl));
                id = product1.getId();

                Product product2 = productRepository.save(new Product(name, maker, price, imageUrl));
                invalidId = product2.getId();

                productRepository.delete(product2);
            }

            @Test
            @DisplayName("삭제를 수행하고 영향을 받은 행 수를 반환한다")
            void it_returns_affected_row_count() {
                int affectedRowCountById = productRepository.removeById(id);
                int affectedRowCountByInvalidId = productRepository.removeById(invalidId);

                assertThat(affectedRowCountById).isEqualTo(1);
                assertThat(affectedRowCountByInvalidId).isEqualTo(0);
            }
        }
    }
}
