//TODO : List 목록에 빈값이 존재 여부 확인 테스트

package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    private ProductController productController;
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        productController = new ProductController(product);

        product.setId(1L);
        product.setTitle("고양이 장난감");
    }

    @Test
    void getList() {
        assertThat(productController.list()).isEmpty();
    }

    @Test
    void getdetail() {
        assertThat(productController.detail(product.getId())).isEqualTo(0L);
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class createProduct{

        @Test
        @DisplayName("product가 null이라면")
        void CheckWithcreateProduct() {
            Product product = null;
            assertThat(productController.create(product)).isEqualTo(null);
        }
    }
}
