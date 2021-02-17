package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// TODO 테스트 목록
// 1. 생성되는 모습 테스트 하기 (문서화)

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void create() {
        product.setId(1L);
        product.setName("탱탱볼");
        product.setPrice(5000L);
        product.setImage("/Users/admin/Document/static/image.jpg");

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo("탱탱볼");
        assertThat(product.getPrice()).isEqualTo(5000L);
        assertThat(product.getImage()).isEqualTo("/Users/admin/Document/static/image.jpg");
    }
}
