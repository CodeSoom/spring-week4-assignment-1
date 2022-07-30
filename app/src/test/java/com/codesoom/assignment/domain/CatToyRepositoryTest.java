package com.codesoom.assignment.domain;

import com.codesoom.assignment.ToyTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("CatToyRepository 클래스의")
public class CatToyRepositoryTest {
    @Autowired
    private CatToyRepository catToyRepository;

    @BeforeEach
    void setUp() {
        catToyRepository.deleteAll();
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_save {
        @Nested
        @DisplayName("장난감이 주어지면")
        class Context_with_catToy {
            @Test
            @DisplayName("장난감을 저장하고 리턴한다")
            void It_returns_catToyAndSave() {
                CatToy toy = catToyRepository.save(ToyTestHelper.TOY_INFO_TO_CREATE.toCatToy());

                assertThat(toy)
                        .isEqualTo(ToyTestHelper.createdToy(toy.getId()));
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
        @Nested
        @DisplayName("식별자와 같은 장난감이 주어지면")
        class Context_with_ToyWithId {
            @Test
            @DisplayName("장난감을 찾고 리턴한다")
            void It_returns_catToy_and_save() {
                CatToy toy = catToyRepository.save(ToyTestHelper.TOY_INFO_TO_CREATE.toCatToy());

                assertThat(catToyRepository.findById(toy.getId()).get())
                        .isEqualTo(ToyTestHelper.createdToy(toy.getId()));
            }
        }
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_findAll {
        @Nested
        @DisplayName("장난감 목록이 주어지면")
        class Context_with_toyList {
            void prepare() {
                catToyRepository.save(ToyTestHelper.TOY_INFO_TO_CREATE.toCatToy());
                catToyRepository.save(ToyTestHelper.TOY_INFO_TO_CREATE.toCatToy());
            }

            @Test
            @DisplayName("장난감 목록을 리턴한다")
            void It_returns_toyList() {
                prepare();

                assertThat(catToyRepository.findAll()).hasSize(2);
            }
        }

        @Nested
        @DisplayName("장난감 목록이 없다면")
        class Context_without_toyList {
            @Test
            @DisplayName("장난감 목록을 리턴한다")
            void It_returns_toyList() {
                assertThat(catToyRepository.findAll()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메서드는")
    class Describe_deleteById {
        @Nested
        @DisplayName("식별자를 가진 장난감이 있다면")
        class Context_with_toyWithId {
            @Test
            @DisplayName("장난감을 삭제한다")
            void It_returns_trueDeleteToy() {
                CatToy toy = catToyRepository.save(ToyTestHelper.TOY_INFO_TO_CREATE.toCatToy());
                catToyRepository.deleteById(toy.getId());

                assertThat(catToyRepository.findById(toy.getId()).isPresent()).isFalse();
            }
        }
    }
}
