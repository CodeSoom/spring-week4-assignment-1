package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("CatToyService 클래스의")
public class CatToyServiceTest {
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
            CatToy prepare() {
                return new CatToy("뱀", "아디다스", 3000, "url");
            }

            @Test
            @DisplayName("장난감을 저장하고 리턴한다")
            void It_returns_catToy_and_save() {
                given(catToyRepository.save(prepare())).willReturn(prepare());

                assertThat(toyService.create(prepare())).isEqualTo(prepare());

                verify(catToyRepository).save(prepare());
            }
        }
    }
}
