package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("CatToyService 클래스의")
public class CatToyServiceTest {
    public static final long GIVEN_ID = 1L;
    private ToyService toyService;

    @Mock
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
            CatToyDto prepareRequest() {
                return new CatToyDto("뱀", "아디다스", 3000, "url");
            }

            CatToy prepareResult() {
                return new CatToy(GIVEN_ID, "뱀", "아디다스", 3000, "url");
            }

            @Test
            @DisplayName("장난감을 저장하고 리턴한다")
            void It_returns_catToy_and_save() {
                given(catToyRepository.save(prepareRequest().toCatToy())).willReturn(prepareResult());

                assertThat(toyService.create(prepareRequest())).isEqualTo(prepareResult());

                verify(catToyRepository).save(prepareRequest().toCatToy());
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {
        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 있다면")
        class Context_with_toy {
            Optional<CatToy> prepareResult() {
                return Optional.of(new CatToy(GIVEN_ID, "뱀", "아디다스", 3000, "url"));
            }

            @Test
            @DisplayName("장난감을 리턴한다")
            void It_returns_toy() {
                given(catToyRepository.findById(GIVEN_ID)).willReturn(prepareResult());

                assertThat(toyService.findById(GIVEN_ID)).isEqualTo(prepareResult().get());

                verify(catToyRepository).findById(GIVEN_ID);
            }
        }
    }
}
