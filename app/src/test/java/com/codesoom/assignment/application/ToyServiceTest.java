package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ToyServiceTest {
    private ToyService toyService;
    private ToyRepository toyRepository;

    private Toy toyFixture;
    private Toy newToyFixture;
    private List<Toy> toysFixture;

    private final static Long VALID_ID = 1L;
    private final static Long INVALID_ID = 100L;

    @BeforeEach
    void setup() {
        toyRepository = mock(ToyRepository.class);
        toyService = new ToyService(toyRepository);

        toyFixture = new Toy(
                VALID_ID,
                "애오옹",
                "난난",
                12345,
                "https://source.unsplash.com/random"
        );

        newToyFixture = new Toy(
                VALID_ID,
                "애오옹2",
                "난난2",
                123450,
                "https://source.unsplash.com/random"
        );

        toysFixture = new ArrayList<>();
        toysFixture.add(toyFixture);
    }

    @Nested
    @DisplayName("GetAll Service")
    class GetAllService {
        @BeforeEach
        void setup() {
            given(toyRepository.findAll()).willReturn(toysFixture);
        }

        @Test
        @DisplayName("calls findAll in repository and returns toys")
        void getToys() {
            List<Toy> result = toyService.getToys();

            verify(toyRepository).findAll();

            assertThat(result).hasSize(1);
        }
    }

    @Nested
    @DisplayName("Get Service")
    class GetService {
        @BeforeEach
        void setup() {
            given(toyRepository.findById(VALID_ID)).willReturn(Optional.of(toyFixture));
            given(toyRepository.findById(INVALID_ID)).willReturn(Optional.empty());
        }

        @Nested
        @DisplayName("With Valid Id")
        class WithValidId {
            @Test
            @DisplayName("calls findById in repository and returns toy")
            void getToyWithValidId() {
                Toy result = toyService.getToy(VALID_ID);

                verify(toyRepository).findById(VALID_ID);

                assertThat(result.getId()).isEqualTo(VALID_ID);
            }
        }

        @Nested
        @DisplayName("With Invalid Id")
        class WithInvalidId {
            @Test
            @DisplayName("throws NoSuchElementException")
            void getToyWithInvalidId() {
                assertThatThrownBy(() -> toyService.getToy(INVALID_ID))
                        .isInstanceOf(NoSuchElementException.class);
            }
        }

    }

    @Nested
    @DisplayName("Save Service")
    class SaveService {
        @BeforeEach
        void setup() {
            given(toyRepository.save(any(Toy.class))).willReturn(toyFixture);
        }

        @Test
        @DisplayName("calls save in repository and returns a created toy")
        void save() {
            Toy result = toyService.saveToy(toyFixture);

            verify(toyRepository).save(any(Toy.class));

            assertThat(result.getName()).isEqualTo(toyFixture.getName());
            assertThat(result.getMaker()).isEqualTo(toyFixture.getMaker());
            assertThat(result.getPrice()).isEqualTo(toyFixture.getPrice());
            assertThat(result.getImageUrl()).isEqualTo(toyFixture.getImageUrl());
        }
    }

    @Nested
    @DisplayName("Update Service")
    class UpdateService {
        @BeforeEach
        void setup() {
            given(toyRepository.findById(VALID_ID)).willReturn(Optional.of(toyFixture));
            given(toyRepository.findById(INVALID_ID)).willReturn(Optional.empty());
        }

        @Nested
        @DisplayName("With valid id")
        class WithValidId {
            @Test
            @DisplayName("calls findById in repository and returns an updated toy")
            void updateWithValidId() {
                Toy result = toyService.updateToy(VALID_ID, newToyFixture);

                verify(toyRepository).findById(VALID_ID);

                assertThat(result.getId()).isEqualTo(toyFixture.getId());
                assertThat(result.getName()).isEqualTo(newToyFixture.getName());
                assertThat(result.getMaker()).isEqualTo(newToyFixture.getMaker());
                assertThat(result.getPrice()).isEqualTo(newToyFixture.getPrice());
                assertThat(result.getImageUrl()).isEqualTo(newToyFixture.getImageUrl());
            }
        }

        @Nested
        @DisplayName("With invalid id")
        class WithInvalidId {
            @Test
            @DisplayName("throws NoSuchElementException")
            void updateWithInvalidId() {
                assertThatThrownBy(() -> toyService.updateToy(INVALID_ID, newToyFixture))
                        .isInstanceOf(NoSuchElementException.class);
            }
        }
    }

    @Nested
    @DisplayName("Delete Service")
    class DeleteService {
        @Nested
        @DisplayName("With valid id")
        class WithValidId {
            @BeforeEach
            void setup() {
                given(toyRepository.findById(VALID_ID)).willReturn(Optional.of(toyFixture));
            }

            @Test
            @DisplayName("calls findById and delete in repository")
            void deleteWithValidId() {
                toyService.deleteToy(VALID_ID);

                verify(toyRepository).findById(VALID_ID);
                verify(toyRepository).deleteById(VALID_ID);
            }
        }

        @Nested
        @DisplayName("With invalid id")
        class WithInvalidId {
            @BeforeEach
            void setup() {
                given(toyRepository.findById(INVALID_ID)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("throws NoSuchElementException")
            void deleteWithInvalidId() {
                assertThatThrownBy(() -> toyService.deleteToy(INVALID_ID))
                        .isInstanceOf(NoSuchElementException.class);
            }
        }
    }
}
