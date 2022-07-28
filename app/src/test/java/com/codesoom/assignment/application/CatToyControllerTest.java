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
    private final String FIXTURE_NAME = "name";
    private final String FIXTURE_MAKER = "maker";
    private final int FIXTURE_PRICE = 10000;

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
                    repository.save(new CatToy(FIXTURE_NAME + i, FIXTURE_MAKER + i, FIXTURE_PRICE + i));
                }
            }

            @Test
            @DisplayName("저장된 고양이 장난감 목록을 반환한다")
            void it_returnsSavedToyList() {
                List<CatToy> result = controller.getList();

                assertThat(result).hasSize(NUMBER_OF_TOY_LIST);

                for (int i = 0; i < NUMBER_OF_TOY_LIST; i++) {
                    assertThat(result.get(i).getName()).isEqualTo(FIXTURE_NAME + i);
                    assertThat(result.get(i).getMaker()).isEqualTo(FIXTURE_MAKER + i);
                    assertThat(result.get(i).getPrice()).isEqualTo(FIXTURE_PRICE + i);
                }
            }
        }
    }
}
