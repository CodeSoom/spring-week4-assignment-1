package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DisplayName("ProductServiceImpl 클래스")
class ProductServiceImplTest {

    private final String name = "My toy";
    private final String maker = "toy shop";
    private final int price = 15000;
    private final String imageUrl = "toy.jpg";

    @Autowired
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository);
    }

    @AfterEach
    void clear() {
        productRepository.deleteAll();
    }

    Product getProduct() {
        return new Product(name, maker, price, imageUrl);
    }

    @Nested
    @DisplayName("get 메소드는")
    class Describe_get {

        @Nested
        @DisplayName("만약 조회 가능한 id가 주어지면")
        class Context_with_valid_id {
            private Long id;

            @BeforeEach
            void setUp() {
                Product product = productService.create(getProduct());
                id = product.getId();
            }

            @Test
            @DisplayName("장난감을 반환한다")
            void it_returns_product() {
                Product product = productService.get(id);

                assertThat(product).isNotNull();
                assertThat(product.getId()).isEqualTo(id);
            }
        }

        @Nested
        @DisplayName("만약 조회 불가능한 id가 주어지면")
        class Context_with_invalid_id {
            private final Long invalidId = 1000L;

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.get(invalidId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("getAll 메소드는")
    class Describe_getAll {

        @Nested
        @DisplayName("만약 저장된 장난감이 없으면")
        class Context_with_no_products {

            @Test
            @DisplayName("빈 목록을 반환한다")
            void it_returns_empty_list() {
                List<Product> products = productService.getAll();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 저장된 장난감이 있으면")
        class Context_with_products {
            private final int productSize = 3;

            @BeforeEach
            void setUp() {
                for (int i = 0; i < productSize; i++) {
                    productService.create(getProduct());
                }
            }

            @Test
            @DisplayName("모든 장난감을 반환한다")
            void it_returns_all_products() {
                List<Product> products = productService.getAll();

                assertThat(products).hasSize(productSize).element(0).isNotNull();
            }
        }
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {

        @Test
        @DisplayName("장난감을 생성해 반환한다")
        void it_returns_new_product() {
            Product createdProduct = productService.create(getProduct());

            assertThat(createdProduct).isNotNull();
            assertThat(createdProduct.getId()).isNotNull();
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("만약 수정이 가능한 경우")
        class Context_with_valid_id {
            private Long id;
            private Product source;

            @BeforeEach
            void setUp() {
                Product product = productService.create(getProduct());
                id = product.getId();

                final String updatePrefix = "updated_";
                final int updatedPrice = 20000;

                source = new Product(updatePrefix + name,
                        updatePrefix + maker,
                        updatedPrice,
                        updatePrefix + imageUrl);
            }

            @Test
            @DisplayName("수정된 장난감을 반환한다")
            void it_returns_updated_task() {
                Product updatedProduct = productService.update(id, source);

                assertThat(updatedProduct).isNotNull();
                assertThat(updatedProduct.getId()).isEqualTo(id);
                assertThat(updatedProduct.getName()).isEqualTo(source.getName());
            }
        }

        @Nested
        @DisplayName("만약 수정이 불가능한 경우")
        class Context_with_invalid_id {
            private final Long invalidId = 1000L;

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.update(invalidId, getProduct()))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

        @Nested
        @DisplayName("만약 삭제 가능한 id가 주어지면")
        class Context_with_valid_id {
            private Long id;

            @BeforeEach
            void setUp() {
                Product product = productService.create(getProduct());
                id = product.getId();
            }

            @Test
            @DisplayName("삭제를 수행한다")
            void it_removes_product() {
                productService.deleteById(id);
            }
        }

        @Nested
        @DisplayName("만약 삭제 불가능한 id가 주어지면")
        class Context_with_invalid_id {
            private final Long invalidId = 1000L;

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productService.deleteById(invalidId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
