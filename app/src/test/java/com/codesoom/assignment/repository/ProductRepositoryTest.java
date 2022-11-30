package com.codesoom.assignment.repository;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@DisplayName("ProductRepository 클래스 테스트")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    private static final String PRODUCT_NAME = "춘식이 고구마 장난감";
    private static final String MAKER        = "카카오";
    private static final Long PRICE          = 39000L;
    private static final String IMAGE_URL    = "http://localhost:8080/snake";

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("id가 존재하지 않을 때")
        class Context_without_id {
            @Test
            @DisplayName("상품을 새로 만들어 리턴한다")
            void it_return_new_product() {
                Product product = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);

                Product toy = repository.save(product);

                assertThat(toy.getProductName()).isEqualTo(PRODUCT_NAME);
                assertThat(repository.findAll().size()).isEqualTo(1);
            }
        }

        @Nested
        @DisplayName("id가 존재할 때")
        class Context_with_id {
            @Test
            @DisplayName("상품을 업데이트한다")
            void it_update_product() {
                //given
                Product product = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);

                repository.save(product);

                assertThat(product.getProductName()).isEqualTo(PRODUCT_NAME);

                //when
                product.setProductName("춘식이 감자 장난감");
                product.setPrice(29000L);

                repository.save(product);

                //then
                assertThat(product.getProductName()).isEqualTo("춘식이 감자 장난감");
            }
        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        @BeforeEach
        void setUp() {
            Product product  = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);
            Product product2 = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);
            Product product3 = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);

            repository.save(product);
            repository.save(product2);
            repository.save(product3);
        }

        @Test
        @DisplayName("모든 Product 리스트를 리턴한다")
        void it_return_products() {
            List<Product> products = repository.findAll();

            assertThat(products.size()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
        @Test
        @DisplayName("해당하는 id의 Product를 리턴한다")
        void it_return_product() {
            //given
            Product product  = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);

            repository.save(product);

            //when
            Optional<Product> findProduct = repository.findById(1L)
                                                        .stream()
                                                        .findFirst();

            //then
            assertThat(findProduct).isPresent();
        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_deleteById {
        @BeforeEach
        void setUp() {
            Product product  = new Product(PRODUCT_NAME, MAKER, PRICE, IMAGE_URL);

            repository.save(product);
        }

        @Test
        @DisplayName("해당하는 id의 Product를 삭제한다")
        void it_remove_product() {
            repository.deleteById(1L);

            assertThat(repository.existsById(1L)).isFalse();
        }
    }
}
