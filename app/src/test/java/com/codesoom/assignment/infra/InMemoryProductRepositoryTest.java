package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("InMemoryProductRepositoryTest의")
class InMemoryProductRepositoryTest {

    private ProductRepository productRepository;
    final private Long EXISTENT_ID = 1L;
    final private Long NON_EXISTENT_ID = -1L;
    private Product registeredProduct;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
    }

    @BeforeEach
    void setFixture() {
        registeredProduct = generateProduct(EXISTENT_ID);
        productRepository.save(registeredProduct);
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_of_findById {

        @Nested
        @DisplayName("존재하는 상품의 id가 주어지면")
        class Context_of_existent_id {

            @Test
            @DisplayName("상품을 반환한다")
            void it_returns_product() {
                assertThat(productRepository.findById(EXISTENT_ID))
                        .isEqualTo(Optional.of(registeredProduct));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품의 id가 주어지면")
        class Context_of_non_existent_id {

            @Test
            @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productRepository.findById(NON_EXISTENT_ID))
                        .isInstanceOf(EmptyResultDataAccessException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_of_deleteById {

        @Nested
        @DisplayName("존재하는 상품의 id가 주어지면")
        class Context_of_existent_id {

            @Test
            @DisplayName("상품을 제거한다")
            void it_returns_product() {
                productRepository.deleteById(EXISTENT_ID);

                assertThatThrownBy(() -> productRepository.findById(EXISTENT_ID))
                        .isInstanceOf(EmptyResultDataAccessException.class)
                        .withFailMessage("상품이 제거되지 않았다");
            }
        }

        @Nested
        @DisplayName("존재하지 않는 상품의 id가 주어지면")
        class Context_of_non_existent_id {

            @Test
            @DisplayName("상품을 찾을 수 없다는 예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productRepository.deleteById(NON_EXISTENT_ID))
                        .isInstanceOf(EmptyResultDataAccessException.class);
            }
        }
    }

    private Product generateProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("product" + id);
        product.setMaker("maker" + id);
        product.setPrice(100 * id);
        product.setImageUrl("product" + id + ".jpg");
        return product;
    }
}
