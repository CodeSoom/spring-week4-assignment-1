package com.codesoom.assignment.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void initializeProductTest() {
        Product product = new Product("name", "maker", 5000, "abc.jpg");

        assertThat(product).isNotNull();
    }
}
