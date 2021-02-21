package com.codesoom.assignment.application;

import com.codesoom.assignment.ToyNotFoundException;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import com.codesoom.assignment.fixture.Given;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DataJpaTest
@DisplayName("ToyService의")
class ToyServiceTest {
    private ToyService toyService;
    private ToyRepository toyRepository;
    private Toy toy;

    private Given given = new Given();

    @BeforeEach
    void setUp() {
        toyRepository = mock(ToyRepository.class);
        toyService = new ToyService(toyRepository);

        toy = given.newToy();
    }

    private void assertCreatedToy(Toy toy) {
        assertThat(toy.getClass()).isEqualTo(Toy.class);
        assertThat(toy.getName()).isEqualTo(given.name);
        assertThat(toy.getMaker()).isEqualTo(given.maker);
        assertThat(toy.getPrice()).isEqualTo(given.price);
        assertThat(toy.getImageUrl()).isEqualTo(given.imageUrl);
    }

    private void assertModifiedToy(Toy toy) {
        assertThat(toy.getClass()).isEqualTo(Toy.class);
        assertThat(toy.getName()).isEqualTo(given.name + given.updatePostfixText);
        assertThat(toy.getMaker()).isEqualTo(given.maker + given.updatePostfixText);
        assertThat(toy.getPrice()).isEqualTo(given.price + given.updatePostfixNumber);
        assertThat(toy.getImageUrl()).isEqualTo(given.imageUrl + given.updatePostfixText);
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
                givenToyList = List.of(toy);

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
                givenId = given.savedId;

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
        @DisplayName("저장되지 않은 toy를 찾으려고하면")
        class Context_when_find_unsaved_toy {
            @BeforeEach
            void setUnsavedId() {
                givenId = given.unsavedId;
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

        @Nested
        @DisplayName("저장된 toy의 id를 가지고 있다면")
        class Context_with_saved_id {
            private Toy modified;

            @BeforeEach
            void setSavedId() {
                givenId = given.savedId;
                modifying = given.modifiedToy(givenId);

                given(toyRepository.findById(givenId)).willReturn(Optional.of(modifying));
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
        @DisplayName("저장되지 않은 toy를 수정하려고하면")
        class Context_when_update_unsaved_toy {
            @BeforeEach
            void setUnsavedId() {
                givenId = given.unsavedId;
                modifying = given.modifiedToy(givenId);

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

    @Nested
    @DisplayName("deleteToy 메소드는")
    class Describe_deleteToy {
        private Long givenId;

        @Nested
        @DisplayName("저장된 toy의 id를 가지고 있다면")
        class Context_with_saved_id {
            @BeforeEach
            void setSavedId() {
                givenId = given.savedId;
            }

            @Test
            @DisplayName("toy를 삭제한다.")
            void it_delete_toy() {
                toyService.deleteToy(givenId);

                verify(toyRepository).deleteById(any(Long.class));
            }
        }

        @Nested
        @DisplayName("저장되지 않은 toy를 지우려고하면")
        class Context_when_delete_unsaved_toy {
            @BeforeEach
            void setUnsavedId() {
                givenId = given.unsavedId;

                doThrow(EmptyResultDataAccessException.class).when(toyRepository).deleteById(givenId);
            }

            @Test
            @DisplayName("toy를 찾을 수 없다는 예외를 던집니다.")
            void it_throw_exception() {
                assertThatThrownBy(
                        () -> toyService.deleteToy(givenId),
                        "toy를 찾을 수 없다는 예외를 던져야 합니다."
                ).isInstanceOf(ToyNotFoundException.class);
            }
        }
    }
}
