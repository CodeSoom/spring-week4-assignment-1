package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("InMemoryCatToyRepositoryTest 테스트")
class InMemoryCatToyRepositoryTest {

    private CatToyRepository repository;

    @BeforeEach
    void prepare() {
        repository = new InMemoryCatToyRepository();
    }

    @Nested
    @DisplayName("getList 메소드는")
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
                assertThat(repository.findAll()).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 고양이 장난감이 있을 때")
        class Context_didSaveCatToy {
            @BeforeEach
            void perpare() {
                repository.save(new CatToy());
                repository.save(new CatToy());
            }

            @Test
            @DisplayName("저장된 고양이 장난감 목록을 반환한다")
            void it_returnsSavedToyList() {
                List<CatToy> result = repository.findAll();
                CatToy toy1 = result.get(0);
                CatToy toy2 = result.get(1);

                assertThat(result).hasSize(2);
                assertThat(toy1.getId()).isEqualTo(1L);
                assertThat(toy2.getId()).isEqualTo(2L);
            }
        }
    }
}
