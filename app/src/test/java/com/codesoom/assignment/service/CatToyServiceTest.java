package com.codesoom.assignment.service;

import com.codesoom.assignment.ToyTestHelper;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("CatToyService 클래스의")
public class CatToyServiceTest extends ToyTestHelper {
    public static final long GIVEN_ID = 1L;
    public static final int ROOF_COUNT = 2;

    @Autowired
    private CatToyRepository catToyRepository;

    private ToyService toyService;

    @BeforeEach
    void setUp() {
        toyService = new CatToyService(catToyRepository);

        catToyRepository.deleteAll();
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {
        @Nested
        @DisplayName("장난감이 주어지면")
        class Context_with_catToy {
            CatToy prepare() {
                return toyService.create(new CatToyDto("뱀", "아디다스", 3000, "url"));
            }

            @Test
            @DisplayName("장난감을 저장하고 리턴한다")
            void It_returns_catToy_and_save() {
                CatToy given = prepare();

                assertThat(given).isEqualTo(new CatToy(given.getId(), "뱀", "아디다스", 3000, "url"));
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 있다면")
        class Context_with_toy {
            CatToy prepare() {
                return toyService.create(new CatToyDto("뱀", "아디다스", 3000, "url"));
            }

            @Test
            @DisplayName("장난감을 리턴한다")
            void It_returns_toy() {
                CatToy given = prepare();

                assertThat(toyService.findById(given.getId())).isEqualTo(new CatToy(given.getId(), "뱀", "아디다스", 3000, "url"));
            }
        }

        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 없다면")
        class Context_without_toy {
            @Test
            @DisplayName("예외를 던진다")
            void It_returns_toy() {
                assertThatThrownBy(() -> toyService.findById(GIVEN_ID))
                        .isInstanceOf(RuntimeException.class);
            }
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("장난감 목록이 주어진다면")
        class Context_with_toyList {
            void prepare() {
                catToyRepository.save(givenToyDto.toCatToy());
                catToyRepository.save(givenToyDto.toCatToy());
            }

            @Test
            @DisplayName("장난감 목록을 리턴한다")
            void It_returns_toyList() {
                prepare();

                assertThat(toyService.findAll()).hasSize(ROOF_COUNT);
            }
        }

        @Nested
        @DisplayName("장난감 목록이 없다면")
        class Context_without_toyList {
            @Test
            @DisplayName("장난감 목록을 리턴한다")
            void It_returns_toyList() {
                assertThat(toyService.findAll()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메서드는")
    class Describe_deleteById {
        @Nested
        @DisplayName("식별자를 가진 장난감이 있으면")
        class Context_with_toy {
            @Test
            @DisplayName("장난감을 제거한다")
            void It_returns_trueAndDeleteToy() {
                CatToy catToy = toyService.create(givenToyDto);
                toyService.deleteById(catToy.getId());

                assertThatThrownBy(() -> toyService.findById(catToy.getId()))
                        .isInstanceOf(RuntimeException.class);
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {
        @Nested
        @DisplayName("식별자를 가진 장난감과 변경할 정보가 있다면")
        class Context_with_toyWithIdAndChangeData {
            @Test
            @DisplayName("변경된 정보를 가진 장난감을 리턴한다")
            void It_returns_ChangedToy() {
                CatToy catToy = toyService.create(givenToyDto);

                assertThat(toyService.update(catToy.getId(), givenToyToChangeDto))
                        .isEqualTo(changedToy(catToy.getId()));
            }
        }
    }
}
