package com.codesoom.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void getToys_not_exists() {
        // GIVEN

        // WHEN
        List<Toy> toys = toyService.getToys();

        // THEN
        assertThat(toys).isEmpty();
    }

    @Test
    void getToys_exists() {
        // GIVEN
        Toy registered = toyService.register("Toy 1", "maker 1", 1000, "url 1");
        Toy registered2 = toyService.register("Toy 2", "maker 2", 2000, "url 2");

        // WHEN
        List<Toy> toys = toyService.getToys();

        // THEN
        assertThat(toys).hasSize(2);
        assertThat(toys.get(0).getName()).isEqualTo(registered.getName());
        assertThat(toys.get(0).getMaker()).isEqualTo(registered.getMaker());
        assertThat(toys.get(0).getPrice()).isEqualTo(registered.getPrice());
        assertThat(toys.get(0).getImageUrl()).isEqualTo(registered.getImageUrl());
        assertThat(toys.get(1).getName()).isEqualTo(registered2.getName());
        assertThat(toys.get(1).getMaker()).isEqualTo(registered2.getMaker());
        assertThat(toys.get(1).getPrice()).isEqualTo(registered2.getPrice());
        assertThat(toys.get(1).getImageUrl()).isEqualTo(registered2.getImageUrl());
    }
}
