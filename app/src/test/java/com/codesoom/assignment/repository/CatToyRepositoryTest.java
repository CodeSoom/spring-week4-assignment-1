package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.CatToy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.codesoom.assignment.constant.CatToyTestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CatToyRepositoryTest {

    @Autowired
    private CatToyRepository catToyRepository;

    private CatToy catToy;

    @BeforeEach
    void setup() {
        catToy = new CatToy(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL);
    }

    @Test
    @DisplayName("고양이 장난감을 생성 하여 반환한다.")
    void createCatToy() {
        // when
        CatToy saveCatToy = catToyRepository.save(catToy);

        // then
        assertThat(catToyRepository.findById(saveCatToy.id()).isPresent()).isTrue();

    }
}
