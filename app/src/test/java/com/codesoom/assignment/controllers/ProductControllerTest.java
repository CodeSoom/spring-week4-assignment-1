package com.codesoom.assignment.controllers;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// TODO
// 1. 어떤 것을 작성할까?
// 2. 어떤 테스트가 필요할까?
// C.R.U.D 뭐 이런거 있겠지

class ProductControllerTest {

    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController();
    }

    @Test
    void createController() {
        assertThat(productController).isNotNull();
    }
}
