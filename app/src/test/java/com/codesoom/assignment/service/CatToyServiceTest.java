package com.codesoom.assignment.service;

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
public class CatToyServiceTest {
    public static final long GIVEN_ID = 1L;

    private ToyService toyService;

    @Autowired
    private CatToyRepository catToyRepository;

    @BeforeEach
    void setUp() {
        toyService = new CatToyService(catToyRepository);
    }

    @Nested
    @DisplayName("create 메소드는")
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
    @DisplayName("findById 메소드는")
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
}
