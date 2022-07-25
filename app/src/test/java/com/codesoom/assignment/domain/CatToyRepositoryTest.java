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
            CatToy prepare() {
                return new CatToy("뱀", "나이키", 5000, "url");
            }

            @Test
            @DisplayName("장난감을 저장하고 리턴한다")
            void It_returns_catToy_and_save() {
                assertThat(catToyRepository.save(prepare())).isEqualTo(prepare());
            }
        }
    }
}
