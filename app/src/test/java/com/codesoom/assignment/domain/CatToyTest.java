package com.codesoom.assignment.domain;

import com.codesoom.assignment.exception.CatToyInvalidFieldException;
import com.codesoom.assignment.exception.CatToyInvalidPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CatToy 객체 유닛 테스트")
class CatToyTest {
    private static final String NAME = "장난감 뱀";
    private static final Long PRICE = 5000L;
    private static final String MAKER = "애옹이네 장난감";
    private static final String IMAGE_URL = "https://cdn.pixabay.com/photo/2018/09/11/22/19/the-3670813_960_720.jpg";

    private static final String OTHER_NAME = "스크래처";
    private static final Long OTHER_PRICE = 8000L;
    private static final String OTHER_MAKER = "뽀떼";
    private static final String OTHER_IMAGE_URL = "https://www.biteme.co.kr/data/goods/21/06/24/1000005510/1000005510_detail_015.png";



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

    @DisplayName("빌더 패턴을 이용해 객체를 생성할 수 있습니다.")
    @Test
    void createFromBuilderPattern() {
        final CatToy sourceCatToy = CatToy.builder()
                .name(NAME)
                .maker(MAKER)
                .price(PRICE)
                .imageUrl(IMAGE_URL)
                .build();

        final CatToy targetCatToy = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThat(sourceCatToy).isEqualTo(targetCatToy);
    }

    @DisplayName("객체의 정보를 업데이트 할 수 있습니다.")
    @Test
    void updateCatToy() {
        final CatToy source = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);
        final CatToy target = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);

        source.update(target);

        assertThat(source).isEqualTo(target);
        assertThat(target.getName()).isEqualTo(OTHER_NAME);
        assertThat(target.getPrice()).isEqualTo(OTHER_PRICE);
        assertThat(target.getMaker()).isEqualTo(OTHER_MAKER);
        assertThat(target.getImageUrl()).isEqualTo(OTHER_IMAGE_URL);
    }

    @DisplayName("장난감의 정보를 공백 혹은 null로 업데이트 할 경우 예외가 발생합니다.")
    @ParameterizedTest
    @MethodSource("provideCatToyWithSpaceOrNull")
    void updateCatToyWithSpace(final CatToy target) {
        final CatToy source = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThatThrownBy(()-> source.update(target))
                .isInstanceOf(CatToyInvalidFieldException.class);

    }

    public static Stream<Arguments> provideCatToyWithSpaceOrNull() {
        return Stream.of(
                Arguments.of(CatToy.of("", OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL)),
                Arguments.of(CatToy.of(null, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL)),
                Arguments.of(CatToy.of(OTHER_NAME, "", OTHER_PRICE, OTHER_IMAGE_URL)),
                Arguments.of(CatToy.of(OTHER_NAME, null, OTHER_PRICE, OTHER_IMAGE_URL)),
                Arguments.of(CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, "")),
                Arguments.of(CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, null))
        );
    }

    @DisplayName("장난감의 가격을 잘 못 업데이트 할 경우 예외가 발생합니다.")
    @Test
    void updateCatToyWithInvalidPrice() {
        final CatToy source = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);
        final CatToy target = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(target, "price", -3000L);

        assertThatThrownBy(()-> source.update(target))
                .isInstanceOf(CatToyInvalidPriceException.class);


    }
}
