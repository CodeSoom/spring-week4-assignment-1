package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("장난감 객체 생성 테스트")
    void createProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName("test1");
        product.setMaker("maker1");
        product.setPrice(1000L);
        product.setImg("url");

        assertEquals(1L, product.getId());
        assertEquals("test1", product.getName());
        assertEquals("maker1", product.getMaker());
        assertEquals(1000L, product.getPrice());
        assertEquals("url", product.getImg());

    }

}
