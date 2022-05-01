package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Toy 클래스")
class ToyTest {
    private final Long TOY_ID = 1L;
    private final String TOY_NAME = "toy_snake";
    private final String TOY_MAKER = "make by cat";
    private final Integer TOY_PRICE = 5000;
    private final String TOY_IMAGE_URL = "http://toy.snake.jpg";

    private Toy toy;

    @BeforeEach
    void setUp() {
        toy = new Toy();
        toy.setId(TOY_ID);
        toy.setName(TOY_NAME);
        toy.setMaker(TOY_MAKER);
        toy.setPrice(TOY_PRICE);
        toy.setImageUrl(TOY_IMAGE_URL);
    }

    @Test
    @DisplayName("id를 반환합니다")
    void getId() {
        assertThat(toy.getId()).isEqualTo(TOY_ID);
    }

    @Test
    @DisplayName("name을 반환합니다")
    void getName() {
        assertThat(toy.getName()).isEqualTo(TOY_NAME);
    }

    @Test
    @DisplayName("maker를 반환합니다")
    void getMaker() {
        assertThat(toy.getMaker()).isEqualTo(TOY_MAKER);
    }

    @Test
    @DisplayName("price를 반환합니다")
    void getPrice() {
        assertThat(toy.getPrice()).isEqualTo(TOY_PRICE);
    }

    @Test
    @DisplayName("imageUrl을 반환합니다")
    void getImageUrl() {
        assertThat(toy.getImageUrl()).isEqualTo(TOY_IMAGE_URL);
    }
}
