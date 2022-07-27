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
public class CatToyRepositoryTest extends ToyTestHelper {

    @Autowired
    private CatToyRepository catToyRepository;

    @BeforeEach
    void setUp() {
        assertThat(catToyRepository).isNotNull();
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
                assertThat(catToyRepository.save(givenToyDto.toCatToy()))
                        .isEqualTo(expectToy);
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
                catToyRepository.save(givenToyDto.toCatToy());

                assertThat(catToyRepository.findById(givenId).
                        orElseThrow()).isEqualTo(expectToy);
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
    }
}
