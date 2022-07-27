package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.infra.InMemoryCatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CatToyService 테스트")
class CatToyServiceTest {

    private CatToyRepository repository;
    private CatToyService service;

    @BeforeEach
    void prepare() {
        repository = new InMemoryCatToyRepository();
        service = new CatToyService(repository);
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
                assertThat(service.getList()).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 고양이 장난감이 있을 때")
        class Context_didSaveCatToy {
            final int NUMBER_OF_TOY_LIST = 2;

            @BeforeEach
            void prepare() {
                for (int i = 0; i < NUMBER_OF_TOY_LIST; i++) {
                    repository.save(new CatToy());
                }
            }

            @Test
            @DisplayName("저장된 고양이 장난감 목록을 반환한다")
            void it_returnsSavedToyList() {
                List<CatToy> result = service.getList();
                CatToy toy1 = result.get(0);
                CatToy toy2 = result.get(1);

                assertThat(result).hasSize(NUMBER_OF_TOY_LIST);
                assertThat(toy1.getId()).isEqualTo(1L);
                assertThat(toy2.getId()).isEqualTo(2L);
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("필수 값들을 가지고 있는 장난감으로 요청했을 때")
        class Context_withToyHasRequiredFields {
            final CatToy toyWithRequiredFields = new CatToy("name", "maker", 999999);

            @DisplayName("저장된 장난감을 반환한다")
            void it_returnsSavedToy() {
                CatToy result = service.save(toyWithRequiredFields);

                assertThat(result.getId()).isEqualTo(1L);
                assertThat(result.getName()).isEqualTo("name");
                assertThat(result.getMaker()).isEqualTo("maker");
                assertThat(result.getPrice()).isEqualTo(999999);
            }
        }
    }
}
