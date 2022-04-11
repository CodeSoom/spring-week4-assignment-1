package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.dto.CatToySaveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("CatToyController 클래스")
public class CatToyControllerTest {

    private static final String TEST_MAKER = "MAKER";
    private static final Integer TEST_PRICE = 1000;
    private static final String TEST_IMAGE_PATH = "/image/test.jpg";

    @InjectMocks
    private CatToyController catToyController;

    @Mock
    private CatToyService catToyService;

    @BeforeEach
    void setUp() {
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
                List<CatToy> catToys = LongStream.rangeClosed(1, givenCount)
                        .mapToObj(CatToy::new)
                        .collect(Collectors.toList());

                given(catToyService.getCatToys()).willReturn(catToys);
            }

            @Test
            @DisplayName("고양이 장난감 목록을 리턴한다.")
            void it_return_toys() {
                List<CatToy> catToys = catToyController.list();
                assertThat(catToys).hasSize(givenCount);
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {

        @Nested
        @DisplayName("고양이 장난감 등록에 필요한 데이터가 주어진다면")
        class Context_valid {

            final CatToySaveDto source = new CatToySaveDto(TEST_MAKER, TEST_PRICE, TEST_IMAGE_PATH);

            @BeforeEach
            void setUp() {
                CatToy catToy = new CatToy(1L, TEST_MAKER, TEST_PRICE, TEST_IMAGE_PATH);
                given(catToyService.saveCatToy(source)).willReturn(catToy);
            }

            @Test
            @DisplayName("고양이 장난감을 생성하고 리턴한다.")
            void it_save_and_return_catToy() {

                CatToy catToy = catToyController.save(source);

                assertAll(
                        () -> assertThat(catToy.getId()).isNotNull(),
                        () -> assertThat(catToy.getMaker()).isEqualTo(TEST_MAKER),
                        () -> assertThat(catToy.getPrice()).isEqualTo(TEST_PRICE),
                        () -> assertThat(catToy.getImagePath()).isEqualTo(TEST_IMAGE_PATH)
                );
            }
        }
    }
}
