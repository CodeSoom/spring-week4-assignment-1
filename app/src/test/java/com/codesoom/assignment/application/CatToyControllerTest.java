package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.infra.InMemoryCatToyRepository;
import com.codesoom.assignment.service.CatToyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CatToyController 테스트")
public class CatToyControllerTest {
    private CatToyRepository repository;
    private CatToyController controller;

    @BeforeEach
    void setup() {
        repository = new InMemoryCatToyRepository();
        CatToyService service = new CatToyService(repository);
        controller = new CatToyController(service);
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
                assertThat(controller.getList()).isEmpty();
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
                List<CatToy> result = controller.getList();
                CatToy toy1 = result.get(0);
                CatToy toy2 = result.get(1);

                assertThat(result).hasSize(NUMBER_OF_TOY_LIST);
                assertThat(toy1.getId()).isEqualTo(1L);
                assertThat(toy2.getId()).isEqualTo(2L);
            }
        }
    }
}
