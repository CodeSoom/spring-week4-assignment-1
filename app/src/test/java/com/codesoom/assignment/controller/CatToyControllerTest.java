package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ToyService;
import com.codesoom.assignment.domain.CatToy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CatToyController 컨트롤러 테스트")
class CatToyControllerTest {

    @MockBean
    private ToyService<CatToy> catToyService;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("전체 목록을 조회할 수 있습니다.")
    @Test
    void findAll() {

    }

    @DisplayName("존재하는 식별자를 이용해 장난감 상세정보를 조회할 수 있습니다.")
    @Test
    void findByExistsId() {

    }

    @DisplayName("존재하지 않는 식별자를 이용해 장난감 상세정보를 조회할 경우 예외가 발생합니다.")
    @Test
    void findByNotExistsId() {

    }

    @DisplayName("새로운 장난감을 생성할 수 있습니다.")
    @Test
    void createWithCatToy() {

    }

    @DisplayName("유효하지 못한 장난감 정보를 생성하려 하면 예외가 발생합니다.")
    @Test
    void createInvalidCatToy() {

    }

    @DisplayName("존재하는 식별자의 장난감 정보를 수정할 수 있습니다.")
    @Test
    void updateExistsIdWithValidCatToy() {

    }

    @DisplayName("존재하는 식별자의 장난감 정보를 잘 못된 정보로 수정하려 하면 예외가 발생합니다.")
    @Test
    void updateExistsIdWithInvalidCatToy() {

    }

    @DisplayName("존재하지 않는 식별자의 장난감 정보를 수정할 수 없습니다.")
    @Test
    void updateNotExistsId() {

    }

    @DisplayName("존재하는 식별자의 장난감 정보를 삭제할 수 있습니다.")
    @Test
    void deleteExistsId() {

    }

    @DisplayName("존재하지 않는 식별자의 장난감 정보를 삭제할 수 없습니다.")
    @Test
    void deleteNotExistsId() {

    }

}
