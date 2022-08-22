package com.codesoom.assignment.service;

import com.codesoom.assignment.ToyTestHelper;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@DisplayName("CatToyService 클래스의")
public class CatToyServiceTest extends ToyTestHelper {
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
            @Test
            @DisplayName("장난감을 저장하고 리턴한다")
            void It_returns_catToyAndSave() {
                CatToy catToy = toyService.create(ToyTestHelper.TOY_INFO_TO_CREATE);

                assertThat(catToy).isEqualTo(ToyTestHelper.createdToy(catToy.getId()));
            }
        }
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 있다면")
        class Context_with_toyWithId {
            @Test
            @DisplayName("장난감을 리턴한다")
            void It_returns_toy() {
                CatToy catToy = toyService.create(ToyTestHelper.TOY_INFO_TO_CREATE);

                assertThat(toyService.findById(catToy.getId())).isEqualTo(createdToy(catToy.getId()));
            }
        }

        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 없다면")
        class Context_without_toy {
            @Test
            @DisplayName("예외를 던진다")
            void It_throws_exception() {
                assertThatThrownBy(() -> toyService.findById(ToyTestHelper.IMPOSSIBLE_ID))
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
            @BeforeEach
            void prepare() {
                toyService.create(ToyTestHelper.TOY_INFO_TO_CREATE);
                toyService.create(ToyTestHelper.TOY_INFO_TO_CREATE);
            }

            @Test
            @DisplayName("장난감 목록을 리턴한다")
            void It_returns_toyList() {
                assertThat(toyService.findAll()).hasSize(2);
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
                CatToy catToy = toyService.create(ToyTestHelper.TOY_INFO_TO_CREATE);
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
                CatToy catToy = toyService.create(ToyTestHelper.TOY_INFO_TO_CREATE);

                CatToy changedToy = toyService.update(catToy.getId(), ToyTestHelper.TOY_INFO_TO_CHANGE);

                CatToy expectToy = ToyTestHelper.changedToy(catToy.getId());

                assertAll(
                        () -> assertThat(changedToy.getId()).isEqualTo(expectToy.getId()),
                        () -> assertThat(changedToy.getName()).isEqualTo(expectToy.getName()),
                        () -> assertThat(changedToy.getMaker()).isEqualTo(expectToy.getMaker()),
                        () -> assertThat(changedToy.getPrice()).isEqualTo(expectToy.getPrice()),
                        () -> assertThat(changedToy.getImageUrl()).isEqualTo(expectToy.getImageUrl())
                );
            }
        }
    }
}
