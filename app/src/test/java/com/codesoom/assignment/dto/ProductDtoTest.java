package com.codesoom.assignment.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductDtoTest {

    @Test
    void initializeProductDtoTest() {
        ProductDto productDto = new ProductDto("name", "maker", 5000, "abc.jpg");

        assertThat(productDto).isNotNull();
    }
}
