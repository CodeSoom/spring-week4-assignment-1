package com.codesoom.assignment.domain;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CatToyRegistoryTest 클래스")
class CatToyRegistoryTest {

    private CatToyRegistory catToyRegistory;
    private CatToy catToy1;
    private CatToy catToy2;
    private CatToy catToy3;

    @BeforeEach
    void setUp() {
        catToy1 = new CatToy(1L, "toy1", "maker1", 1000L, "toy1.jpg");
        catToy2 = new CatToy(1L, "toy1", "maker1", 1000L, "toy1.jpg");
        catToy3 = new CatToy(1L, "toy1", "maker1", 1000L, "toy1.jpg");

        catToyRegistory = new CatToyRegistory();
    }

    @Nested
    @DisplayName("create 메소드")
    class Describe_create {

        @Test
        @DisplayName("")
        void it_add_catToy() {
            assertThat(catToyRegistory.getAll()).hasSize(0);
            catToyRegistory.create(catToy1);
            assertThat(catToyRegistory.getAll()).hasSize(1);
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
            void it_return_all_catToy() {
                List<CatToy> catToys = catToyRegistory.getAll();

                assertThat(catToys).hasSize(0);
            }
        }

        @Nested
        @DisplayName("catToy 데이터가 있다면")
        class Context_has_catToy {

            @BeforeEach
            void prepare() {
                catToyRegistory.create(catToy1);
                catToyRegistory.create(catToy2);
                catToyRegistory.create(catToy3);
            }

            @Test
            @DisplayName("모든 catToy를 반환합니다.")
            void it_return_all_catToy() {
                List<CatToy> catToys = catToyRegistory.getAll();

                assertThat(catToys).hasSize(3);
                assertThat(catToys.get(0)).isEqualTo(catToy1);
                assertThat(catToys.get(1)).isEqualTo(catToy2);
                assertThat(catToys.get(2)).isEqualTo(catToy3);
            }
        }
    }
}