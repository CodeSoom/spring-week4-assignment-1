package com.codesoom.assignment.application;

import com.codesoom.assignment.ToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.infra.InMemoryCatToyRepository;
import com.codesoom.assignment.service.CatToyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CatToyController 테스트")
public class CatToyControllerTest {
    private final String FIXTURE_NAME = "name";
    private final String FIXTURE_MAKER = "maker";
    private final int FIXTURE_PRICE = 10000;
    private final String FIXTURE_IMAGE_URL = "http://localhost:8080/snake";

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
                List<CatToy> result = controller.getList();

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
            @DisplayName("장난감을 찾을 수 없다는 예외를 던진다")
            void it_returnsFoundToy() {
                assertThrows(ToyNotFoundException.class, () -> {
                    controller.findById(1L);
                });
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
                CatToy result = controller.findById(toyId);

                assertThat(result).isNotNull();
                assertThat(result.getId()).isEqualTo(toyId);
                assertThat(result.getName()).isEqualTo(FIXTURE_NAME);
                assertThat(result.getMaker()).isEqualTo(FIXTURE_MAKER);
                assertThat(result.getPrice()).isEqualTo(FIXTURE_PRICE);
                assertThat(result.getImageURL()).isEqualTo(FIXTURE_IMAGE_URL);
            }
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {
        @Nested
        @DisplayName("저장되지 않은 장난감의 Id로 요청했을 떄")
        class Context_withIdOfNotSavedToy {
            @BeforeEach
            void prepare() {
                repository.deleteAll();
            }

            @Test
            @DisplayName("장난감을 찾을 수 없다는 예외를 던진다")
            void it_returnsFoundToy() {
                assertThrows(ToyNotFoundException.class, () -> {
                    controller.update(1L, new CatToy());
                });
            }
        }

        @Nested
        @DisplayName("저장되어 있는 장난감의 Id와 새로운 장난감을 파라미터로 요청했을 때")
        class Context_withIdOfSavedToy {
            private final CatToy savedCatToy = new CatToy(FIXTURE_NAME, FIXTURE_MAKER, FIXTURE_PRICE, FIXTURE_IMAGE_URL);

            @BeforeEach
            void prepare() {
                repository.save(savedCatToy);
            }

            @Test
            @DisplayName("업데이트된 장난감을 반환한다")
            void it_returnsFoundToy() {
                final Long toyId = 1L;

                CatToy newToy = new CatToy(FIXTURE_NAME + 1, FIXTURE_MAKER + 1, FIXTURE_PRICE + 1, FIXTURE_IMAGE_URL + 1);
                CatToy result = controller.update(toyId, newToy);

                assertThat(result.getId()).isEqualTo(toyId);
                assertThat(result.getName()).isEqualTo(FIXTURE_NAME + 1);
                assertThat(result.getMaker()).isEqualTo(FIXTURE_MAKER + 1);
                assertThat(result.getPrice()).isEqualTo(FIXTURE_PRICE + 1);
                assertThat(result.getImageURL()).isEqualTo(FIXTURE_IMAGE_URL + 1);
            }
        }
    }
}
