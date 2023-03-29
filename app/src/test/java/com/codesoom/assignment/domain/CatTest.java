package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cat Class")
class CatTest {
    String name;
    String maker;
    Long price;
    String imgUrl;

    @BeforeEach
    public void setup() {
        name = "고양이이름";
        maker = "아디다스";
        price = 5000L;
        imgUrl = "test.jpg";
    }

    @Nested
    @DisplayName("createMethod는")
    class create {

        @Nested
        @DisplayName("모든 파라미터가 있을 경우")
        class parameter_all {

            @Test
            @DisplayName("모든 데이터가 들어있는 고양이를 생성한다.")
            public void create_all_parameter() {
                Cat createdCat = Cat.create(name, maker, price, imgUrl);
                assertAll(() -> assertThat(createdCat.getName()).isEqualTo(name));
                assertAll(() -> assertThat(createdCat.getMaker()).isEqualTo(maker));
                assertAll(() -> assertThat(createdCat.getPrice()).isEqualTo(price));
                assertAll(() -> assertThat(createdCat.getImgUrl()).isEqualTo(imgUrl));
            }
        }

        @Nested
        @DisplayName("특정 파라미터가 null 일  경우")
        class exist_specific_empty_parameter {
            @Test
            @DisplayName("해당 값은 null 값 이 들어간다.")
            public void specific_parameter_is_null() {
                Cat createdCat = Cat.create(name, maker, price, null);
                assertAll(() -> assertThat(createdCat.getImgUrl()).isEqualTo(null));
            }
        }
    }

    @Nested
    @DisplayName("update 는")
    class update {
        String updateName = "UPDATE";
        String updateMaker = "changeMaker";
        Long updatePrice = 10000L;
        String updateUrl = "UpdateUrl";


        @Nested
        @DisplayName("모든 파라미터에 데이터가 존재할 경우")
        class exist_all_parameter {

            @Test
            @DisplayName("모든 내용을 수정한다.")
            public void update_all_parameter() {
                Cat cat = Cat.create(name, maker, price, imgUrl);
                Cat updatedCat = cat.update(updateName, updateMaker, updatePrice, updateUrl);
                assertAll(() -> assertThat(cat.getName()).isEqualTo(updateName),
                        () -> assertThat(cat.getMaker()).isEqualTo(updateMaker),
                        () -> assertThat(cat.getPrice()).isEqualTo(updatePrice),
                        () -> assertThat(cat.getImgUrl()).isEqualTo(updateUrl));
            }
        }

    }
}