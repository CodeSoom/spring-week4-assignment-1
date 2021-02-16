package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// 1. 필요한 변수 작성 : 이름, 메이커, 가격, 이미지
class ProductTest {

    private Product product;

    String name = "toy-snake";
    String maker = "codesoom";
    int price = 5000;
    String imgName = "snake-sample.jpg";

    @BeforeEach
    void setUp(){

        product = Product.builder()
                .name(name)
                .maker(maker)
                .price(price)
                .imgName(imgName)
                .build();

    }

    @DisplayName("변수값 확인")
    @Test
    void testProduct(){
        assertThat(name).isEqualTo(product.getName());
        assertThat(maker).isEqualTo(product.getMaker());
        assertThat(name).isEqualTo(product.getName());
        assertThat(imgName).isEqualTo(product.getImgName());
    }

}