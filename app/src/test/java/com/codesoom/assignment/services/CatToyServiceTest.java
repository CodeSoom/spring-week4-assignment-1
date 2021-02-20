package com.codesoom.assignment.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CatToyService 클래스")
class CatToyServiceTest {
    private final ToyService givenCatToyService = new CatToyService();

    @Nested
    @DisplayName("find 메소드는")
    class Describe_find {
        @Nested
        @DisplayName("주어진 id가 없을 때")
        class Context_without_given_id {
            @Test
            @DisplayName("모든 장난감들을 리턴한다.")
            void It_returns_all_toy() {

            }
        }

        @Nested
        @DisplayName("주어진 id가 있을 때")
        class Context_with_given_id {
            @Nested
            @DisplayName("주어진 id의 장난감이 없을 때.")
            class Context_when_not_exist_given_id {
                @Test
                @DisplayName("id를 찾을 수 없다는 예외를 던진다.")
                void It_throws_not_found_id_exception() {

                }
            }

            @Nested
            @DisplayName("주어진 id의 장난감이 있을 때.")
            class Context_when_exist_given_id {
                @Test
                @DisplayName("장난감을 리턴한다.")
                void It_returns_toy() {

                }
            }
        }
    }

    @Nested
    @DisplayName("insert 메소드는")
    class Describe_insert {
        @Test
        @DisplayName("주어진 장난감을 저장한다.")
        void It_save_given_toy() {

        }
    }

    @Nested
    @DisplayName("modify 메소드는")
    class Describe_modify {
        @Nested
        @DisplayName("주어진 id의 장난감이 없을 때.")
        class Context_when_not_exist_given_id {
            @Test
            @DisplayName("id를 찾을 수 없다는 예외를 던진다.")
            void It_throws_not_found_id_exception() {

            }
        }

        @Nested
        @DisplayName("주어진 id의 장난감이 있을 때.")
        class Context_when_exist_given_id {
            @Test
            @DisplayName("해당 id의 장난감 정보를 변경한다.")
            void It_modify_given_id_toy() {

            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {
        @Nested
        @DisplayName("주어진 id의 장난감이 없을 때.")
        class Context_when_not_exist_given_id {
            @Test
            @DisplayName("id를 찾을 수 없다는 예외를 던진다.")
            void It_throws_not_found_id_exception() {

            }
        }

        @Nested
        @DisplayName("주어진 id의 장난감이 있을 때.")
        class Context_when_exist_given_id {
            @Test
            @DisplayName("해당 id의 장난감을 제거한다.")
            void It_delete_given_id_toy() {

            }
        }
    }
}
