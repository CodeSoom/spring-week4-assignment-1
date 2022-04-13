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


@DisplayName("ProductDeleteServiceImpl 클래스")
public class ProductDeleteServiceTest extends ServiceTest {

    private ProductDeleteServiceImpl service;

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setup() {
        this.service = new ProductDeleteServiceImpl(repository);
    }

    @DisplayName("deleteById 메서드는")
    @Nested
    class Describe_delete_by_id {

        private final Product SAVED_PRODUCT = Product.builder()
                .name("키위새").maker("유령회사").price(BigDecimal.valueOf(3000)).image("")
                .build();

        @BeforeEach
        void setup() {
            repository.save(SAVED_PRODUCT);
        }

        @AfterEach
        void cleanup() {
            repository.deleteAll();
        }

        @DisplayName("존재하는 상품 id가 주어진다면")
        @Nested
        class Context_with_exist_id {
            @DisplayName("성공적으로 상품을 삭제한다.")
            @Test
            void will_delete_product() {
                service.deleteById(SAVED_PRODUCT.getId());
                assertThat(repository.findById(SAVED_PRODUCT.getId())).isEmpty();
            }
        }

        @DisplayName("존재하지 않는 상품 id가 주어진다면")
        @Nested
        class Context_with_not_exist_id {
            @DisplayName("예외를 던진다.")
            @Test
            void will_throw_exception() {
                assertThatThrownBy(() -> service.deleteById(100L))
                    .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

}
