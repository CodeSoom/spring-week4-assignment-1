package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
@DisplayName("ToyService의")
class ToyServiceTest {
    private final Long givenUnsavedToyId = 100L;
    private final String givenToyName = "장난감 칼";
    private final String givenToyBrand = "코드숨";
    private final int givenToyPrice = 5000;
    private final String givenToyImageUrl = "https://cdn.shopify.com/s/files/1/0940/6942/products/DSC0243_800x.jpg";

    private ToyService toyService;
    private ToyRepository toyRepository;
    private Toy toy;

    @BeforeEach
    void setUp() {
        toyRepository = mock(ToyRepository.class);
        toyService = new ToyService(toyRepository);

        toy = new Toy(givenToyName, givenToyBrand, givenToyPrice, givenToyImageUrl);
    }

    @Nested
    @DisplayName("getToys 메서드")
    class Describe_getToys {
        @Nested
        @DisplayName("저장된 toy가 없다면")
        class Context_without_any_toy {
            @BeforeEach
            void setGiven() {
                given(toyRepository.findAll()).willReturn(List.of());
            }

            @Test
            @DisplayName("비어있는 리스트를 리턴한다.")
            void it_return_empty_list() {
                assertThat(toyService.getToys()).isEmpty();

                verify(toyRepository).findAll();
            }
        }

        @Nested
        @DisplayName("저장된 toy가 있다면")
        class Context_with_toy {
            private List<Toy> givenToyList;
            @BeforeEach
            void setSavedToy() {
                givenToyList = new ArrayList<Toy>();
                givenToyList.add(toy);

                given(toyRepository.findAll()).willReturn(givenToyList);
            }

            @Test
            void it_return_toy_list() {
                assertThat(toyService.getToys()).isEqualTo(givenToyList);

                verify(toyRepository).findAll();
            }
        }
    }

    @Test
    void getToys() {
    }

    @Test
    void getToy() {
    }

    @Test
    void createToy() {
    }

    @Test
    void updateToy() {
    }

    @Test
    void deleteToy() {
    }
}