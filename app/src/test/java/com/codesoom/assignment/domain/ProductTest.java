package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스의")
class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = generateProduct(1L);
    }

    @Nested
    @DisplayName("toString 메소드는")
    class Describe_of_toString {

        @Nested
        @DisplayName("상품으로부터 호출되면")
        class Context_of_product {

            private Product givenProduct;

            @BeforeEach
            void setUp() {
               givenProduct = product;
            }

            @Test
            @DisplayName("속성을 정해진 형식의 문자열로 반환한다")
            void it_returns_attributes_as_string() {
                assertThat(givenProduct.toString())
                        .isEqualTo("[Product] id:1 / name:product1 / maker:maker1 / price:100 / imageUrl:product1.jpg");
            }
        }
    }

    @Nested
    @DisplayName("equals 메소드는")
    class Describe_of_equals {

        @Nested
        @DisplayName("자기 자신이 주어지면")
        class Context_of_myself {

            @Test
            @DisplayName("true를 반환한다")
            void it_returns_true() {
                product.equals(product);
            }
        }

        @Nested
        @DisplayName("null이 주어지면")
        class Context_of_null {

            private Product other;

            @BeforeEach
            void setUp() {
                other = null;
            }

            @Test
            @DisplayName("false를 반환한다")
            void it_returns_false() {
                assertThat(product.equals(other))
                        .isFalse();
            }
        }

        @Nested
        @DisplayName("Product와 다른 타입의 객체가 주어지면")
        class Context_of_different_type {

            private NotProduct other;

            @BeforeEach
            void setUp() {
                other = new NotProduct();
            }

            @Test
            @DisplayName("false를 반환한다")
            void it_returns_false() {
                assertThat(product.equals(other))
                        .isFalse();
            }

            private class NotProduct {
            }
        }

        @Nested
        @DisplayName("모든 속성이 같은 객체가 주어지면")
        class Context_of_same_attribute {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
            }

            @Test
            @DisplayName("true를 반환한다")
            void it_returns_true() {
                assertThat(product.equals(other))
                        .isTrue();
            }
        }

        @Nested
        @DisplayName("id가 다른 객체가 주어지면")
        class Context_of_different_id {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
                other.setId(42L);
            }

            @Test
            @DisplayName("false를 반환한다")
            void it_returns_false() {
                assertThat(product.equals(other))
                        .isFalse();
            }
        }

        @Nested
        @DisplayName("name이 다른 객체가 주어지면")
        class Context_of_different_name {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
                other.setName("other");
            }

            @Test
            @DisplayName("false를 반환한다")
            void it_returns_false() {
                assertThat(product.equals(other))
                        .isFalse();
            }
        }

        @Nested
        @DisplayName("price가 다른 객체가 주어지면")
        class Context_of_different_price {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
                other.setPrice(42L);
            }

            @Test
            @DisplayName("false를 반환한다")
            void it_returns_false() {
                assertThat(product.equals(other))
                        .isFalse();
            }
        }

        @Nested
        @DisplayName("maker가 다른 객체가 주어지면")
        class Context_of_different_maker {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
                other.setMaker("other");
            }

            @Test
            @DisplayName("false를 반환한다")
            void it_returns_false() {
                assertThat(product.equals(other))
                        .isFalse();
            }
        }

        @Nested
        @DisplayName("imageUrl이 다른 객체가 주어지면")
        class Context_of_different_imageUrl {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
                other.setImageUrl("other.jpg");
            }

            @Test
            @DisplayName("false를 반환한다")
            void it_returns_false() {
                assertThat(product.equals(other))
                        .isFalse();
            }
        }
    }

    @Nested
    @DisplayName("hashCode 메소드는")
    class Describe_of_hashCode {

        @Nested
        @DisplayName("속성과 타입이 동일한 객체로부터 호출되면")
        class Context_of_same_attribute {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
            }

            @Test
            @DisplayName("동일한 code를 반환한다")
            void it_returns_same_code() {
                assertThat(product.hashCode())
                        .isEqualTo(other.hashCode());
            }
        }

        @Nested
        @DisplayName("id가 다른 두 객체로부터 호출되면")
        class Context_of_different_id {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
                other.setId(42L);
            }

            @Test
            @DisplayName("각기 다른 code를 반환한다")
            void it_returns_different_code() {
                assertThat(product.hashCode())
                        .isNotEqualTo(other.hashCode());
            }
        }

        @Nested
        @DisplayName("name이 다른 두 객체로부터 호출되면")
        class Context_of_different_name {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
                other.setName("other");
            }

            @Test
            @DisplayName("각기 다른 code를 반환한다")
            void it_returns_different_code() {
                assertThat(product.hashCode())
                        .isNotEqualTo(other.hashCode());
            }
        }

        @Nested
        @DisplayName("price가 다른 두 객체로부터 호출되면")
        class Context_of_different_price {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
                other.setPrice(42L);
            }

            @Test
            @DisplayName("각기 다른 code를 반환한다")
            void it_returns_different_code() {
                assertThat(product.hashCode())
                        .isNotEqualTo(other.hashCode());
            }
        }

        @Nested
        @DisplayName("price가 다른 두 객체로부터 호출되면")
        class Context_of_different_maker {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
                other.setMaker("other");
            }

            @Test
            @DisplayName("각기 다른 code를 반환한다")
            void it_returns_different_code() {
                assertThat(product.hashCode())
                        .isNotEqualTo(other.hashCode());
            }
        }

        @Nested
        @DisplayName("imageUrl가 다른 두 객체로부터 호출되면")
        class Context_of_different_imageUrl {

            private Product other;

            @BeforeEach
            void setUp() {
                other = cloneProduct(product);
                other.setImageUrl("other.jpg");
            }

            @Test
            @DisplayName("각기 다른 code를 반환한다")
            void it_returns_different_code() {
                assertThat(product.hashCode())
                        .isNotEqualTo(other.hashCode());
            }
        }
    }

    private Product generateProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("product" + id);
        product.setPrice(id * 100L);
        product.setMaker("maker" + id);
        product.setImageUrl("product" + id + ".jpg");
        return product;
    }

    private Product cloneProduct(Product source) {
       Product product = new Product();
        product.setId(source.getId());
        product.setName(source.getName());
        product.setPrice(source.getPrice());
        product.setMaker(source.getMaker());
        product.setImageUrl(source.getImageUrl());
        return product;
    }
}
