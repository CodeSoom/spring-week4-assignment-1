package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ProductTest {

    private static final Long ID = 1L;
    private static final String NAME = "키위새";
    private static final String MAKER = "어쩌구컴퍼니";
    private static final BigDecimal PRICE = BigDecimal.valueOf(3000);
    private static final String URL = "some url";

    @DisplayName("기본 생성자로 상품 엔티티를 생성할 수 있다.")
    @Test
    void createNoArgsConstructorTest () {
        assertThat(new Product()).isNotNull();
    }

    @DisplayName("생성자로 상품 엔티티를 생성할 수 있다.")
    @Test
    void createWithBuilderTest() {
        Product product = new Product(ID, NAME, MAKER, PRICE, URL);
        assertThat(product).isNotNull();
        assertAll(() -> {
            assertThat(product.getId()).isEqualTo(ID);
            assertThat(product.getName()).isEqualTo(NAME);
            assertThat(product.getMaker()).isEqualTo(MAKER);
            assertThat(product.getPrice()).isEqualTo(PRICE);
            assertThat(product.getImageUrl()).isEqualTo(URL);
        });
    }

    @DisplayName("getter는 각 필드의 값을 반환한다.")
    @Test
    void getterTest() {
        Product product
                = new Product(NAME, MAKER, PRICE, URL);
        assertAll(() -> {
            assertThat(product.getId()).isEqualTo(null);
            assertThat(product.getName()).isEqualTo(NAME);
            assertThat(product.getMaker()).isEqualTo(MAKER);
            assertThat(product.getPrice()).isEqualTo(PRICE);
            assertThat(product.getImageUrl()).isEqualTo(URL);
        });
    }

    @DisplayName("update는 상품의 내용을 수정한다.")
    @Test
    void updateTest() {
        Product product = new Product(ID, NAME, MAKER, PRICE, URL);
        final ProductDto productDto = new ProductDto(NAME + "ver2", MAKER, PRICE, URL);

        product.update(productDto.product());

        assertThat(product.getName()).isEqualTo(NAME + "ver2");
    }

    @DisplayName("toString은 상품에 저장된 내용을 보여준다.")
    @Test
    void toStringTest() {
        Product product = new Product(ID, NAME, MAKER, PRICE, URL);
        assertThat(product.toString()).contains(NAME);
    }

}
