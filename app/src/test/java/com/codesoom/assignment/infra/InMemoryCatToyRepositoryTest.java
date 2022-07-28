package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("InMemoryCatToyRepositoryTest 테스트")
class InMemoryCatToyRepositoryTest {
    private final String FIXTURE_NAME = "name";
    private final String FIXTURE_MAKER = "maker";
    private final int FIXTURE_PRICE = 10000;

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
                            return new CatToy(FIXTURE_NAME + value, FIXTURE_MAKER + value, FIXTURE_PRICE + value);
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
                        });
            }
        }
    }
}
