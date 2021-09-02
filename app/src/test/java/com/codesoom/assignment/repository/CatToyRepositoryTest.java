package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.dto.CatToyModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.codesoom.assignment.constant.CatToyTestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CatToyRepositoryTest {

    @Autowired
    private CatToyRepository catToyRepository;

    private CatToy catToy;
    private CatToyModel changeCatToyModel;
    private Long id;

    @BeforeEach
    void setup(){
        catToy = new CatToy(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL);
        changeCatToyModel = new CatToyModel(id, CHANGE_NAME, CHANGE_MAKER, CHANGE_PRICE, CHANGE_IMAGE_URL);
        id = saveCatToyId();
    }

    private Long saveCatToyId() {
        return catToyRepository.save(catToy).id();
    }

    @Test
    @DisplayName("고양이 장난감을 생성 하여 반환한다.")
    void createCatToy() {
        // when
        CatToy saveCatToy = catToyRepository.save(catToy);

        // then
        assertThat(catToyRepository.findById(saveCatToy.id()).isPresent()).isTrue();

    }

    @Test
    @DisplayName("고양이 장난감 리스트를 검색하여 반환한다.")
    void selectCatToyList() {
        // when
        List<CatToy> catToys = catToyRepository.findAll();

        // then
        assertThat(catToys).isNotEmpty();
    }

    @Test
    @DisplayName("고양이 장난감을 검색하여 반환한다.")
    void selectCatToy() {
        // when
        CatToy selectCatToy = catToyRepository.findById(id).get();

        // then
        assertThat(selectCatToy).isEqualTo(catToy);
    }

    @Test
    @DisplayName("고양이 장난감을 수정하여 반환한다.")
    void modifyCatToy() {
        // given
        CatToy selectCatToy = catToyRepository.findById(id).get();

        // when
        selectCatToy.changeCatToy(changeCatToyModel);
        catToyRepository.save(selectCatToy);

        // then
        assertThat(selectCatToy.name()).isEqualTo(CHANGE_NAME);
        assertThat(selectCatToy.maker()).isEqualTo(CHANGE_MAKER);
    }

    @Test
    @DisplayName("고양이 장난감을 삭제")
    void deleteCatToy() {
        // when
        catToyRepository.deleteById(id);

        // then
        assertThat(catToyRepository.findById(id)).isEmpty();
    }
}
