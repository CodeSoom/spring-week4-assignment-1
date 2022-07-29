package com.codesoom.assignment.domain;

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

    public final Long givenId = 1L;
    public final String givenToyName = "낚시물고기";
    public final String givenMaker = "나이키";
    public final Integer givenPrice = 5000;
    public final String givenUrl = "url";

    public final CatToyDto givenToyDto = new CatToyDto(givenToyName, givenMaker, givenPrice, givenUrl);

    private CatToy givenExpectToy(Long givenId) {
        return new CatToy(givenId, givenToyName, givenMaker, givenPrice, givenUrl);
    }

    @BeforeEach
    void setUp() {
        assertThat(catToyRepository).isNotNull();
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
            void It_returns_catToy_and_save() {
                CatToy toy = catToyRepository.save(givenToyDto.toCatToy());

                assertThat(toy)
                        .isEqualTo(givenExpectToy(toy.getId()));
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
                CatToy toy = catToyRepository.save(givenToyDto.toCatToy());

                assertThat(catToyRepository.findById(toy.getId()).get()).isEqualTo(givenExpectToy(toy.getId()));
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
                catToyRepository.save(givenToyDto.toCatToy());
                catToyRepository.save(givenToyDto.toCatToy());
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
        class Context_with_toy {
            @Test
            @DisplayName("장난감을 삭제한다")
            void It_returns_trueDeleteToy() {
                CatToy toy = catToyRepository.save(givenToyDto.toCatToy());
                catToyRepository.deleteById(toy.getId());

                assertThat(catToyRepository.findById(toy.getId()).orElse(null)).isNull();
            }
        }
    }
}
