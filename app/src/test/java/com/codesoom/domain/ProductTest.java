package com.codesoom.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product class")
class ProductTest {

    private static final String PRODUCT_NAME = "고양이 장남감";
    private static final String PRODUCT_MAKER = "코드숨";
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(10000);
    private static final String PRODUCT_IMAGE_URL = "test.jpg";

    @Nested
    @DisplayName("getter와 setter는")
    class Describe_get {
        @Test
        @DisplayName("Product 정보를 등록하고 등록된 id,name,price,maker,imgUrl을 리턴한다.")
        void getProductResult() {
            Product product = new Product();
            product.setId(1L);
            product.setName(PRODUCT_NAME);
            product.setMaker(PRODUCT_MAKER);
            product.setPrice(PRODUCT_PRICE);
            product.setImageUrl(PRODUCT_IMAGE_URL);

            assertThat(product.getId()).isEqualTo(1L);
            assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
            assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
            assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
            product.setImageUrl(PRODUCT_IMAGE_URL);
        }
    }
}
