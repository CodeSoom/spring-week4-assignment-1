package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.service.CatToyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CatToyControllerTest {

    private static final long ID = 1L;
    private static final String NAME = "TEST NAME";
    private static final String MAKER = "TEST MAKER";
    private static final int PRICE = 10000;
    private static final String IMAGE_URL = "TEST IMAGE URL";

    private CatToyController catToyController;
    private CatToyService catToyService;

    @BeforeEach
    public void setUp() {
        catToyService = mock(CatToyService.class);

        CatToy catToy = new CatToy(ID, NAME, MAKER, PRICE, IMAGE_URL);
        List<CatToy> catToyList = new ArrayList<>();
        catToyList.add(catToy);

        given(catToyService.getCatToys()).willReturn(catToyList);
        given(catToyService.findCatToyById(ID)).willReturn(Optional.of(catToy));

        catToyController = new CatToyController(catToyService);
    }

    @Nested
    @DisplayName("getCatToys 메서드는")
    class getAllCatToys {
        @Test
        @DisplayName("고양이 장난감 목록 전체를 반환한다.")
        void getCatToys() {
            Assertions.assertEquals(catToyController.getCatToys().size(), 1);
        }
    }

    @Nested
    @DisplayName("findCatTiyById 메서드는")
    class findCatToyById {
        @Test
        @DisplayName("식별자에 해당하는 장난감을 반환한다.")
        void findCatToyById() {
            Assertions.assertEquals(catToyController.findCatToyById(1L).getName(), NAME);
        }
    }

    @Nested
    @DisplayName("registerCatToy 메서드는")
    class registerCatToy {
        CatToy givenCatToy = new CatToy(2L, "test name2", "test maker2", 20000, "test imageUrl2");

        @Test
        void registerCatToy() {
            catToyService.addCatToy(givenCatToy);

            Assertions.assertEquals(catToyService.getCatToys().size(), 2);
        }
    }
    @Test
    void updateCatToy() {
    }

    @Test
    void deleteCatToy() {
    }
}