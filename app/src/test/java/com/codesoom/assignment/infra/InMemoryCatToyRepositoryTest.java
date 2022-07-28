package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("InMemoryCatToyRepository 클래스")
class InMemoryCatToyRepositoryTest {
    private final String FIXTURE_NAME = "name";
    private final String FIXTURE_MAKER = "maker";
    private final int FIXTURE_PRICE = 10000;
    private final String FIXTURE_IMAGE_URL = "http://localhost:8080/snake";

    private CatToyRepository repository;

    @BeforeEach
    void prepare() {
        repository = new InMemoryCatToyRepository();
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        @Nested
        @DisplayName("저장된 고양이 장난감이 없을 때")
        class Context_didNotSaveCatToy {
            @BeforeEach
            void prepare() {
                repository.deleteAll();
            }

            @Test
            @DisplayName("빈 목록을 반환한다")
            void it_returnsEmptyList() {
                assertThat(repository.findAll()).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 고양이 장난감이 있을 때")
        class Context_didSaveCatToy {
            final int NUMBER_OF_TOY_LIST = 3;

            @BeforeEach
            void prepare() {
                IntStream.rangeClosed(1, NUMBER_OF_TOY_LIST)
                        .mapToObj(value -> {
                            return new CatToy(FIXTURE_NAME + value, FIXTURE_MAKER + value, FIXTURE_PRICE + value, FIXTURE_IMAGE_URL + value);
                        })
                        .forEach(catToy -> {
                            repository.save(catToy);
                        });
            }

            @Test
            @DisplayName("저장된 고양이 장난감 목록을 반환한다")
            void it_returnsSavedToyList() {
                List<CatToy> result = repository.findAll();
                assertThat(result).hasSize(NUMBER_OF_TOY_LIST);

                IntStream.rangeClosed(1, NUMBER_OF_TOY_LIST)
                        .forEach(index -> {
                            CatToy catToy = result.get(index - 1);
                            assertThat(catToy.getName()).isEqualTo(FIXTURE_NAME + index);
                            assertThat(catToy.getMaker()).isEqualTo(FIXTURE_MAKER + index);
                            assertThat(catToy.getPrice()).isEqualTo(FIXTURE_PRICE + index);
                            assertThat(catToy.getImageURL()).isEqualTo(FIXTURE_IMAGE_URL + index);
                        });
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("고양이 장난감 정보를 전달했을 때")
        class Context_withCatToy {
            CatToy catToy = new CatToy(FIXTURE_NAME, FIXTURE_MAKER, FIXTURE_PRICE, FIXTURE_IMAGE_URL);

            @Test
            @DisplayName("저장에 성공한 장난감을 반환한다")
            void it_returnsSavedToy() {
                CatToy result = repository.save(catToy);

                assertThat(result.getId()).isEqualTo(1L);
                assertThat(result.getName()).isEqualTo(FIXTURE_NAME);
                assertThat(result.getMaker()).isEqualTo(FIXTURE_MAKER);
                assertThat(result.getPrice()).isEqualTo(FIXTURE_PRICE);
                assertThat(result.getPrice()).isEqualTo(FIXTURE_IMAGE_URL);
            }
        }

        @Nested
        @DisplayName("고양이 장난감을 담아서 여러번 호출했을 때")
        class Context_calledMultipleWithCatToy {
            private final int NUMBER_OF_TOY_LIST = 3;
            private final List<CatToy> requestToys = IntStream.rangeClosed(1, NUMBER_OF_TOY_LIST)
                    .mapToObj(value -> {
                        return new CatToy(FIXTURE_NAME + value, FIXTURE_MAKER + value, FIXTURE_PRICE + value, FIXTURE_IMAGE_URL + value);
                    })
                    .collect(Collectors.toList());

            @Test
            @DisplayName("저장에 성공한 장난감을 반환한다")
            void it_returnsSavedToy() {
                 List<CatToy> result = requestToys.stream()
                         .map(catToy -> {
                             return repository.save(catToy);
                         })
                         .collect(Collectors.toList());

                IntStream.rangeClosed(1, NUMBER_OF_TOY_LIST)
                                .forEach(index -> {
                                    CatToy catToy = result.get(index - 1);
                                    assertThat(catToy.getId()).isEqualTo(index);
                                    assertThat(catToy.getName()).isEqualTo(FIXTURE_NAME + index);
                                    assertThat(catToy.getMaker()).isEqualTo(FIXTURE_MAKER + index);
                                    assertThat(catToy.getPrice()).isEqualTo(FIXTURE_PRICE + index);
                                    assertThat(catToy.getPrice()).isEqualTo(FIXTURE_IMAGE_URL + index);
                                });
            }
        }

    }
}
