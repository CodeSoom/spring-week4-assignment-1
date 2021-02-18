package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private static final Long ID = 1L;

    private static final String NAME = "물고기 장난감";

    private static final String MAKER = "애옹이네 장난감";

    private static final Integer PRICE = 5000;

    private static final String IMAGE = "https://bit.ly/2M4YXkw";

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product(ID, NAME, MAKER, PRICE, IMAGE);
    }

    @Test
    @DisplayName("getId 메소드는 장난감 아이디를 반환한다")
    void getId() {
        assertThat(product.getId()).isEqualTo(ID);
    }

    @Test
    @DisplayName("getName 메소드는 장난감 이름을 반환한다")
    void getName() {
        assertThat(product.getName()).isEqualTo(NAME);
    }

    @Test
    @DisplayName("getMaker 메소드는 장난감 메이커를 반환한다")
    void getMaker() {
        assertThat(product.getMaker()).isEqualTo(MAKER);
    }

    @Test
    @DisplayName("getPrice 메소드는 장난감 가격을 반환한다")
    void getPrice() {
        assertThat(product.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("getImage 메소드는 장난감 이미지를 반환한다")
    void getImage() {
        assertThat(product.getImage()).isEqualTo(IMAGE);
    }
}
