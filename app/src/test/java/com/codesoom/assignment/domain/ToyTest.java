package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
* toy 도메인에 대한 간단한 예제를 작성한다는 느낌으로 테스트를 작성했다.
* */
class ToyTest {
    private static final String TOY_NAME = "장난감 뱀";
    private static final String TOY_MAKER = "애옹이네 장난감";
    private static final Integer TOY_PRICE = 5000;
    private static final String TOY_IMAGE = "someUrl";

    @Test
    void toyExample() {
        Toy toy = new Toy();

        toy.setId(1L);
        toy.setName(TOY_NAME);
        toy.setMaker(TOY_MAKER);
        toy.setPrice(TOY_PRICE);
        toy.setImage(TOY_IMAGE);

        assertThat(toy.getId()).isEqualTo(1L);
        assertThat(toy.getName()).isEqualTo(TOY_NAME);
        assertThat(toy.getMaker()).isEqualTo(TOY_MAKER);
        assertThat(toy.getPrice()).isEqualTo(TOY_PRICE);
        assertThat(toy.getImage()).isEqualTo(TOY_IMAGE);
    }
}