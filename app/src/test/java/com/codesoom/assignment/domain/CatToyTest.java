package com.codesoom.assignment.domain;

import com.codesoom.assignment.exception.CatToyInvalidPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CatToy 객체 유닛 테스트")
class CatToyTest {
    private static final String NAME = "장난감 뱀";
    private static final Long PRICE = 5000L;
    private static final String MAKER = "애옹이네 장난감";
    private static final String IMAGE_URL = "https://cdn.pixabay.com/photo/2018/09/11/22/19/the-3670813_960_720.jpg";


    @DisplayName("객체를 생성할 수 있습니다.")
    @Test
    void create() {
        final CatToy catToy = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThat(catToy.getName()).isEqualTo(NAME);
        assertThat(catToy.getPrice()).isEqualTo(PRICE);
        assertThat(catToy.getMaker()).isEqualTo(MAKER);
        assertThat(catToy.getImageUrl()).isEqualTo(IMAGE_URL);
    }

    @DisplayName("유효하지 않은 가격으로 객체를 만들 수 없습니다.")
    @Test
    void createWithInvalidPrice() {

        assertThatThrownBy(() -> CatToy.of(NAME, MAKER, -5000L, IMAGE_URL)
        ).isInstanceOf(CatToyInvalidPriceException.class);
    }

    @DisplayName("객체는 동일성 비교를 할 수 있습니다.")
    @Test
    void sameness() {
        final CatToy source = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThat(source.equals(source)).isTrue();
        assertThat(source).hasSameHashCodeAs(source);
    }

    @DisplayName("객체의 동등성을 비교할 수 있습니다.")
    @Test
    void equals() {
        final CatToy source = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);

        final String otherName = "Other";
        final String otherMaker = "OtherMaker";
        final String otherUrl = "OtherUrl";
        final Long otherPrice = 3000L;

        assertThat(source).isEqualTo(CatToy.of(NAME, MAKER, PRICE, IMAGE_URL));
        assertThat(source).isNotEqualTo(CatToy.of(otherName, MAKER, PRICE, IMAGE_URL));
        assertThat(source).isNotEqualTo(CatToy.of(NAME, otherMaker, PRICE, IMAGE_URL));
        assertThat(source).isNotEqualTo(CatToy.of(NAME, MAKER, otherPrice, IMAGE_URL));
        assertThat(source).isNotEqualTo(CatToy.of(NAME, MAKER, PRICE, otherUrl));

    }

    @DisplayName("객체의 내용이 동일하더라도 객체가 다르면 동일하지도 동등하지도 않습니다.")
    @Test
    void equalsSameContentsAndOtherObject() {
        final CatToy source = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);
        final DogToy target = DogToy.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThat(source).isNotEqualTo(target);
    }


}
