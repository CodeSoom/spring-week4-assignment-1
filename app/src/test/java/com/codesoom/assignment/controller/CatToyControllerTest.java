package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("고양이 장난감 Controller 유닛 테스트")
public class CatToyControllerTest {

    private CatToyController catToyController;

    @BeforeEach
    void setUp() {
        catToyController = new CatToyController();
    }

    @Nested
    @DisplayName("고양이 장난감 목록 조회 시")
    class Describe_list {

        @Nested
        @DisplayName("주어진 수 만큼")
        class Context_givenCount {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {

            }

            @Test
            @DisplayName("고양이 장난감 목록을 리턴한다.")
            void it_return_toys() {
                List<CatToy> catToys = catToyController.list();
                assertThat(catToys).hasSize(givenCount);
            }
        }
    }
}
