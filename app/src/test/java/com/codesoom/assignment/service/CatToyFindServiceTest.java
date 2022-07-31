package com.codesoom.assignment.service;

import com.codesoom.assignment.ToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.infra.InMemoryCatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CatToyFindService 테스트")
class CatToyFindServiceTest {
    private final String FIXTURE_NAME = "name";
    private final String FIXTURE_MAKER = "maker";
    private final int FIXTURE_PRICE = 10000;
    private final String FIXTURE_IMAGE_URL = "http://localhost:8080/snake";

    private CatToyRepository repository;
    private CatToyFindService service;

    @BeforeEach
    void prepare() {
        repository = new InMemoryCatToyRepository();
        service = new CatToyFindService(repository);
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_getList {
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
                assertThat(service.findAll()).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 고양이 장난감이 있을 때")
        class Context_didSaveCatToy {
            final int NUMBER_OF_TOY_LIST = 3;

            final List<CatToy> savedToys = IntStream.rangeClosed(1, NUMBER_OF_TOY_LIST)
                    .mapToObj(value -> {
                        return new CatToy(FIXTURE_NAME + value, FIXTURE_MAKER + value, FIXTURE_PRICE + value, FIXTURE_IMAGE_URL + value);
                    })
                    .collect(Collectors.toList());

            @BeforeEach
            void prepare() {
                savedToys.forEach(catToy -> {
                    repository.save(catToy);
                });
            }

            @Test
            @DisplayName("저장된 고양이 장난감 목록을 반환한다")
            void it_returnsSavedToyList() {
                List<CatToy> result = service.findAll();

                assertThat(result).hasSize(NUMBER_OF_TOY_LIST);

                IntStream.rangeClosed(1, NUMBER_OF_TOY_LIST)
                        .forEach(index -> {
                            CatToy actual = result.get(index - 1);
                            CatToy expected = savedToys.get(index - 1);

                            assertThat(actual).isEqualTo(expected);
                        });
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
        @Nested
        @DisplayName("저장되지 않은 장난감의 Id로 요청했을 떄")
        class Context_withIdOfNotSavedToy {
            @BeforeEach
            void prepare() {
                repository.deleteAll();
            }

            @Test
            @DisplayName("null 값을 반환한다")
            void it_returnsFoundToy() {
                Optional<CatToy> result = service.findById(1L);

                assertThat(result).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장되어 있는 장난감의 Id로 요청했을 떄")
        class Context_withIdOfSavedToy {
            private final CatToy savedCatToy = new CatToy(FIXTURE_NAME, FIXTURE_MAKER, FIXTURE_PRICE, FIXTURE_IMAGE_URL);

            @BeforeEach
            void prepare() {
                repository.save(savedCatToy);
            }

            @Test
            @DisplayName("조회된 장난감을 반환한다")
            void it_returnsFoundToy() {
                final Long toyId = 1L;
                Optional<CatToy> result = service.findById(toyId);

                assertThat(result).isPresent();
                assertThat(result.get()).isEqualTo(savedCatToy);
            }
        }
    }
}
