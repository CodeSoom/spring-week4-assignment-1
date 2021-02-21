package com.codesoom.assignment.services;

import com.codesoom.assignment.exceptions.ToyNotFoundException;
import com.codesoom.assignment.models.CatToy;
import com.codesoom.assignment.models.Toy;
import com.codesoom.assignment.repositories.ToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CatToyService 클래스")
class CatToyServiceTest {
    private final Toy givenToy1 = new CatToy(0L, "cat nip", "cat company. co", 1000D, "https://cat.toy/cat-nip.png");
    private final Toy givenToy2 = new CatToy(1L, "cat tower", "cat company. co", 10000D, "https://cat.toy/cat-tower.png");

    private ToyRepository toyRepository;
    private ToyService catToyService;

    @BeforeEach
    void setup() {
        toyRepository = new ToyRepository.Fake();
        catToyService = new CatToyService(toyRepository);
    }

    @Nested
    @DisplayName("find 메소드는")
    class Describe_find {
        @Nested
        @DisplayName("주어진 id가 없을 때")
        class Context_without_given_id {
            @BeforeEach
            void setup() {
                toyRepository.save(givenToy1);
                toyRepository.save(givenToy2);
            }

            @Test
            @DisplayName("모든 장난감들을 리턴한다.")
            void It_returns_all_toy() {
                List<Toy> toys = catToyService.find();

                assertThat(toys).hasSize(2);
            }
        }

        @Nested
        @DisplayName("주어진 id가 있을 때")
        class Context_with_given_id {
            @Nested
            @DisplayName("주어진 id의 장난감이 없을 때.")
            class Context_when_not_exist_given_id {
                private final Long givenID = 1L;

                @Test
                @DisplayName("장난감을 찾을 수 없다는 예외를 던진다.")
                void It_throws_not_found_id_exception() {
                    assertThatThrownBy(() -> catToyService.find(givenID))
                            .isInstanceOf(ToyNotFoundException.class);
                }
            }

            @Nested
            @DisplayName("주어진 id의 장난감이 있을 때.")
            class Context_when_exist_given_id {
                @BeforeEach
                void setup() {
                    toyRepository.save(givenToy1);
                }

                @Test
                @DisplayName("장난감을 리턴한다.")
                void It_returns_toy() {
                    Toy toy = catToyService.find(givenToy1.id());

                    assertThat(toy.id()).isEqualTo(givenToy1.id());
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
            catToyService.insert(givenToy1);
            catToyService.find(givenToy1.id());
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
                assertThatThrownBy(() -> catToyService.modify(givenToy1.id(), givenToy1))
                    .isInstanceOf(ToyNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("주어진 id의 장난감이 있을 때.")
        class Context_when_exist_given_id {
            @BeforeEach
            void setup() {
                toyRepository.save(givenToy1);
            }

            private final Toy modifiedCatToy = new CatToy(givenToy1.id(), "마따따비 막대", givenToy1.brand(), givenToy1.price(), givenToy1.imageURL());

            @Test
            @DisplayName("해당 id의 장난감 정보를 변경한다.")
            void It_modify_given_id_toy() {
                catToyService.modify(givenToy1.id(), modifiedCatToy);

                final Toy toy = catToyService.find(modifiedCatToy.id());

                assertThat(toy.name()).isEqualTo(modifiedCatToy.name());
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
                assertThatThrownBy(() -> catToyService.delete(givenToy1.id()))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("주어진 id의 장난감이 있을 때.")
        class Context_when_exist_given_id {
            @BeforeEach
            void setup() {
                toyRepository.save(givenToy1);
            }

            @Test
            @DisplayName("해당 id의 장난감을 제거한다.")
            void It_delete_given_id_toy() {
                catToyService.delete(givenToy1.id());

                assertThatThrownBy(() -> catToyService.find(givenToy1.id()))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }
    }
}
