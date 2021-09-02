package com.codesoom.assignment.domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.Entity;
import javax.persistence.Id;

import static com.codesoom.assignment.constant.CatToyTestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

class CatToyTest {
    @Test
    @DisplayName("고양이 장난감 생성")
    void createCatToy() {
        // when
        CatToy catToy = new CatToy(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL);

        // then
        assertThat(catToy).isEqualTo(new CatToy(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL));
    }

    @Test
    @DisplayName("고양이 장난감 Equals And HashCode")
    void catToyEqualsAndHashCode() {
        // EqualsVerifier 사용하여 EqualsAndHashCode 자동 테스트
        EqualsVerifier
                .simple()
                .forClass(CatToy.class)
                .withIgnoredAnnotations(Entity.class, Id.class)
                .verify();
    }
}
