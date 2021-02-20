package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product product;
    private final Long ID = 1L;
    private final String NAME = "setupName";
    private final String MAKER = "setupMaker";
    private final int PRICE = 100;
    private final String IMAGEURL = "setupImage";

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(ID)
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .imageUrl(IMAGEURL)
                .build();
    }

    @Test
    @DisplayName("아이디를 리턴한다")
    void getId() {
        assertThat(product.getId()).isEqualTo(ID);
    }

    @Test
    @DisplayName("이름을 리턴한다")
    void getName() {
        assertThat(product.getName()).isEqualTo(NAME);
    }

    @Test
    @DisplayName("메이커를 리턴한다")
    void getMaker() {
        assertThat(product.getMaker()).isEqualTo(MAKER);
    }

    @Test
    @DisplayName("가격을 리턴한다")
    void getPrice() {
        assertThat(product.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("이미지를 리턴한다")
    void getImage() {
        assertThat(product.getImageUrl()).isEqualTo(IMAGEURL);
    }

    @Test
    @DisplayName("매개변수가 없는 생성자로 객체를 생성한다")
    void constructorWithNoArgs() {
        assertThat(new Product()).isInstanceOf(Product.class);
    }

    @Test
    @DisplayName("모든 매개변수가 있는 생성자로 객체를 생성한다")
    void constructorWithAllArgs() {
        assertThat(Product.builder()
                .id(ID)
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .imageUrl(IMAGEURL)
                .build()).isInstanceOf(Product.class);
    }
}