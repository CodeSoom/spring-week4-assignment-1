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
    private final String IMAGE_URL = "이미지 URL";

    @Test
    @DisplayName("id가 없는 Prodcut를 생성한다.")
    void of() {
        Product product = Product.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
    }

    @Test
    @DisplayName("update 메서드는 주어진 Product의 name, maker, price, image_url으로 업데이트합니다.")
    void update() {
        Product updatedProduct = new Product();
        Product product = Product.of(NAME, MAKER, PRICE, IMAGE_URL);

        updatedProduct.update(product);

        assertThat(updatedProduct.getName()).isEqualTo(NAME);
        assertThat(updatedProduct.getMaker()).isEqualTo(MAKER);
        assertThat(updatedProduct.getPrice()).isEqualTo(PRICE);
        assertThat(updatedProduct.getImageUrl()).isEqualTo(IMAGE_URL);
    }

    @Test
    @DisplayName("Product의 프로퍼티를 검사합니다.")
    void verify_property() {
        Product product = new Product();

        product.setId(ID);
        product.setName(NAME);
        product.setMaker(MAKER);
        product.setPrice(PRICE);
        product.setImageUrl(IMAGE_URL);

        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
    }
}