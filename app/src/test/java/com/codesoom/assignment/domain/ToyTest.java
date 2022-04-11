package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ToyTest {

    @DisplayName("기본 생성자로 장난감 엔티티를 생성할 수 있다.")
    @Test
    void createNoArgsConstructorTest () {
        assertThat(new Toy()).isNotNull();
    }

    @DisplayName("Builder 패턴으로 장난감 엔티티를 생성할 수 있다.")
    @Test
    void createWithBuilderTest() {
        assertThat(new Toy.Builder(1L,
                "키위새",
                "어쩌구컴퍼니",
                BigDecimal.valueOf(3000),
                "https://somthing.com").build())
        .isNotNull();
    }

    @DisplayName("getter는 각 필드의 값을 반환한다.")
    @Test
    void getterTest() {
        Toy toy = new Toy.Builder(1L,
                "키위새",
                "어쩌구컴퍼니",
                BigDecimal.valueOf(3000),
                "https://somthing.com").build();

        assertThat(toy.getId()).isEqualTo(1L);
        assertThat(toy.getName()).isEqualTo("키위새");
        assertThat(toy.getMaker()).isEqualTo("어쩌구컴퍼니");
        assertThat(toy.getPrice()).isEqualTo(BigDecimal.valueOf(3000));
        assertThat(toy.getImage()).isEqualTo("https://somthing.com");
    }

}
