package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("")
    void getName() {

    }

    @Test
    @DisplayName("")
    void getMaker() {

    }

    @Test
    @DisplayName("")
    void getPrice() {

    }

    @Test
    @DisplayName("")
    void getImage() {

    }
}