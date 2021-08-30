package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    @DisplayName("객체 생성 테스트")
    void created() {
        String name = "장난감 뱀";
        String maker = "애옹이네 장난감";
        long price = 10000L;
        String imageUrl= "http://localhost:8080/snake";

        Product product = new Product(name, maker, price, imageUrl);

        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getMaker()).isEqualTo(maker);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getImageUrl()).isEqualTo(imageUrl);
    }
}
