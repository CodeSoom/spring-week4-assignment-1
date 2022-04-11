package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("CatToyService 클래스")
class CatToyServiceTest {

    @InjectMocks
    CatToyService catToyService;

    @Mock
    CatToyRepository catToyRepository;

    @Nested
    @DisplayName("getCatToys 메소드는")
    class Describe_getCatToys {

        @Nested
        @DisplayName("주어진 고양이 장난감 수 만큼")
        class Context_hasCatToys {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {
                Iterable<CatToy> catToys = LongStream.rangeClosed(1, givenCount)
                        .mapToObj(CatToy::new)
                        .collect(Collectors.toList());

                given(catToyRepository.findAll()).willReturn(catToys);
            }

            @Test
            @DisplayName("고양이 장난감 목록을 리턴한다.")
            void it_return_catToys() {

                List<CatToy> catToys = catToyService.getCatToys();

                assertThat(catToys).hasSize(givenCount);
            }
        }
    }
}
