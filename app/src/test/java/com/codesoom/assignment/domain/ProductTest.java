package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스")
class ProductTest {

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product(0L, "catTower", "samsung", 35900L, "https://thumbnail14.coupangcdn.com/thumbnails/remote/712x712ex/image/retail/images/451976858609946-e5186418-5f5e-4f4c-bccc-a59ac573d029.jpg");
        product2 = new Product(1L, "catBall", "love cat", 8000L, "http://mstatic1.e-himart.co.kr/contents/goods/00/05/96/13/20/0005961320__TB10__M_640_640.jpg");
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @BeforeEach
        void setUP() {
            product2.update(product1);
        }

        @Test
        @DisplayName("요청받은 product로 수정된 product를 리턴한다.")
        void it_returns_product_that_update_by_request_product() {
            assertThat(product1.getName()).isEqualTo(product2.getName());
            assertThat(product1.getMaker()).isEqualTo(product2.getMaker());
            assertThat(product1.getPrice()).isEqualTo(product2.getPrice());
            assertThat(product1.getImageUrl()).isEqualTo(product2.getImageUrl());
        }
    }
}
