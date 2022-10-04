package com.codesoom.assignment.domain;

import com.codesoom.assignment.common.exception.InvalidParamException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Product 클래스")
class ProductTest {

    @Nested
    @DisplayName("빌더는")
    class Describe_builder {
        @Nested
        @DisplayName("이름이 입력되지 않으면")
        class Context_with_empty_name {
            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                Assertions.assertThatThrownBy(() -> Product.builder()
                        .name(null)
                        .maker("삼성")
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build()).isInstanceOf(InvalidParamException.class);
            }
        }
        @Nested
        @DisplayName("제조사가 입력되지 않으면")
        class Context_with_empty_maker {
            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                Assertions.assertThatThrownBy(() -> Product.builder()
                        .name("고양이 장난감1")
                        .maker(null)
                        .price(10000L)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build()).isInstanceOf(InvalidParamException.class);
            }
        }
        @Nested
        @DisplayName("가격이 입력되지 않으면")
        class Context_with_empty_price {
            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                Assertions.assertThatThrownBy(() -> Product.builder()
                        .name("고양이 장난감1")
                        .maker("삼성")
                        .price(null)
                        .imageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png")
                        .build()).isInstanceOf(InvalidParamException.class);
            }
        }


    }

}