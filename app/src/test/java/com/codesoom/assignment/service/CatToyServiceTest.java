package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;


@Nested
@DisplayName("CatToyService 클래스")
class CatToyServiceTest {



    @Nested
    @DisplayName("getCatToys 메서드는")
    class getAllCatToys {
        @Test
        @DisplayName("고양이 장난감 목록 전체를 반환한다.")
        void getCatToys() {
        }
    }
    @Test
    void getCatToys() {
    }

    @Test
    void findCatToyById() {
    }

    @Test
    void addCatToy() {
    }

    @Test
    void updateCatToy() {
    }

    @Test
    void deleteCatToyById() {
    }
}