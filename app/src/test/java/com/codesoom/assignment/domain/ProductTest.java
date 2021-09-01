package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스")
class ProductTest {

    private final Long ID = 1L;
    private final String NAME = "이름";
    private final String MAKER = "메이커";
    private final Long PRICE = 1000L;
    private final String IMAGE_URI = "이미지 URL";

    @Test
    @DisplayName("Product의 생성자를 확인합니다.")
    void verify_constructor() {
        Product product = new Product(ID, NAME, MAKER, PRICE, IMAGE_URI);

        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImageURI()).isEqualTo(IMAGE_URI);
    }

    @Test
    @DisplayName("Product의 프로퍼티를 검사합니다.")
    void verify_property() {
        Product product = new Product();

        product.setId(ID);
        product.setName(NAME);
        product.setMaker(MAKER);
        product.setPrice(PRICE);
        product.setImageURI(IMAGE_URI);

        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImageURI()).isEqualTo(IMAGE_URI);
    }
}