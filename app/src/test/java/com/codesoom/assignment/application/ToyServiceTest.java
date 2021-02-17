package com.codesoom.assignment.application;

import com.codesoom.assignment.ToyNotFoundException;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
@DisplayName("ToyService의")
class ToyServiceTest {
    private final Long givenSavedToyId = 1L;
    private final Long givenUnsavedToyId = 100L;
    private final String givenToyName = "장난감 칼";
    private final String givenToyBrand = "코드숨";
    private final int givenToyPrice = 5000;
    private final String givenToyImageUrl = "https://cdn.shopify.com/s/files/1/0940/6942/products/DSC0243_800x.jpg";
    private final String givenUpdatePostfixText = "+";
    private final int givenUpdatePostfixNumber = 1;

    private ToyService toyService;
    private ToyRepository toyRepository;
    private Toy toy;

    @BeforeEach
    void setUp() {
        toyRepository = mock(ToyRepository.class);
        toyService = new ToyService(toyRepository);

        toy = new Toy(givenToyName, givenToyBrand, givenToyPrice, givenToyImageUrl);
        toy.setId(givenSavedToyId);
    }

    private void assertCreatedToy(Toy toy) {
        assertThat(toy.getClass()).isEqualTo(Toy.class);
        assertThat(toy.getName()).isEqualTo(givenToyName);
        assertThat(toy.getBrand()).isEqualTo(givenToyBrand);
        assertThat(toy.getPrice()).isEqualTo(givenToyPrice);
        assertThat(toy.getImageUrl()).isEqualTo(givenToyImageUrl);
    }

    private void assertModifiedToy(Toy toy) {
        assertThat(toy.getClass()).isEqualTo(Toy.class);
        assertThat(toy.getName()).isEqualTo(givenToyName);
        assertThat(toy.getBrand()).isEqualTo(givenToyBrand);
        assertThat(toy.getPrice()).isEqualTo(givenToyPrice);
        assertThat(toy.getImageUrl()).isEqualTo(givenToyImageUrl);
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

    @Nested
    @DisplayName("getToy 메소드는")
    class Describe_getToy {
        private Long givenId;

        @Nested
        @DisplayName("저장된 toy의 id를 가지고 있다면")
        class Context_with_saved_id {
            private Toy found;

            @BeforeEach
            void setSavedId() {
                givenId = givenSavedToyId;

                given(toyRepository.findById(givenId)).willReturn(Optional.of(toy));
            }

            @Test
            @DisplayName("toy를 리턴한다.")
            void it_return_toy() {
                found = toyService.getToy(givenId);

                verify(toyRepository).findById(givenId);

                assertCreatedToy(found);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 toy의 id를 가지고 있다면")
        class Context_with_unsaved_id {
            @BeforeEach
            void setUnsavedId() {
                givenId = givenUnsavedToyId;
            }

            @Test
            @DisplayName("toy를 찾을 수 없다는 exception을 던진다.")
            void it_throw_exception() {
                assertThatThrownBy(
                        () -> toyService.getToy(givenId),
                        "toy를 찾을 수 없다는 예외를 던져야 합니다."
                ).isInstanceOf(ToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createToy 메소드는")
    class Describe_createToy {
        private Toy created;

        @Test
        @DisplayName("추가된 toy를 리턴한다.")
        void it_return_created_toy() {
            given(toyRepository.save(toy)).will(invocation -> {
                return invocation.getArgument(0);
            });

            created = toyService.createToy(toy);

            verify(toyRepository).save(any(Toy.class));

            assertCreatedToy(created);
        }
    }

    @Nested
    @DisplayName("updateToy 메소드는")
    class Describe_updateToy {
        private Long givenId;
        private Toy modifying;

        @BeforeEach
        void setModifiedToy() {
            modifying = new Toy(
                    givenToyName + givenUpdatePostfixText,
                    givenToyBrand + givenUpdatePostfixText,
                    givenToyPrice + givenUpdatePostfixNumber,
                    givenToyImageUrl + givenUpdatePostfixText
            );
            modifying.setId(givenSavedToyId);
        }

        @Nested
        @DisplayName("저장된 toy의 id를 가지고 있다면")
        class Context_with_saved_id {
            private Toy modified;

            @BeforeEach
            void setSavedId() {
                givenId = givenSavedToyId;

                given(toyRepository.findById(givenId)).willReturn(Optional.of(toy));
            }

            @Test
            @DisplayName("수정된 toy를 리턴한다.")
            void it_return_modified_toy() {
                modified = toyService.updateToy(modifying);

                verify(toyRepository).findById(givenId);

                assertModifiedToy(modified);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 id를 가지고 있다면")
        class Context_with_unsaved_id {
            @BeforeEach
            void setUnsavedId() {
                givenId = givenUnsavedToyId;
                modifying.setId(givenId);

                given(toyRepository.findById(givenId)).willThrow(ToyNotFoundException.class);
            }

            @Test
            @DisplayName("toy를 찾을 수 없다는 exception을 던진다.")
            void it_throw_exception() {
                assertThatThrownBy(
                        () -> toyService.updateToy(modifying),
                        "toy를 찾을 수 없다는 예외를 던져야 합니다."
                ).isInstanceOf(ToyNotFoundException.class);
            }
        }
    }

    @Test
    void deleteToy() {
    }
}