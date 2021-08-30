package com.codesoom.assignment.models;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CatProduct model에 대한 테스트")
class CatProductTest {

    private static final Long ID = 1L;
    private static final String NAME = "TEST";
    private static final String MAKER = "GUCCI";
    private static final Long PRICE = 100000L;
    private static final String IMAGE = "image";


    private CatProduct catProduct;


    @BeforeEach
    void setUp() {
        catProduct = new CatProduct();
    }


    @Test
    @DisplayName("catProduct의 속성설정 테스트")
    public void settings(){
        catProduct.setId(ID);
        catProduct.setName(NAME);
        catProduct.setMaker(MAKER);
        catProduct.setPrice(PRICE);
        catProduct.setImage(IMAGE);

        assertThat(catProduct.getId()).isEqualTo(ID);
        assertThat(catProduct.getName()).isEqualTo(NAME);
        assertThat(catProduct.getMaker()).isEqualTo(MAKER);
        assertThat(catProduct.getPrice()).isEqualTo(PRICE);
        assertThat(catProduct.getImage()).isEqualTo(IMAGE);

    }
}
