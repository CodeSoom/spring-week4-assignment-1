package com.codesoom.assignment.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void initializeProductTest() {
        Product product = new Product("name", "maker", 5000, "abc.jpg");
        Product productWithNoParameter = new Product();

        assertThat(product).isNotNull();
        assertThat(productWithNoParameter).isNotNull();
    }

    @Test
    void initializeProductWithIdTest() {
        Product product = new Product(1L, "name", "maker", 5000, "abc.jpg");

        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(1L);
    }

    @Test
    void equalsAndHashCodeTest() {
        Product product = new Product("name", "maker", 5000, "abc.jpg");
        Product product2 = new Product("name", "maker", 5000, "abc.jpg");

        assertThat(product.equals(product)).isTrue();
        assertThat(product.equals("test")).isFalse();
        assertThat(product.hashCode()).isEqualTo(product2.hashCode());
    }
}
