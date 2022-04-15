package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductNotFoundException;
import com.codesoom.assignment.application.ProductReadServiceImpl;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
public class ProductReadControllerTest {

    private ProductReadController controller;

    @Autowired
    private ProductReadServiceImpl service;

    @Autowired
    private ProductRepository repository;

    private static final Product SAVED_PRODUCT = Product.builder()
            .name("키위새").maker("유령회사").price(BigDecimal.valueOf(3000)).image("")
            .build();

    @BeforeEach
    void setup() {
        this.controller = new ProductReadController(service);
        repository.deleteAll();
    }

    @DisplayName("findAll 메서드는")
    @Nested
    class Describe_find_all {
        @BeforeEach
        void setup() {
            repository.save(SAVED_PRODUCT);
        }

        @DisplayName("전체 상품을 성공적으로 조회한다.")
        @Test
        void findAllTest() {
            assertThat(controller.getProducts()).isNotEmpty();
        }
    }

    @DisplayName("findById 메서드는")
    @Nested
    class Describe_find_by_id {

        @DisplayName("존재하는 id로 조회 요청이 오면")
        @Nested
        class Context_with_exist_id {

            private Long EXIST_ID;

            @BeforeEach
            void setup() {
                this.EXIST_ID = repository.save(SAVED_PRODUCT).getId();
            }

            @DisplayName("해당 상품을 반환한다.")
            @Test
            void will_return_found_product() {
                assertThat(controller.getProductDetail(EXIST_ID)).isNotNull();
            }
        }

        @DisplayName("존재하지 않는 id로 조회 요청이 오면")
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
            void will_throw_not_found_exception() {
                assertThatThrownBy(() -> controller.getProductDetail(NOT_EXIST_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

}
