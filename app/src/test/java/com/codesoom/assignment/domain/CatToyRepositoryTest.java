package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.CatToyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CatToyRepositoryTest 클래스")
class CatToyRepositoryTest {

    private CatToyRepository catToyRepository;
    private CatToy catToy1;
    private CatToy catToy2;
    private CatToy catToy3;

    @BeforeEach
    void setUp() {
        catToy1 = new CatToy(1L, "toy1", "maker1", 1000L, "toy1.jpg");
        catToy2 = new CatToy(2L, "toy2", "maker2", 2000L, "toy2.jpg");
        catToy3 = new CatToy(3L, "toy3", "maker3", 3000L, "toy3.jpg");

        catToyRepository = new CatToyRepository();
    }

    @Nested
    @DisplayName("save 메소드")
    class Describe_save {

        @Test
        @DisplayName("catToy가 Repository에 추가됩니다.")
        void it_add_catToy() {
            assertThat(catToyRepository.findAll()).hasSize(0);
            catToyRepository.save(catToy1);
            assertThat(catToyRepository.findAll()).hasSize(1);
        }

        @Test
        @DisplayName("새로운 catToy를 반환합니다.")
        void it_return_new_catToy() {
            CatToy newCatToy = catToyRepository.save(catToy1);
            assertThat(newCatToy.getName()).isEqualTo(catToy1.getName());
            assertThat(newCatToy.getMaker()).isEqualTo(catToy1.getMaker());
            assertThat(newCatToy.getPrice()).isEqualTo(catToy1.getPrice());
            assertThat(newCatToy.getImageURI()).isEqualTo(catToy1.getImageURI());
        }
    }

    @Nested
    @DisplayName("findAll 메소드")
    class Describe_findAll {

        @Nested
        @DisplayName("catToy 데이터가 없다면")
        class Context_has_no_catToy {

            @Test
            @DisplayName("빈 데이터를 반환합니다.")
            void it_return_empty_catToys() {
                List<CatToy> catToys = catToyRepository.findAll();

                assertThat(catToys).hasSize(0);
            }
        }

        @Nested
        @DisplayName("catToy 데이터가 저장되어 있다면")
        class Context_has_catToy {

            private List<CatToy> givenCatToys;

            @BeforeEach
            void prepare() {
                givenCatToys = List.of(catToy1, catToy2, catToy3);

                for (CatToy toy: givenCatToys) {
                    catToyRepository.save(toy);
                }
            }

            @Test
            @DisplayName("저장되어 있는 catToy를 반환합니다.")
            void it_return_all_catToy() {
                int index = 0;
                List<CatToy> catToys = catToyRepository.findAll();

                assertThat(catToys).hasSize(3);

                for (CatToy toy: givenCatToys) {
                    assertThat(catToys.get(index)).isEqualTo(toy);
                    index ++;
                }

            }
        }
    }

    @Nested
    @DisplayName("findById 메소드")
    class Describe_findById {

        @BeforeEach
        void prepare() {
            catToyRepository.save(catToy1);
        }

        @Test
        @DisplayName("해당 Id의 Optional<CatToy>를 반환합니다.")
        void it_return_optional_catToy() {
            assertThat(catToyRepository.findById(1L)).isEqualTo(Optional.of(catToy1));
        }
    }

    @Nested
    @DisplayName("update 메소드")
    class Describe_update {

        @BeforeEach
        void prepare() {
            catToyRepository.save(catToy1);
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 있다면")
        class Context_with_valid_id {

            @Test
            @DisplayName("해당 Id의 catToy를 수정합니다.")
            void it_update_catToy() {
                catToyRepository.update(1L, catToy2);

                CatToy updatedCatToy = catToyRepository.findById(1L).orElseThrow();

                assertThat(updatedCatToy.getName()).isEqualTo(catToy2.getName());
                assertThat(updatedCatToy.getMaker()).isEqualTo(catToy2.getMaker());
                assertThat(updatedCatToy.getPrice()).isEqualTo(catToy2.getPrice());
                assertThat(updatedCatToy.getImageURI()).isEqualTo(catToy2.getImageURI());
            }
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 없다면")
        class Context_with_invalid_id {

            @Test
            @DisplayName("CatToyNotFoundException을 던집니다.")
            void it_throw_CatToyNotFoundException() {
                assertThatThrownBy(() -> catToyRepository.update(1000L, catToy2))
                        .isInstanceOf(CatToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드")
    class Describe_deleteById {

        @BeforeEach
        void prepare() {
            catToyRepository.save(catToy1);
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 있다면")
        class Context_with_valid_id {

            @Test
            @DisplayName("해당 Id의 catToy를 제거합니다.")
            void it_update_catToy() {
                catToyRepository.deleteById(1L);

                assertThat(catToyRepository.findAll()).hasSize(0);
            }
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 없다면")
        class Context_with_invalid_id {

            @Test
            @DisplayName("CatToyNotFoundException을 던집니다.")
            void it_throw_CatToyNotFoundException() {
                assertThatThrownBy(() -> catToyRepository.update(1000L, catToy2))
                        .isInstanceOf(CatToyNotFoundException.class);
            }
        }
    }
}