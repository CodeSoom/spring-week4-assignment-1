package com.codesoom.assignment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cat Class")
class CatTest {

    @Nested
    @DisplayName("createMethod는")
    class create {

        @Nested
        @DisplayName("모든 파라미터가 있을 경우")
        class parameter_all {

            @Test
            @DisplayName("모든 데이터가 들어있는 고양이를 생성한다.")
            public void create_all_parameter() {
                String name = "고양이이름";
                String maker = "아디다스";
                Long price = 5000L;
                String imgUrl = "test.jpg";

                Cat cat = Cat.create(name, maker, price, imgUrl);
                assertAll(() -> assertThat(cat.getName()).isEqualTo(name));
                assertAll(() -> assertThat(cat.getMaker()).isEqualTo(maker));
                assertAll(() -> assertThat(cat.getPrice()).isEqualTo(price));
                assertAll(() -> assertThat(cat.getImgUrl()).isEqualTo(imgUrl));
            }
        }

        @Nested
        @DisplayName("특정 파라미터가 null일  경우")
        class exist_specific_empty_parameter {
            @Test
            @DisplayName("해당 값은 null값이 들어간다.")
            public void specific_parameter_is_null(){

                String name = "고양이이름";
                String maker = "아디다스";
                Long price = 5000L;
                String imgUrl = null;

                Cat cat = Cat.create(name, maker, price, imgUrl);
                assertAll(() -> assertThat(cat.getImgUrl()).isEqualTo(null));
            }
        }
    }
}