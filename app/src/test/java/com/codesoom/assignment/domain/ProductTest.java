package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product 클래스")
class ProductTest {
    //fixture
    final String NAME = "My Toy";
    final String MAKER = "My Home";
    final Long PRICE = 5000L;
    final String IMAGE_URL = "https://cdn.pixabay.com/photo/2016/10/01/20/54/mouse-1708347_1280.jpg";

    @DisplayName("Product가 정상적으로 생성되고 값이 세팅되었는지 확인합니다")
    @Test
    void productCreateTest() {
        Product product = new Product();
        product.setName(NAME);
        product.setMaker(MAKER);
        product.setPrice(PRICE);
        product.setImageUrl(IMAGE_URL);

        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
    }
}
