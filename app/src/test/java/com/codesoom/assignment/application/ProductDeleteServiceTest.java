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


@DisplayName("ProductSafeDeleteService 클래스")
public class ProductDeleteServiceTest extends ServiceTest {

    private ProductSafeDeleteService service;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setup() {
        this.service = new ProductSafeDeleteService(repository);
    }

    @DisplayName("deleteById 메서드는")
    @Nested
    class Describe_delete_by_id {

        @DisplayName("존재하는 상품 id가 주어진다면")
        @Nested
        class Context_with_exist_id {

            private Long EXIST_ID;

            @BeforeEach
            void setup() {
                final Product product
                        = new Product("쥐돌이", "캣이즈락스타", BigDecimal.valueOf(4000), "");
                this.EXIST_ID = repository.save(product).getId();
            }

            @AfterEach
            void cleanup() {
                repository.deleteAll();
            }

            @DisplayName("성공적으로 상품을 삭제한다.")
            @Test
            void will_delete_product() {
                service.deleteById(EXIST_ID);
                assertThat(repository.findById(EXIST_ID)).isEmpty();
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
                assertThatThrownBy(() -> service.deleteById(NOT_EXIST_ID))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

}
