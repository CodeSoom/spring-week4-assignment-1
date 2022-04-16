package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {

    private static final String TOY_NAME = "키위새";
    private static final String TOY_MAKER = "어쩌구컴퍼니";
    private static final BigDecimal TOY_PRICE = BigDecimal.valueOf(3000);
    private static final String TOY_IMG_URL = "some url";

    @DisplayName("기본 생성자로 장난감 엔티티를 생성할 수 있다.")
    @Test
    void createNoArgsConstructorTest () {
        assertThat(new Product()).isNotNull();
    }

    @DisplayName("Builder 패턴으로 장난감 엔티티를 생성할 수 있다.")
    @Test
    void createWithBuilderTest() {
        assertThat(
                Product.builder().name(TOY_NAME).maker(TOY_MAKER).price(TOY_PRICE).image(TOY_IMG_URL).build()
        ).isNotNull();
    }

    @DisplayName("getter는 각 필드의 값을 반환한다.")
    @Test
    void getterTest() {
        Product product = Product.builder().name(TOY_NAME).maker(TOY_MAKER).price(TOY_PRICE).image(TOY_IMG_URL).build();

        assertThat(product.getName()).isEqualTo(TOY_NAME);
        assertThat(product.getMaker()).isEqualTo(TOY_MAKER);
        assertThat(product.getPrice()).isEqualTo(TOY_PRICE);
        assertThat(product.getImage()).isEqualTo(TOY_IMG_URL);
    }

    @DisplayName("update는 장난감의 내용을 수정한다.")
    @Test
    void updateTest() {
        Product product = Product.builder().name(TOY_NAME).maker(TOY_MAKER).price(TOY_PRICE).image(TOY_IMG_URL).build();
        final ProductDto productDto = new ProductDto(TOY_NAME + "ver2", TOY_MAKER, TOY_PRICE, TOY_IMG_URL);

        product.update(productDto.product());

        assertThat(product.getName()).isEqualTo(TOY_NAME + "ver2");
    }

}
