package com.codesoom.assignment.application;

import com.codesoom.assignment.dto.CatToyModel;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import com.codesoom.assignment.repository.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.codesoom.assignment.constant.CatToyTestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class CatToyServiceTest {

    private CatToyService catToyService;
    private CatToyModel catToyModel;

    @Autowired
    private CatToyRepository catToyRepository;

    @BeforeEach
    void setup() {
        catToyService = new CatToyService(catToyRepository);
        catToyModel = new CatToyModel(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL);
    }

    @Test
    @DisplayName("고양이 장난감을 생성 하여 반환한다.")
    void createCatToy() {
        // when
        CatToyModel catToy = catToyService.createCatToy(catToyModel);

        // then
        assertThat(catToy.name()).isEqualTo(TOY_NAME);
        assertThat(catToy.maker()).isEqualTo(TOY_MAKER);

    }

    @Test
    @DisplayName("고양이 장난감 정보를 가져온다")
    void findCatToy() {
        // given
        CatToyModel catToy = catToyService.createCatToy(catToyModel);

        // when
        CatToyModel selectToy = catToyService.selectCatToy(catToy.id());

        // then
        assertThat(selectToy.name()).isEqualTo(TOY_NAME);
        assertThat(selectToy.maker()).isEqualTo(TOY_MAKER);

    }

    @Test
    @DisplayName("고양이 장난감을 검색 - 검색 실패")
    void selectCatToyNotExists() {
        // then
        assertThatThrownBy(() -> catToyService.selectCatToy(NOT_EXISTS_ID))
                .isInstanceOf(CatToyNotFoundException.class);
    }
}
