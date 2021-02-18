package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private static Long ID = 1L;

    private static String NAME = "물고기 장난감";

    private static String MAKER = "애옹이네 장난감";

    private static Integer PRICE = 5000;

    private static String IMAGE = "https://bit.ly/2M4YXkw";

    Product product;

    void setUp() {
        product = new Product(ID, NAME, MAKER, PRICE, IMAGE);
    }

    @Test
    @DisplayName("getName 메소드는 장난감 이름을 반환한다")
    void getName() {
        assertThat(product.getName()).isEqualTo(NAME);
    }

    @Test
    @DisplayName("getMaker 메소드는 메이커를 반환한다")
    void getMaker() {
        assertThat(product.getMaker()).isEqualTo(MAKER);
    }

    @Test
    @DisplayName("getPrice 메소드는 장난감 가격을 반환한다")
    void getPrice() {
        assertThat(product.getPrice()).isEqualTo(PRICE);
    }
}
