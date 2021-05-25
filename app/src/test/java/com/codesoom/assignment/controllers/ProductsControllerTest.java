package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ProductsController의")
class ProductControllerTest {

    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController();
    }

    @Nested
    @DisplayName("list 메소드는")
    class Describe_of_list {

        @Nested
        @DisplayName("등록된 상품이 없을 때")
        class Context_of_empty_products {

            @Test
            @DisplayName("비어있는 상품 리스트를 반환한다")
            public void it_returns_empty_list() {
                assertThat(productController.list())
                        .isEmpty();
            }
        }

        @Nested
        @DisplayName("등록된 상품이 있을 때")
        class Context_of_not_empty_products {

            @Test
            @DisplayName("상품 리스트를 반환한다")
            public void it_returns_empty_list() {
                assertThat(productController.list())
                        .isNotEmpty();
            }
        }
    }

}
