package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private static final String PRODUCT_NAME = "캣타워";
    private static final String PRODUCT_MAKER = "야옹이";
    private static final int PRODUCT_PRICE = 20000;
    private static final String PRODUCT_IMAGE_URL = "https://pixabay.com/photos/cat-kitten-playful-pet-feline-5694895/";
    private static final String PRODUCT_NAME_FOR_UPDATING = "장난감뱀";
    private static final String PRODUCT_MAKER_FOR_UPDATING = "호랑이";
    private static final int PRODUCT_PRICE_FOR_UPDATING = 30000;
    private static final String PRODUCT_IMAGE_URL_FOR_UPDATING = "https://pixabay.com/photos/animal-close-up-cobra-outdoors-1836120/";
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName(PRODUCT_NAME);
        product.setMaker(PRODUCT_MAKER);
        product.setPrice(PRODUCT_PRICE);
        product.setImageUrl(PRODUCT_IMAGE_URL);
    }

    @Test
    void getId() {
        assertThat(product.getId()).isEqualTo(1L);
    }

    @Test
    void setId() {
        product.setId(2L);
        assertThat(product.getId()).isEqualTo(2L);
    }

    @Test
    void getName() {
        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
    }

    @Test
    void setName() {
        product.setName(PRODUCT_NAME_FOR_UPDATING);
        assertThat(product.getName()).isEqualTo(PRODUCT_NAME_FOR_UPDATING);
    }

    @Test
    void getMaker() {
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
    }

    @Test
    void setMaker() {
        product.setMaker(PRODUCT_MAKER_FOR_UPDATING);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER_FOR_UPDATING);
    }

    @Test
    void getPrice() {
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
    }

    @Test
    void setPrice() {
        product.setPrice(PRODUCT_PRICE_FOR_UPDATING);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE_FOR_UPDATING);
    }

    @Test
    void getImageUrl() {
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL);
    }

    @Test
    void setImageUrl() {
        product.setImageUrl(PRODUCT_IMAGE_URL_FOR_UPDATING);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL_FOR_UPDATING);
    }
}