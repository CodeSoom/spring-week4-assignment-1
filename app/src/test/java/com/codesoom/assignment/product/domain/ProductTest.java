package com.codesoom.assignment.product.domain;

import com.codesoom.assignment.ProvideInvalidProductArguments;
import com.codesoom.assignment.product.exception.ProductInvalidFieldException;
import com.codesoom.assignment.product.exception.ProductInvalidPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

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

@DisplayName("Product 객체 유닛 테스트")
class ProductTest {

    @DisplayName("객체를 생성할 수 있습니다.")
    @Test
    void create() {
        final Product product = Product.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThat(product.getName()).isEqualTo(NAME);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getImageUrl()).isEqualTo(IMAGE_URL);
    }

    @DisplayName("유효하지 않은 가격으로 객체를 만들 수 없습니다.")
    @ParameterizedTest
    @ValueSource(longs = {0L, -1L ,-5000L, Long.MIN_VALUE})
    void createWithInvalidPrice(Long invalidPrice) {

        assertThatThrownBy(() -> Product.of(NAME, MAKER, invalidPrice, IMAGE_URL)
        ).isInstanceOf(ProductInvalidPriceException.class);
    }

    @DisplayName("객체는 동일성 비교를 할 수 있습니다.")
    @Test
    void sameness() {
        final Product source = Product.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThat(source.equals(source)).isTrue();
        assertThat(source).hasSameHashCodeAs(source);
    }

    @DisplayName("객체의 동등성을 비교할 수 있습니다.")
    @Test
    void equals() {
        final Product source = Product.of(NAME, MAKER, PRICE, IMAGE_URL);

        final String otherName = "Other";
        final String otherMaker = "OtherMaker";
        final String otherUrl = "OtherUrl";
        final long otherPrice = 3000L;

        assertThat(source).isEqualTo(Product.of(NAME, MAKER, PRICE, IMAGE_URL))
                .isNotEqualTo(Product.of(otherName, MAKER, PRICE, IMAGE_URL))
                .isNotEqualTo(Product.of(NAME, otherMaker, PRICE, IMAGE_URL))
                .isNotEqualTo(Product.of(NAME, MAKER, otherPrice, IMAGE_URL))
                .isNotEqualTo(Product.of(NAME, MAKER, PRICE, otherUrl));

    }

    @DisplayName("객체의 내용이 동일하더라도 객체가 다르면 동일하지도 동등하지도 않습니다.")
    @Test
    void equalsSameContentsAndOtherObject() {
        final Product source = Product.of(NAME, MAKER, PRICE, IMAGE_URL);
        final DogToy target = DogToy.of(NAME, MAKER, PRICE, IMAGE_URL);

        assertThat(source).isNotEqualTo(target);
    }

    @DisplayName("객체의 정보를 업데이트 할 수 있습니다.")
    @Test
    void updateCatToy() {
        final Product source = Product.of(NAME, MAKER, PRICE, IMAGE_URL);
        final Product target = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);

        source.update(target);

        assertThat(source).isEqualTo(target);
        assertThat(target.getName()).isEqualTo(OTHER_NAME);
        assertThat(target.getPrice()).isEqualTo(OTHER_PRICE);
        assertThat(target.getMaker()).isEqualTo(OTHER_MAKER);
        assertThat(target.getImageUrl()).isEqualTo(OTHER_IMAGE_URL);
    }

    @DisplayName("상품의 정보를 공백 혹은 null로 업데이트 할 경우 예외가 발생합니다.")
    @ParameterizedTest
    @ArgumentsSource(ProvideInvalidProductArguments.class)
    void updateCatToyWithSpace(final List<Product> products) {
        final Product source = Product.of(NAME, MAKER, PRICE, IMAGE_URL);

        for (Product target : products) {
            assertThatThrownBy(() -> source.update(target))
                    .isInstanceOf(ProductInvalidFieldException.class);
        }
    }

    @DisplayName("상품의 가격을 잘 못 업데이트 할 경우 예외가 발생합니다.")
    @Test
    void updateCatToyWithInvalidPrice() {
        final Product source = Product.of(NAME, MAKER, PRICE, IMAGE_URL);
        final Product target = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(target, "price", -3000L);

        assertThatThrownBy(() -> source.update(target))
                .isInstanceOf(ProductInvalidPriceException.class);


    }
}
