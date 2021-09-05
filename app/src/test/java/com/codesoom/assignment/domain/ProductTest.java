package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductModel;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.Entity;
import javax.persistence.Id;

import static com.codesoom.assignment.constant.ProductTestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    private Product catToy;
    private ProductModel productModel;

    @BeforeEach
    void setup(){
        catToy = new Product(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL);
        productModel = new ProductModel(CHANGE_NAME, CHANGE_MAKER, CHANGE_PRICE, CHANGE_IMAGE_URL);
    }

    @Test
    @DisplayName("고양이 장난감 생성")
    void createCatToy() {
        // when
        Product catToy = new Product(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL);

        // then
        assertThat(catToy).isEqualTo(new Product(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL));
    }

    @Test
    @DisplayName("고양이 장난감 Equals And HashCode")
    void catToyEqualsAndHashCode() {
        // EqualsVerifier 사용하여 EqualsAndHashCode 자동 테스트
        EqualsVerifier
                .simple()
                .forClass(Product.class)
                .withIgnoredAnnotations(Entity.class, Id.class)
                .verify();
    }

    @Test
    @DisplayName("고양이 장난감 정보 변경")
    void changeCatToy() {
        // when
        catToy.changeProduct(productModel);

        // then
        assertThat(catToy).isEqualTo(new Product(CHANGE_NAME, CHANGE_MAKER, CHANGE_PRICE, CHANGE_IMAGE_URL));
    }

    @Test
    @DisplayName("고양이 장난감 이름, 가격 변경")
    void changeCatToyNameAndPrice() {
        // given
        ProductModel nameChangeModel = new ProductModel(CHANGE_NAME, "", CHANGE_PRICE, "");

        // when
        catToy.changeProduct(nameChangeModel);

        // then
        assertThat(catToy).isEqualTo(new Product(CHANGE_NAME, TOY_MAKER, CHANGE_PRICE, IMAGE_URL));
    }

    @Test
    @DisplayName("고양이 장난감 메이커, 이미지 변경")
    void changeCatToyName() {
        // given
        ProductModel nameChangeModel = new ProductModel("", CHANGE_MAKER, null, CHANGE_IMAGE_URL);

        // when
        catToy.changeProduct(nameChangeModel);

        // then
        assertThat(catToy).isEqualTo(new Product(TOY_NAME, CHANGE_MAKER, PRICE, CHANGE_IMAGE_URL));
    }

    @Test
    @DisplayName("고양이 장난감 정보 변경 실패 - null")
    void changeCatToyFail() {
        // when
        // then
        assertThatThrownBy(() -> catToy.changeProduct(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
