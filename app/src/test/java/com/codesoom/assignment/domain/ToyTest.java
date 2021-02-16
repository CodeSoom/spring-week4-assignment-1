package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ToyTest {
    private final Long toyId = 1L;
    private final String toyName = "장난감 칼";
    private final String toyBrand = "코드숨";
    private final int toyPrice = 5000;
    private final String toyImageUrl = "https://cdn.shopify.com/s/files/1/0940/6942/products/DSC0243_800x.jpg";

    private Toy toy;

    @BeforeEach
    void setToy() {
        toy = new Toy(toyName, toyBrand, toyPrice, toyImageUrl);
    }

    @Test
    void testConstructor() {
        assertThat(toy.getName()).isEqualTo(toyName);
        assertThat(toy.getBrand()).isEqualTo(toyBrand);
        assertThat(toy.getPrice()).isEqualTo(toyPrice);
        assertThat(toy.getImageUrl()).isEqualTo(toyImageUrl);
    }

    @Test
    void testId() {
        toy.setId(toyId);
        assertThat(toy.getId()).isEqualTo(toyId);
    }

    @Test
    void testName() {
        toy.setName(toyName);
        assertThat(toy.getName()).isEqualTo(toyName);
    }

    @Test
    void testBrand() {
        toy.setBrand(toyBrand);
        assertThat(toy.getBrand()).isEqualTo(toyBrand);
    }

    @Test
    void testPrice() {
        toy.setPrice(toyPrice);
        assertThat(toy.getPrice()).isEqualTo(toyPrice);
    }

    @Test
    void testImageUrl() {
        toy.setImageUrl(toyImageUrl);
        assertThat(toy.getImageUrl()).isEqualTo(toyImageUrl);
    }
}