package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CatToy 클래스")
class CatToyTest {

    private final Long ID = 1L;
    private final String NAME = "이름";
    private final String MAKER = "메이커";
    private final Long PRICE = 1000L;
    private final String IMAGE_URI = "이미지 URL";

    @Test
    @DisplayName("CatToy의 프로퍼티를 검사합니다.")
    void verify_property() {
        CatToy catToy = new CatToy();

        catToy.setId(ID);
        catToy.setName(NAME);
        catToy.setMaker(MAKER);
        catToy.setPrice(PRICE);
        catToy.setImageURI(IMAGE_URI);

        assertThat(catToy.getId()).isEqualTo(ID);
        assertThat(catToy.getName()).isEqualTo(NAME);
        assertThat(catToy.getMaker()).isEqualTo(MAKER);
        assertThat(catToy.getPrice()).isEqualTo(PRICE);
        assertThat(catToy.getImageURI()).isEqualTo(IMAGE_URI);
    }
}