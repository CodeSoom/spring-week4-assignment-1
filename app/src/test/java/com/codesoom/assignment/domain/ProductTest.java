package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductTest {
    public static final String NAME = "털뭉치";
    public static final String MAKER = "애옹이네 장난감";
    public static final int PRICE = 2000;
    public static final String IMAGE_URL = "https://cdn.pixabay.com/photo/2018/10/05/12/09/animal-3725762_960_720.jpg";

    @DisplayName("도메인 생성 테스트")
    @Test
    void create() {
        Product product = new Product(NAME, MAKER, PRICE, IMAGE_URL);

        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {
        private Product product;
        private static final String UPDATE_NAME = "캣닢";
        private static final String UPDATE_MAKER = "캣드러그";
        private static final int UPDATE_PRICE = 7000;
        private static final String UPDATE_IMAGE_URL = "https://www.catdrug.com/images/1234567";

        @BeforeEach
        void setUp() {
            product = new Product(NAME, MAKER, PRICE, IMAGE_URL);
        }

        @Test
        @DisplayName("product의 값을 변경한다.")
        void it_updates_product() {
            product.update(UPDATE_NAME, UPDATE_MAKER, UPDATE_PRICE, UPDATE_IMAGE_URL);

            assertThat(product.getName()).isEqualTo(UPDATE_NAME);
            assertThat(product.getMaker()).isEqualTo(UPDATE_MAKER);
            assertThat(product.getPrice()).isEqualTo(UPDATE_PRICE);
            assertThat(product.getImageUrl()).isEqualTo(UPDATE_IMAGE_URL);
        }
    }
}
