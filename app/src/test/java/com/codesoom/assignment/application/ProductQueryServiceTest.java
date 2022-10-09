package com.codesoom.assignment.application;

import com.codesoom.assignment.controller.dto.ProductRequestDto;
import com.codesoom.assignment.controller.dto.ProductResponseDto;
import com.codesoom.assignment.controller.dto.ProductUpdateRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("ProductQueryService 클래스")
class ProductQueryServiceTest {

    private final String name = "My toy";
    private final String maker = "toy shop";
    private final int price = 15000;
    private final String imageUrl = "toy.jpg";

    @Autowired
    private ProductRepository productRepository;

    private ProductCommandService productCommandService;
    private ProductQueryService productQueryService;

    @BeforeEach
    void setUp() {
        productCommandService = new ProductCommandService(productRepository);
        productQueryService = new ProductQueryService(productRepository);
    }

    @AfterEach
    void clear() {
        productRepository.deleteAll();
    }

    private ProductUpdateRequest getProductRequest() {
        return new ProductRequestDto(name, maker, price, imageUrl);
    }

    @Nested
    @DisplayName("get 메소드는")
    class Describe_get {

        @Nested
        @DisplayName("조회 가능한 id가 주어지면")
        class Context_with_valid_id {
            private Long id;

            @BeforeEach
            void setUp() {
                ProductResponseDto product = productCommandService.create(getProductRequest());
                id = product.getId();
            }

            @Test
            @DisplayName("상품을 반환한다")
            void it_returns_product() {
                ProductResponseDto product = productQueryService.get(id);

                assertThat(product).isNotNull();
                assertThat(product.getId()).isEqualTo(id);
            }
        }

        @Nested
        @DisplayName("조회 불가능한 id가 주어지면")
        class Context_with_invalid_id {
            private final Long invalidId = 1000L;

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> productQueryService.get(invalidId))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("getAll 메소드는")
    class Describe_getAll {

        @Nested
        @DisplayName("저장된 상품이 없으면")
        class Context_with_no_products {

            @Test
            @DisplayName("빈 목록을 반환한다")
            void it_returns_empty_list() {
                List<ProductResponseDto> products = productQueryService.getAll();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 상품이 있으면")
        class Context_with_products {
            private final int productSize = 3;

            @BeforeEach
            void setUp() {
                for (int i = 0; i < productSize; i++) {
                    productCommandService.create(getProductRequest());
                }
            }

            @Test
            @DisplayName("모든 상품을 반환한다")
            void it_returns_all_products() {
                List<ProductResponseDto> products = productQueryService.getAll();

                assertThat(products).hasSize(productSize).element(0).isNotNull();
            }
        }
    }
}
