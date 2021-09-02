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

        catToyController = new CatToyController(catToyService);
    }

    @Nested
    @DisplayName("getCatToys 메서드는")
    class getAllCatToys {

        @Test
        @DisplayName("고양이 장난감 목록 전체를 반환한다.")
        void getCatToys() {
            Assertions.assertEquals(catToyController.getCatToys().size(), 2);
        }
    }

    @Test
    void findCatToyById() {
    }

    @Test
    void registerCatToy() {
    }

    @Test
    void updateCatToy() {
    }

    @Test
    void deleteCatToy() {
    }
}