package com.codesoom.assignment.catToy.application;

import com.codesoom.assignment.catToy.ToyNotFoundException;
import com.codesoom.assignment.catToy.domain.CatToy;
import com.codesoom.assignment.catToy.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("CatToyService 클래스")
class CatToyServiceTest {

    private CatToyService toyService;
    private CatToyRepository toyRepo;
    private List<CatToy> toys;
    private CatToy catToy;

    private static final Long REGISTERED_ID = 1L;
    private static final Long NOT_REGISTERED_ID = 100L;
    private static final String TOYS_MAKER = "김애용이";

    @BeforeEach
    void setUp() {
        toyRepo = mock(CatToyRepository.class);
        toyService = new CatToyService(toyRepo);
        toys = new ArrayList<CatToy>();
        catToy = new CatToy();
        catToy.setMaker(TOYS_MAKER);
    }

    @Nested
    @DisplayName("getToys 메서드는")
    class Describe_getToys{

        @Nested
        @DisplayName("등록된 장난감이 있다면")
        class Context_with_something{
            @BeforeEach
            void registerToy() {
                given(toyRepo.findAll()).willReturn(toys);
            }

            @Test
            @DisplayName("모든 장난감을 리턴합니다.")
            void it_return_all_toy() {
                assertThat(toyService.getToys()).hasSize(toys.size());
                verify(toyRepo).findAll();
            }
        }

        @Nested
        @DisplayName("등록된 장난감이 없다면")
        class Context_with_nothing {
            @BeforeEach
            void setUp() {
                given(toyRepo.findAll())
                        .willReturn(new ArrayList<CatToy>());
            }

            @Test
            @DisplayName("빈 List를 리턴합니다.")
            void it_return_empty() {
                assertThat(toyService.getToys()).isEmpty();
                verify(toyRepo).findAll();
            }
        }
    }

    @Nested
    @DisplayName("getDetailToy 메서드는")
    class Describe_getDetailToy{

        @Nested
        @DisplayName("등록된 ID값이 주어지면")
        class Context_with_registered_id {
            @BeforeEach
            void setUp() {
                given(toyRepo.findById(REGISTERED_ID))
                        .willReturn(Optional.of(catToy));
            }

            @Test
            @DisplayName("해당 ID의 CatToy를 리턴합니다.")
            void it_return_registered_toy() {
                assertThat(toyService.getDetailToy(REGISTERED_ID).getMaker()).isEqualTo(TOYS_MAKER);
                verify(toyRepo).findById(REGISTERED_ID);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_id {

            @Test
            @DisplayName("ToyNotFound 예외를 던집니다.")
            void it_throw_exception() {
                assertThatThrownBy(() -> toyService.getDetailToy(NOT_REGISTERED_ID))
                        .isInstanceOf(ToyNotFoundException.class);

                verify(toyRepo).findById(NOT_REGISTERED_ID);
            }
        }
    }


    @Nested
    @DisplayName("createToy")
    class Describe_createToy{

    }

    @Nested
    @DisplayName("updateToy")
    class Describe_updateToy{

    }

    @Nested
    @DisplayName("deleteToy")
    class Describe_deleteToy{

    }
}
