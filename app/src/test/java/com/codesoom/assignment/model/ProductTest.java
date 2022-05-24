package com.codesoom.assignment.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void initializeProductTest() {
        Product product = new Product();
        product.setName("name");
        product.setMaker("maker");
        product.setPrice(5000);
        product.setImageUrl("abc.jpg");

        assertThat(product).isNotNull();
    }
}
