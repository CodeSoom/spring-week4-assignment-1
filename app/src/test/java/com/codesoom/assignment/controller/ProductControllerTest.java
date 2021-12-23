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

@Nested
@DisplayName("ProductController 클래스")
class ProductControllerTest {
    private static final String PRODUCT_TITLE = "고양이 장난감";

    ProductController productController;
    private  Product product;

    @Nested
    @DisplayName("List 메소드는")
    class ProductList {

        @BeforeEach
        void setUp() {

        }


        @Test
        @DisplayName("만약 List 안에 setTilte 값이 있다면")
        void getList() {
            List<Product> products = productController.list();

            assertThat(products).hasSize(1);

            products.get(0);
            assertThat(product.getId()).isEqualTo(1L);
            assertThat(product.getTitle()).isEqualTo(PRODUCT_TITLE);
        }
    }
}

