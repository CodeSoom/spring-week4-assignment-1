package com.codesoom.assignment;

import com.codesoom.assignment.exceptions.ToyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ToyService 클래스")
public class ToyServiceTest {
    private ToyService toyService;

    @BeforeEach
    void setUp() {
        toyService = new ToyService();
    }

    private Toy randomToy() {
        int randomNumber = (int) (Math.random() * 1000);
        return new Toy(
                "Toy" + randomNumber,
                "Maker" + randomNumber,
                randomNumber,
                "URL" + randomNumber
        );
    }

    @Nested
    @DisplayName("getToys 메소드는")
    class Describe_getToys {

        @Nested
        @DisplayName("아무런 toy가 등록되지 않았다면")
        class Context_no_toys {

            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_empty_list() {
                List<Toy> toys = toyService.getToys();

                assertThat(toys).hasSize(0);
            }
        }

        @Nested
        @DisplayName("1개의 toy만 등록되어 있다면")
        class Context_one_toy {
            private Toy toy;

            @BeforeEach
            void setUp() {
                toy = randomToy();
                toyService.register(toy);
            }

            @Test
            @DisplayName("1개의 Toy만이 들어있는 리스트를 반환한다.")
            void it_returns_list_of_one_toy() {
                List<Toy> toys = toyService.getToys();

                assertThat(toys).hasSize(1);
                assertThat(toys.get(0)).isEqualTo(toy);
            }
        }

        @Nested
        @DisplayName("n개 이상의 toy가 등록되어 있다면")
        class Context_multiple_toys {
            private List<Toy> expectedToys = new ArrayList<>();

            void setUp(int testCase) {
                for (int i = 0; i < testCase; i++) {
                    Toy randomToy = randomToy();
                    expectedToys.add(randomToy);
                    toyService.register(randomToy);
                }
            }

            @DisplayName("n개의 Toy만이 들어있는 리스트를 반환한다.")
            @ParameterizedTest
            @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20})
            void it_returns_list_of_n_toys(int testCase) {
                setUp(testCase);

                List<Toy> toys = toyService.getToys();

                assertThat(toys).hasSize(testCase);
                for (int i = 0; i < testCase; i++) {
                    assertThat(toys.get(i)).isEqualTo(this.expectedToys.get(i));
                }
            }
        }
    }

    @Nested
    @DisplayName("getToyById 메소드는")
    class Describe_getToyById {

        @Nested
        @DisplayName("인자로 넘긴 ID의 Toy를 발견하면")
        class Context_found {
            private Toy toy;

            @BeforeEach
            void setUp() {
                toy = toyService.register(randomToy());
            }

            @Test
            @DisplayName("해당 Toy를 리턴한다")
            void it_returns_according_toy() throws ToyNotFoundException {
                Toy foundToy = toyService.getToyById(toy.getId());

                assertThat(foundToy).isEqualTo(toy);
            }
        }

        @Nested
        @DisplayName("인자로 넘긴 ID의 Toy를 발견하지 못하면")
        class Context_not_found {
            @Test
            @DisplayName("404 Not Found Exception을 던진다.")
            void it_throws_404_not_found_exception() {
                long ID_NOT_EXISTS = Long.MAX_VALUE;
                assertThatThrownBy(() -> toyService.getToyById(ID_NOT_EXISTS))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("register 메소드는")
    class Describe_register {

        @Nested
        @DisplayName("정상적인 상황에서")
        class Context_normal {
            private Toy toy;

            @BeforeEach
            void setUp() {
                toy = randomToy();
            }

            @Test
            @DisplayName("인자로 넘겨진 Toy를 등록하고, 등록된 Toy를 리턴한다")
            void it_returns_registered_toy() {
                Toy registeredToy = toyService.register(toy);

                assertThat(registeredToy).isEqualTo(toy);
            }
        }

        @Nested
        @DisplayName("여러 태스크를 추가해도")
        class Context_multiple_tasks {
            void registerMultipleToys(int testCase) {
                for (int i = 0; i < testCase; i++) {
                    Toy randomToy = randomToy();
                    toyService.register(randomToy);
                }
            }

            @DisplayName("모두 다른 ID를 부여한다")
            @ParameterizedTest
            @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20})
            void it_returns_all_different_ids(int testCase) {
                registerMultipleToys(testCase);
                List<Toy> toys = toyService.getToys();

                Set<Long> ids = toys.stream().map(Toy::getId).collect(Collectors.toSet());
                assertThat(ids).hasSize(testCase);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

        @Nested
        @DisplayName("인자로 넘겨진 ID에 해당하는 Toy를 찾으면")
        class Context_toy_found {
            private Toy targetToy;

            @BeforeEach
            void setUp() {
                targetToy = toyService.register(randomToy());
            }

            @Test
            @DisplayName("해당 Toy를 인자로 넘겨진 Toy의 정보로 업데이트하고, 업데이트된 Toy를 리턴한다")
            void it_returns_updated_toy() throws ToyNotFoundException {
                Toy newToy = randomToy();
                Toy updated = toyService.update(targetToy.getId(), newToy);

                assertThat(updated).isEqualTo(newToy);
            }
        }

        @Nested
        @DisplayName("인자로 넘겨진 ID에 해당하는 Toy를 찾지 못하면")
        class Context_toy_not_found {
            @Test
            @DisplayName("404 Not Found Exception을 던진다.")
            void it_throws_404_not_found_exception() {
                long ID_NOT_EXISTS = Long.MAX_VALUE;
                assertThatThrownBy(() -> toyService.update(ID_NOT_EXISTS, randomToy()))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

            @Nested
            @DisplayName("인자로 넘겨진 ID에 해당하는 Toy를 찾으면")
            class Context_toy_found {
                private Toy targetToy;

                @BeforeEach
                void setUp() {
                    targetToy = toyService.register(randomToy());
                }

                @Test
                @DisplayName("해당 Toy를 삭제한다")
                void it_returns_deleted_toy() throws ToyNotFoundException {
                    toyService.delete(targetToy.getId());

                    assertThatThrownBy(() -> toyService.getToyById(targetToy.getId()))
                            .isInstanceOf(ToyNotFoundException.class);
                }
            }

            @Nested
            @DisplayName("인자로 넘겨진 ID에 해당하는 Toy를 찾지 못하면")
            class Context_toy_not_found {
                @Test
                @DisplayName("404 Not Found Exception을 던진다.")
                void it_throws_404_not_found_exception() {
                    long ID_NOT_EXISTS = Long.MAX_VALUE;
                    assertThatThrownBy(() -> toyService.delete(ID_NOT_EXISTS))
                            .isInstanceOf(ToyNotFoundException.class);
                }
            }
    }
}