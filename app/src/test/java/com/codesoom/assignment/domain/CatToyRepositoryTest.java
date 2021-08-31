package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.CatToyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    @DisplayName("create 메소드")
    class Describe_create {

        @Test
        @DisplayName("catToy가 추가됩니다.")
        void it_add_catToy() {
            assertThat(catToyRepository.getAll()).hasSize(0);
            catToyRepository.create(catToy1);
            assertThat(catToyRepository.getAll()).hasSize(1);
        }
    }

    @Nested
    @DisplayName("getAll 메소드")
    class Describe_getAll {

        @Nested
        @DisplayName("catToy 데이터가 없다면")
        class Context_has_no_catToy {

            @Test
            @DisplayName("빈 데이터를 반환합니다.")
            void it_return_empty_catToys() {
                List<CatToy> catToys = catToyRepository.getAll();

                assertThat(catToys).hasSize(0);
            }
        }

        @Nested
        @DisplayName("catToy 데이터가 있다면")
        class Context_has_catToy {

            @BeforeEach
            void prepare() {
                catToyRepository.create(catToy1);
                catToyRepository.create(catToy2);
                catToyRepository.create(catToy3);
            }

            @Test
            @DisplayName("모든 catToy를 반환합니다.")
            void it_return_all_catToy() {
                List<CatToy> catToys = catToyRepository.getAll();

                assertThat(catToys).hasSize(3);
                assertThat(catToys.get(0)).isEqualTo(catToy1);
                assertThat(catToys.get(1)).isEqualTo(catToy2);
                assertThat(catToys.get(2)).isEqualTo(catToy3);
            }
        }
    }

    @Nested
    @DisplayName("getOne 메소드")
    class Describe_getOne {

        @BeforeEach
        void prepare() {
            catToyRepository.create(catToy1);
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 있다면")
        class Context_with_valid_id {

            @Test
            @DisplayName("해당 Id의 catToy를 반환합니다.")
            void it_return_catToy() {
                CatToy findedCatToy = catToyRepository.get(1L);

                assertThat(findedCatToy).isEqualTo(catToy1);
            }
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 없다면")
        class Context_with_invalid_id {

            @Test
            @DisplayName("CatToyNotFoundException을 던집니다.")
            void it_throw_CatToyNotFoundException() {
                assertThatThrownBy(() -> catToyRepository.get(1000L))
                        .isInstanceOf(CatToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드")
    class Describe_update {

        @BeforeEach
        void prepare() {
            catToyRepository.create(catToy1);
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 있다면")
        class Context_with_valid_id {

            @Test
            @DisplayName("해당 Id의 catToy를 수정합니다.")
            void it_update_catToy() {
                catToyRepository.update(1L, catToy2);

                CatToy updatedCatToy = catToyRepository.get(1L);

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
    @DisplayName("delete 메소드")
    class Describe_delete {

        @BeforeEach
        void prepare() {
            catToyRepository.create(catToy1);
        }

        @Nested
        @DisplayName("해당되는 id의 catToy가 있다면")
        class Context_with_valid_id {

            @Test
            @DisplayName("해당 Id의 catToy를 제거합니다.")
            void it_update_catToy() {
                catToyRepository.delete(1L);

                assertThat(catToyRepository.getAll()).hasSize(0);
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