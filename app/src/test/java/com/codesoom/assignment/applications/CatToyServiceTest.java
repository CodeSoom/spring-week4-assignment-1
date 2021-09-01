package com.codesoom.assignment.applications;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.dto.CatToyNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;

@DisplayName("CatToyServiceTest 클래스")
class CatToyServiceTest {
    private CatToyService catToyService;

    @Mock
    private CatToyRepository catToyRepository;

    private CatToy catToy1;
    private CatToy catToy2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        catToy1 = new CatToy(1L, "toy1", "maker1", 1000L, "toy1.jpg");
        catToy2 = new CatToy(2L, "toy2", "maker2", 2000L, "toy2.jpg");

        catToyService = new CatToyService(catToyRepository);
    }

    @Nested
    @DisplayName("save 메소드")
    class Describe_save {

        @BeforeEach
        void setUp() {
            given(catToyRepository.save(catToy1)).willReturn(catToy1);
        }

        @Test
        @DisplayName("새로운 catToy를 반환합니다.")
        void it_return_new_catToy() {
            assertThat(catToyService.save(catToy1)).isEqualTo(catToy1);
        }
    }

    @Nested
    @DisplayName("findAll 메소드")
    class Describe_findAll {

        private List<CatToy> givenCatToys;

        @BeforeEach
        void setUp() {
            givenCatToys = List.of(catToy1, catToy2);

            given(catToyRepository.findAll()).willReturn(givenCatToys);
        }

        @Test
        @DisplayName("모든 catToy를 반환합니다.")
        void it_return_all_catToy() {
            assertThat(catToyService.findAll()).isEqualTo(givenCatToys);
        }
    }

    @Nested
    @DisplayName("findById 메소드")
    class Describe_findById {

        @Nested
        @DisplayName("해당되는 id의 catToy가 있다면")
        class Context_with_valid_id {

            private final Long VALID_ID = 1L;

            @BeforeEach
            void setUp() {
                given(catToyRepository.findById(VALID_ID)).willReturn(Optional.of(catToy1));
            }

            @Test
            @DisplayName("해당 Id의 catToy를 반환합니다.")
            void it_return_catToy() {
                assertThat(catToyService.findById(VALID_ID)).isEqualTo(catToy1);
            }
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 없다면")
        class Context_with_invalid_id {

            private final Long INVALID_ID = 100L;

            @BeforeEach
            void setUp() {
                willThrow(new CatToyNotFoundException(INVALID_ID)).given(catToyRepository).findById(INVALID_ID);
            }

            @Test
            @DisplayName("CatToyNotFoundException을 던집니다.")
            void it_throw_CatToyNotFoundException() {
                assertThatThrownBy(() -> catToyService.findById(INVALID_ID))
                        .isInstanceOf(CatToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드")
    class Describe_update {

        private final Long VALID_ID = 1L;

        @BeforeEach
        void setUp() {
            willDoNothing().given(catToyRepository).update(any(Long.class), any(CatToy.class));
        }

        @Test
        @DisplayName("catToryRepository의 update를 호출합니다.")
        void it_call_update() {
            catToyService.update(VALID_ID, catToy1);
            verify(catToyRepository).update(any(Long.class), any(CatToy.class));
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 없다면")
        class Context_with_invalid_id {

            private final Long INVALID_ID = 100L;

            @BeforeEach
            void setUp() {
                willThrow(new CatToyNotFoundException(INVALID_ID)).given(catToyRepository).update(eq(INVALID_ID), any(CatToy.class));
            }

            @Test
            @DisplayName("CatToyNotFoundException을 던집니다.")
            void it_throw_CatToyNotFoundException() {
                assertThatThrownBy(() -> catToyRepository.update(INVALID_ID, catToy2))
                        .isInstanceOf(CatToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드")
    class Describe_deleteById {

        private final Long VALID_ID = 1L;

        @BeforeEach
        void setUp() {
            willDoNothing().given(catToyRepository).deleteById(any(Long.class));
        }

        @Test
        @DisplayName("catToryRepository의 update를 호출합니다.")
        void it_call_update() {
            catToyService.deleteById(VALID_ID);
            verify(catToyRepository).deleteById(any(Long.class));
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 없다면")
        class Context_with_invalid_id {

            private final Long INVALID_ID = 100L;

            @BeforeEach
            void setUp() {
                willThrow(new CatToyNotFoundException(INVALID_ID)).given(catToyRepository).deleteById(INVALID_ID);
            }

            @Test
            @DisplayName("CatToyNotFoundException을 던집니다.")
            void it_throw_CatToyNotFoundException() {
                assertThatThrownBy(() -> catToyService.deleteById(INVALID_ID))
                        .isInstanceOf(CatToyNotFoundException.class);
            }
        }
    }
}