package com.codesoom.assignment.service;

import com.codesoom.assignment.ToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.infra.InMemoryCatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CatToyEditService 테스트")
class CatToyEditServiceTest {
    private final String FIXTURE_NAME = "name";
    private final String FIXTURE_MAKER = "maker";
    private final int FIXTURE_PRICE = 10000;
    private final String FIXTURE_IMAGE_URL = "http://localhost:8080/snake";

    private CatToyRepository repository;
    private CatToyEditService service;

    @BeforeEach
    void prepare() {
        repository = new InMemoryCatToyRepository();
        service = new CatToyEditService(repository);
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("필수 값들을 가지고 있는 장난감으로 요청했을 때")
        class Context_withToyHasRequiredFields {
            final CatToy toyWithRequiredFields = new CatToy(FIXTURE_NAME, FIXTURE_MAKER, FIXTURE_PRICE, FIXTURE_IMAGE_URL);

            @Test
            @DisplayName("저장된 장난감을 반환한다")
            void it_returnsSavedToy() {
                CatToy result = service.save(toyWithRequiredFields);

                assertThat(result).isEqualTo(toyWithRequiredFields);
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
                    service.update(1L, new CatToy());
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
                CatToy result = service.update(toyId, newToy);
                CatToy expected = new CatToy(toyId, FIXTURE_NAME + 1, FIXTURE_MAKER + 1, FIXTURE_PRICE + 1, FIXTURE_IMAGE_URL + 1);

                assertThat(result).isEqualTo(expected);
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메소드는")
    class Describe_deleteAll {
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
                    service.deleteById(1L);
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
            @DisplayName("장난감이 삭제된다")
            void it_returnsFoundToy() {
                final Long toyId = 1L;
                service.deleteById(toyId);
                assertThat(repository.findById(toyId)).isEmpty();
            }
        }
    }
}
