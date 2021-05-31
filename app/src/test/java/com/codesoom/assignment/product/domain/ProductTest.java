package com.codesoom.assignment.product.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스의")
class ProductTest {

    @Nested
    @DisplayName("change 메서드는")
    class Describe_change {
        private Product product;

        @BeforeEach
        void prepareProduct() {
            product = new Product();
        }

        @Test
        @DisplayName("주어진 값이 유효한 속성만 수정한다")
        void It_changes_only_valid_attributes() {
            product.change("name", null, null, null);
            assertThat(product.getName()).isEqualTo("name");
            assertThat(product.getMaker()).isEqualTo(null);
            assertThat(product.getPrice()).isEqualTo(null);
            assertThat(product.getImageUrl()).isEqualTo(null);

            product.change(null, "maker", null, null);
            assertThat(product.getMaker()).isEqualTo("maker");

            product.change(null, null, 33_000L, null);
            assertThat(product.getPrice()).isEqualTo(33_000L);

            product.change(null, null, null, "imageUrl");
            assertThat(product.getImageUrl()).isEqualTo("imageUrl");
        }

    }

    @Nested
    @DisplayName("toString 메서드는")
    class Describe_toString {
        private Product product;

        @BeforeEach
        void prepareProduct() {
            product = new Product("name", "maker", 33_000L);
        }

        @Test
        @DisplayName("null을 포함한 모든 속성을 문자열로 반환한다")
        void It_returns_all_attributes() {
            assertThat(product.toString())
                    .isEqualTo("Product{id=null,name=name, maker=maker, price=33000, imageUrl=null}");
        }
    }
}
