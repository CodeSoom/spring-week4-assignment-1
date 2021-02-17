package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testEquals() {
        String name = "고양이 인형";
        String maker = "라스 공방";
        String price = "1000원";
        String imageURL = "https://magical.dev/static/las.jpg";

        ProductId id = new ProductId(1L);
        Product product = new Product(id, name, maker, price, imageURL);
        Product sameProduct = new Product(id, name, maker, price, imageURL);

        ProductId otherId = new ProductId(2L);
        Product otherProduct = new Product(otherId, name, maker, price, imageURL);

        assertThat(product)
            .isEqualTo(product)
            .isEqualTo(sameProduct)
            .isNotEqualTo(otherProduct)
            .isNotEqualTo(null)
            .isNotEqualTo(new Object());
    }
}