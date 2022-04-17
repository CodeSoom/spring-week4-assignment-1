package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("ProductReadServiceImpl 클래스")
public class ProductReadServiceTest extends ServiceTest {

    private ProductReadServiceImpl service;

    @Autowired
    private ProductRepository repository;

    private Product SAVED_PRODUCT;

    @BeforeEach
    void setup() {
        this.service = new ProductReadServiceImpl(repository);
        final Product product = new Product("쥐돌이", "캣이즈락스타", BigDecimal.valueOf(4000), "");
        SAVED_PRODUCT = repository.save(product);
    }

    @AfterEach
    void cleanup() {
        repository.deleteAll();
    }

    @DisplayName("findAll 메서드는")
    @Nested
    class Describe_find_all {
        @DisplayName("저장된 모든 상품을 반환한다.")
        @Test
        void will_return_all_products() {
            assertThat(service.findAll()).isNotEmpty();
        }
    }

    @DisplayName("findById 메서드는")
    @Nested
    class Describe_find_by_id {

        @DisplayName("존재하는 상품 id가 주어진다면")
        @Nested
        class Context_with_exist_id {
            @DisplayName("성공적으로 상품을 조회한다.")
            @Test
            void will_return_found_product() {
                final Product product = service.findById(SAVED_PRODUCT.getId());

                assertThat(product).isNotNull();
                assertThat(product.getId()).isEqualTo(SAVED_PRODUCT.getId());
            }
        }

        @DisplayName("존재하지 않는 상품 id가 주어진다면")
        @Nested
        class Context_with_not_exist_id {

            private final Long NOT_EXIST_ID = 100L;

            @BeforeEach
            void setup() {
                if (repository.existsById(NOT_EXIST_ID)) {
                    repository.deleteById(NOT_EXIST_ID);
                }
            }

            @DisplayName("예외를 던진다.")
            @Test
            void will_throw_exception() {
                assertThatThrownBy(() -> service.findById(NOT_EXIST_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

}
