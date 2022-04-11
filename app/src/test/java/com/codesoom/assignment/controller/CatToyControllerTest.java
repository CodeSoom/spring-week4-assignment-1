package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CatToyController 클래스")
@DataJpaTest
public class CatToyControllerTest {

    private CatToyController catToyController;

    @Autowired
    private CatToyRepository catToyRepository;

    @BeforeEach
    void setUp() {

        CatToyService catToyService = new CatToyService(catToyRepository);

        catToyController = new CatToyController(catToyService);
    }

    @Nested
    @DisplayName("list 메소드는")
    class Describe_list {

        @Nested
        @DisplayName("주어진 고양이 장난감 수 만큼")
        class Context_givenCount {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {
                LongStream.rangeClosed(1, givenCount)
                        .mapToObj(CatToy::new)
                        .forEach(catToy -> catToyRepository.save(catToy));
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
