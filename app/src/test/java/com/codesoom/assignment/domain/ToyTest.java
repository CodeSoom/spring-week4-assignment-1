package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ToyTest {

    private static final String TOY_NAME = "키위새";
    private static final String TOY_MAKER = "어쩌구컴퍼니";
    private static final BigDecimal TOY_PRICE = BigDecimal.valueOf(3000);
    private static final String TOY_IMG_URL = "some url";

    @DisplayName("기본 생성자로 장난감 엔티티를 생성할 수 있다.")
    @Test
    void createNoArgsConstructorTest () {
        assertThat(new Toy()).isNotNull();
    }

    @DisplayName("Builder 패턴으로 장난감 엔티티를 생성할 수 있다.")
    @Test
    void createWithBuilderTest() {
        assertThat(
                Toy.builder().name(TOY_NAME).maker(TOY_MAKER).price(TOY_PRICE).image(TOY_IMG_URL).build()
        ).isNotNull();
    }

    @DisplayName("getter는 각 필드의 값을 반환한다.")
    @Test
    void getterTest() {
        Toy toy = Toy.builder().name(TOY_NAME).maker(TOY_MAKER).price(TOY_PRICE).image(TOY_IMG_URL).build();

        assertThat(toy.getId()).isEqualTo(1L);
        assertThat(toy.getName()).isEqualTo(TOY_NAME);
        assertThat(toy.getMaker()).isEqualTo(TOY_MAKER);
        assertThat(toy.getPrice()).isEqualTo(TOY_PRICE);
        assertThat(toy.getImage()).isEqualTo(TOY_IMG_URL);
    }

}
