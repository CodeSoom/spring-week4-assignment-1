package com.codesoom.assignment.core.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 도메인 클래스")
class ProductTest {
    private final String NAME = "고양이 장난감 이름";
    private final String BRAND = "고양이 장난감 브랜드";
    private final int PRICE = 10000;

    private final String UPDATE_POSTFIX = "[SETTER]";


    @Nested
    @DisplayName("생성자 함수")
    class DescribeConstructor {
        @Nested
        @DisplayName("인자가 없을 때")
        class ContextNoArgumentsConstructor {
            @Test
            @DisplayName("모든 항목의 값이 없는 객체 생성")
            void withoutValues() {
                Product product = newProductWithoutArguments();

                assertThat(product).isNotNull();
                assertThat(product.getName()).isNull();
                assertThat(product.getBrand()).isNull();
                assertThat(product.getPrice()).isNull();
            }
        }

        @Nested
        @DisplayName("인자가 있을 때")
        class ContextArgumentsConstructor {
            @Test
            @DisplayName("모든 항목의 값이 있는 객체 생성")
            void withValues() {
                Product product = newProductWithArguments();

                assertThat(product).isNotNull();
                assertThat(product.getName()).isEqualTo(NAME);
                assertThat(product.getBrand()).isEqualTo(BRAND);
                assertThat(product.getPrice()).isEqualTo(PRICE);
            }
        }
    }

    @Nested
    @DisplayName("Getter/Setter 함수")
    class DescribeGetterSetter {
        @Nested
        @DisplayName("인자가 없을 때")
        class ContextNoArgumentsConstructor {
            @Test
            @DisplayName("특정 항목의 값 변경")
            void whenNull2NotNullValues() {
                Product product = newProductWithoutArguments();

                assertThat(product).isNotNull();
                assertThat(product.getName()).isNull();
                assertThat(product.getBrand()).isNull();
                assertThat(product.getPrice()).isNull();

                product.setName(NAME);
                product.setBrand(BRAND);
                product.setPrice(PRICE);

                assertThat(product.getName()).isEqualTo(NAME);
                assertThat(product.getBrand()).isEqualTo(BRAND);
                assertThat(product.getPrice()).isEqualTo(PRICE);
            }
        }

        @Nested
        @DisplayName("인자가 있을 때")
        class ContextArgumentsConstructor {
            @Test
            @DisplayName("특정 항목의 값 변경")
            void whenValues2UpdatedValues() {
                Product product = newProductWithArguments();

                assertThat(product).isNotNull();
                assertThat(product.getName()).isEqualTo(NAME);
                assertThat(product.getBrand()).isEqualTo(BRAND);
                assertThat(product.getPrice()).isEqualTo(PRICE);

                product.setName(NAME + UPDATE_POSTFIX);
                product.setBrand(BRAND + UPDATE_POSTFIX);
                product.setPrice(PRICE + 100);

                assertThat(product.getName()).isEqualTo(NAME + UPDATE_POSTFIX);
                assertThat(product.getBrand()).isEqualTo(BRAND + UPDATE_POSTFIX);
                assertThat(product.getPrice()).isEqualTo(PRICE + 100);
            }
        }
    }

    public Product newProductWithoutArguments() {
        return Product.builder().build();
    }

    public Product newProductWithArguments() {
        return Product.builder()
                .name(NAME)
                .brand(BRAND)
                .price(PRICE)
                .build();
    }

}
