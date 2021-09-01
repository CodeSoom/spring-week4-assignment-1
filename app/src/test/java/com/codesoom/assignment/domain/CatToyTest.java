package com.codesoom.assignment.domain;

import com.codesoom.assignment.ProvideInvalidCatToyArguments;
import com.codesoom.assignment.exception.CatToyInvalidFieldException;
import com.codesoom.assignment.exception.CatToyInvalidPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.test.util.ReflectionTestUtils;

import static com.codesoom.assignment.Constant.IMAGE_URL;
import static com.codesoom.assignment.Constant.MAKER;
import static com.codesoom.assignment.Constant.NAME;
import static com.codesoom.assignment.Constant.OTHER_IMAGE_URL;
import static com.codesoom.assignment.Constant.OTHER_MAKER;
import static com.codesoom.assignment.Constant.OTHER_NAME;
import static com.codesoom.assignment.Constant.OTHER_PRICE;
import static com.codesoom.assignment.Constant.PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CatToy 객체 유닛 테스트")
class CatToyTest {

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
        final long otherPrice = 3000L;

        assertThat(source).isEqualTo(CatToy.of(NAME, MAKER, PRICE, IMAGE_URL));
        assertThat(source).isNotEqualTo(CatToy.of(otherName, MAKER, PRICE, IMAGE_URL));
        assertThat(source).isNotEqualTo(CatToy.of(NAME, otherMaker, PRICE, IMAGE_URL));
        assertThat(source).isNotEqualTo(CatToy.of(NAME, MAKER, otherPrice, IMAGE_URL));
        assertThat(source).isNotEqualTo(CatToy.of(NAME, MAKER, PRICE, otherUrl));

    }

    @DisplayName("객체의 내용이 동일하더라도 객체가 다르면 동일하지도 동등하지도 않습니다.")
    @Test
    void equalsSameContentsAndOtherObject() {
        final Product source = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);
        final Product target = DogToy.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThat(source).isNotEqualTo(target);
    }

    @DisplayName("객체의 정보를 업데이트 할 수 있습니다.")
    @Test
    void updateCatToy() {
        final Product source = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);
        final Product target = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);

        source.update(target);

        assertThat(source).isEqualTo(target);
        assertThat(target.getName()).isEqualTo(OTHER_NAME);
        assertThat(target.getPrice()).isEqualTo(OTHER_PRICE);
        assertThat(target.getMaker()).isEqualTo(OTHER_MAKER);
        assertThat(target.getImageUrl()).isEqualTo(OTHER_IMAGE_URL);
    }

    @DisplayName("장난감의 정보를 공백 혹은 null로 업데이트 할 경우 예외가 발생합니다.")
    @ParameterizedTest
    @ArgumentsSource(ProvideInvalidCatToyArguments.class)
    void updateCatToyWithSpace(final CatToy target) {
        final Product source = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThatThrownBy(()-> source.update(target))
                .isInstanceOf(CatToyInvalidFieldException.class);

    }

    @DisplayName("장난감의 가격을 잘 못 업데이트 할 경우 예외가 발생합니다.")
    @Test
    void updateCatToyWithInvalidPrice() {
        final Product source = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);
        final Product target = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(target, "price", -3000L);

        assertThatThrownBy(()-> source.update(target))
                .isInstanceOf(CatToyInvalidPriceException.class);


    }
}
