package com.codesoom.assignment.domain;

import com.codesoom.assignment.common.ProductFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("ProductRepository 클래스")
class ProductRepositoryTest {

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        @Nested
        @DisplayName("데이터가 존재한다면")
        class Context_with_existed_data extends JpaTest {
            private final List<Product> givenProducts = new ArrayList<>();

            @BeforeEach
            void prepare() {
                givenProducts.add(getProductRepository().save(ProductFactory.createProduct(1L)));
                givenProducts.add(getProductRepository().save(ProductFactory.createProduct(2L)));
            }

            @Test
            @DisplayName("모든 장난감을 리턴한다")
            void it_returns_all_toy() {
                final List<Product> actualProducts = getProductRepository().findAll();

                assertThat(actualProducts).hasSize(givenProducts.size());
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_existed_id extends JpaTest {
            private Product givenProduct;

            @BeforeEach
            void prepare() {
                givenProduct = getProductRepository().save(ProductFactory.createProduct());
            }

            @Test
            @DisplayName("고양이 장난감을 찾아서 리턴한다")
            void it_returns_searched_cat_toy() {
                final Product actualProduct = getProductRepository().findById(givenProduct.getId()).orElse(null);

                assertThat(actualProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(actualProduct.getId()).isEqualTo(givenProduct.getId());
                assertThat(actualProduct.getPrice()).isEqualTo(givenProduct.getPrice());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 ID가 주어지면")
        class Context_with_non_existed_id extends JpaTest {
            @Test
            @DisplayName("Optional.empty()를 리턴한다")
            void it_returns_optional_empty() {
                assertThat(getProductRepository().findById(100L)).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("새로운 고양이 장난감이 주어지면")
        class Context_with_new_cat_toy extends JpaTest {
            private final Product givenProduct = ProductFactory.createProduct();

            @Test
            @DisplayName("DB에 등록하고 등록된 장난감을 리턴한다")
            void it_returns_registered_cat_toy() {
                final Product actualProduct = getProductRepository().save(givenProduct);

                assertThat(actualProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(actualProduct.getPrice()).isEqualTo(givenProduct.getPrice());
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_existed_id extends JpaTest {
            private Product givenProduct;

            @BeforeEach
            void prepare() {
                givenProduct = getProductRepository().save(ProductFactory.createProduct());
            }

            @Test
            @DisplayName("DB에서 삭제한다")
            void it_returns_nothing() {
                getProductRepository().delete(givenProduct);

                final Optional<Product> actualProduct = getProductRepository().findById(givenProduct.getId());

                assertThat(actualProduct).isEmpty();
            }
        }

        @Nested
        @DisplayName("유효하지 않은 ID가 주어지면")
        class Context_with_non_existed_id extends JpaTest {
            private final Product givenProduct = ProductFactory.createProduct(100L);

            @Test
            @DisplayName("삭제할 대상이 없기때문에 아무것도 삭제하지않는다")
            void it_returns_nothing() {
                final Optional<Product> beforeProduct = getProductRepository().findById(givenProduct.getId());

                getProductRepository().delete(givenProduct);

                final Optional<Product> afterProduct = getProductRepository().findById(givenProduct.getId());

                assertThat(beforeProduct).isEmpty();
                assertThat(afterProduct).isEmpty();
            }
        }
    }

    @DataJpaTest
    class JpaTest {
        @Autowired
        ProductRepository productRepository;

        public ProductRepository getProductRepository() {
            return productRepository;
        }
    }
}
