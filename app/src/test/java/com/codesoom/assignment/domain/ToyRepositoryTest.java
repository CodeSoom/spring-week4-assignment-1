package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("ToyRepository클래스의")
class ToyRepositoryTest {
    private final Long givenUnsavedToyId = 100L;
    private final String givenToyName = "장난감 칼";
    private final String givenToyBrand = "코드숨";
    private final int givenToyPrice = 5000;
    private final String givenToyImageUrl = "https://cdn.shopify.com/s/files/1/0940/6942/products/DSC0243_800x.jpg";

    @Autowired
    private ToyRepository toyRepository;
    private Toy toy;

    private void assertToy(Toy toy) {
        assertThat(toy.getClass()).isEqualTo(Toy.class);
        assertThat(toy.getName()).isEqualTo(givenToyName);
        assertThat(toy.getBrand()).isEqualTo(givenToyBrand);
        assertThat(toy.getPrice()).isEqualTo(givenToyPrice);
        assertThat(toy.getImageUrl()).isEqualTo(givenToyImageUrl);
    }

    @BeforeEach
    void setUp() {
        toyRepository.deleteAll();
        toy = new Toy(givenToyName, givenToyBrand, givenToyPrice, givenToyImageUrl);
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("저장된 toy가 없다면")
        class Context_without_any_toy {
            @Test
            @DisplayName("비어있는 리스트를 리턴한다.")
            void it_return_empty_list() {
                assertThat(toyRepository.findAll().size()).isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("저장된 toy가 있다면")
        class Context_with_a_toy {
            @BeforeEach
            void setSavedToy() {
                toyRepository.save(toy);
            }

            @Test
            @DisplayName("toy 리스트를 리턴한다.")
            void it_return_toy_list() {
                assertThat(toyRepository.findAll().size()).isEqualTo(1);
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
        private Long givenId;

        @Nested
        @DisplayName("저장된 toy의 id를 가지고 있다면")
        class Context_with_saved_id {
            private Toy found;

            @BeforeEach
            void setSavedId() {
                givenId = toyRepository.save(toy).getId();
            }

            @Test
            @DisplayName("toy를 리턴한다.")
            void it_return_toy() {
                found = toyRepository.findById(givenId).orElseThrow();

                assertToy(found);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 toy를 찾으려고하면")
        class Context_when_find_unsaved_toy {
            @BeforeEach
            void setUnsavedId() {
                givenId = givenUnsavedToyId;
            }

            @Test
            @DisplayName("Optional.empty()를 리턴한다.")
            void it_return_empty() {
                assertThat(toyRepository.findById(givenId)).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        private int size;
        private Toy saved;

        @Nested
        @DisplayName("toy를 추가하고,")
        class It_add_toy {
            @BeforeEach
            void setSavedToy() {
                size = toyRepository.findAll().size();
                saved = toyRepository.save(toy);
            }

            @Test
            @DisplayName("추가된 toy를 리턴한다.")
            void it_return_added_toy() {
                assertToy(saved);
            }

            @Test
            @DisplayName("toy 리스트의 크기를 1 증가시킨다.")
            void it_count_up_toy_list_size() {
                assertThat(toyRepository.findAll().size()).isEqualTo(size + 1);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_deleteById {
        private Long givenId;

        @Nested
        @DisplayName("저장된 toy의 id를 가지고 있다면")
        class Context_with_saved_id {
            private int size;

            @BeforeEach
            void setSavedId() {
                givenId = toyRepository.save(toy).getId();
                size = toyRepository.findAll().size();
            }

            @Test
            @DisplayName("toy를 삭제한다.")
            void it_delete_toy() {
                toyRepository.deleteById(givenId);

                assertThat(toyRepository.findAll().size()).isEqualTo(size - 1);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 toy를 지우려고하면")
        class Context_when_delete_unsaved_toy {
            @BeforeEach
            void setUnsavedId() {
                givenId = givenUnsavedToyId;
            }

            @Test
            @DisplayName("데이터 접근 결과가 비어있다는 예외를 던집니다.")
            void it_throw_exception() {
                assertThatThrownBy(
                        () -> toyRepository.deleteById(givenId),
                        "데이터 접근 결과가 비어있다는 예외를 던져야 합니다."
                ).isInstanceOf(EmptyResultDataAccessException.class);
            }
        }
    }
}