package com.codesoom.assignment.domain;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CatProduct model에 대한 테스트")
class ProductTest {

    private static final Long ID = 1L;
    private static final String NAME = "TEST";
    private static final String MAKER = "GUCCI";
    private static final Long PRICE = 100000L;
    private static final String IMAGE = "image";


    private Product product;


    @BeforeEach
    void setUp() {
        product = new Product();
    }


    @Test
    @DisplayName("catProduct의 속성설정 테스트")
    public void settings(){
        product.setId(ID);
        product.setName(NAME);
        product.setMaker(MAKER);
        product.setPrice(PRICE);
        product.setImage(IMAGE);

        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImage()).isEqualTo(IMAGE);

    }
}
