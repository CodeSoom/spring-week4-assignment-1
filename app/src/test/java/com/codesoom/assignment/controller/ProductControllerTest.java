//TODO : getList 정상 작동 여부
//TODO : detail 목록에 빈값이 존재 여부 확인 테스트


package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    private static final String PRODUCT_TITLE = "고양이 장난감";

    private ProductController productController;
    private Product product;

    @BeforeEach
    void setUp() {
        productController = new ProductController(product);

        product = new Product();
        product.setId(1L);
        product.setTitle(PRODUCT_TITLE);

        productController.create(product);
    }

    @Test
    void getList() {
        List<Product> products = productController.list();

        assertThat(products).hasSize(1);

        Product product = products.get(0);
        assertThat(product.getTitle()).isEqualTo(PRODUCT_TITLE);
    }

}
