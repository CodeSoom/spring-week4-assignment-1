package com.codesoom.assignment.application;

import com.codesoom.assignment.ProvideInvalidCatToyArguments;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.exception.CatToyInvalidFieldException;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import static com.codesoom.assignment.Constant.IMAGE_URL;
import static com.codesoom.assignment.Constant.MAKER;
import static com.codesoom.assignment.Constant.NAME;
import static com.codesoom.assignment.Constant.PRICE;
import static com.codesoom.assignment.Constant.OTHER_IMAGE_URL;
import static com.codesoom.assignment.Constant.OTHER_MAKER;
import static com.codesoom.assignment.Constant.OTHER_NAME;
import static com.codesoom.assignment.Constant.OTHER_PRICE;

@DisplayName("CatToyService 서비스 테스트")
public class CatToyServiceTestNested {

    private CatToyService service;
    private CatToyRepository repository;

    private CatToy catToy;

    @BeforeEach
    void setUp() {

        repository = mock(CatToyRepository.class);

        service = new CatToyService(repository);

        setUpFixtures();
    }

    private void setUpFixtures() {
        catToy = new CatToy(1L, NAME, MAKER, PRICE, IMAGE_URL);

        given(repository.findAll())
                .willReturn(new ArrayList<>())
                .willReturn(Collections.singletonList(catToy));

        given(repository.findById(1L)).willReturn(Optional.of(catToy));

        given(repository.findById(100L)).willThrow(new CatToyNotFoundException(100L));

        given(repository.save(any(CatToy.class))).willReturn(catToy);

    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("저장된 장난감들이 없을 경우")
        class Context_without_toy {
            @DisplayName("빈 목록이 반환된다.")
            @Test
            void findAllNotExistsToy() {
                final List<CatToy> catToys = service.findAll();

                assertThat(catToys).isEmpty();
            }
        }

        @Nested
        @DisplayName("저장된 장난감이 있을 경우")
        class Context_with_toy {
            @DisplayName("목록이 반환된다.")
            @Test
            void findAllExistsToy() {
                assertThat(service.findAll()).isEmpty();

                service.save(catToy);

                assertThat(service.findAll()).isNotEmpty();
            }
        }

    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {
        @Nested
        @DisplayName("조회하고자 하는 식별자가 존재할 경우 ")
        class Context_with_primary_key {
            @DisplayName("장난감 상세정보를 반환한다.")
            @Test
            void findByExistsId() {
                final CatToy foundCatToy = service.findById(catToy.getId());

                assertThat(foundCatToy.getId()).isEqualTo(catToy.getId());
                assertThat(foundCatToy.getName()).isEqualTo(catToy.getName());
                assertThat(foundCatToy.getMaker()).isEqualTo(catToy.getMaker());
                assertThat(foundCatToy.getPrice()).isEqualTo(catToy.getPrice());
                assertThat(foundCatToy.getImageUrl()).isEqualTo(catToy.getImageUrl());

                assertThat(foundCatToy).isEqualTo(catToy);
            }
        }

        @Nested
        @DisplayName("조회하고자 하는 식별자가 존재하지 않을 경우")
        class Context_without_primary_key {

            @DisplayName("예외가 발생한다.")
            @Test
            void findByNotExistsId() {
                assertThatThrownBy(() -> service.findById(100L))
                        .isInstanceOf(CatToyNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {

        @Nested
        @DisplayName("저장하고자 하는 장난감 정보가")
        class Context_with_data {

            @Nested
            @DisplayName("모두 유효한 경우")
            class Context_with_valid_data {
                @DisplayName("저장된 후 저장된 결과를 반환한다.")
                @Test
                void createCatToy() {
                    final CatToy catToy = CatToy.of(NAME, MAKER, PRICE, IMAGE_URL);
                    final CatToy savedCatToy = service.save(catToy);

                    assertThat(savedCatToy.getId()).isNotNull();
                    assertThat(savedCatToy.getName()).isEqualTo(catToy.getName());
                    assertThat(savedCatToy.getMaker()).isEqualTo(catToy.getMaker());
                    assertThat(savedCatToy.getPrice()).isEqualTo(catToy.getPrice());
                    assertThat(savedCatToy.getImageUrl()).isEqualTo(catToy.getImageUrl());
                }
            }

            @Nested
            @DisplayName("유효하지 않은 정보가 있을 경우")
            class Context_with_invalid_data {
                @DisplayName("예외가 발생한다.")
                @ParameterizedTest
                @ArgumentsSource(ProvideInvalidCatToyArguments.class)
                void createCatToyWithInvalidData(CatToy source) {
                    assertThatThrownBy(() -> service.save(source))
                            .isInstanceOf(CatToyInvalidFieldException.class);
                }
            }
        }
    }


    @Nested
    @DisplayName("updateCatToy 메서드는")
    class Describe_updateCatToy {

        @Nested
        @DisplayName("변경할 식별자가 존재 할 경우")
        class Context_with_existsId {

            @Nested
            @DisplayName("변경할 장난감 정보가 유효하면")
            class Context_with_valid_data {
                @DisplayName("정상적으로 수정되고 수정된 정보를 반환한다.")
                @Test
                void updateCatToy() {
                    final CatToy updatedCatToy = service.updateCatToy(catToy.getId(),
                            CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL));

                    assertThat(updatedCatToy.getId()).isEqualTo(catToy.getId());
                    assertThat(updatedCatToy.getName()).isEqualTo(OTHER_NAME);
                    assertThat(updatedCatToy.getMaker()).isEqualTo(OTHER_MAKER);
                    assertThat(updatedCatToy.getPrice()).isEqualTo(OTHER_PRICE);
                    assertThat(updatedCatToy.getImageUrl()).isEqualTo(OTHER_IMAGE_URL);
                }
            }

            @Nested
            @DisplayName("변경할 장난감 정보가 유효하지 않으면")
            class Context_with_invalid_data {

                @DisplayName("예외가 발생합니다.")
                @ParameterizedTest
                @ArgumentsSource(ProvideInvalidCatToyArguments.class)
                void updateCatToyWithInvalidData(CatToy target) {
                    assertThatThrownBy(() -> service.updateCatToy(catToy.getId(), target))
                            .isInstanceOf(CatToyInvalidFieldException.class);
                }

            }
        }

        @Nested
        @DisplayName("변경할 식별자가 존재하지 않을 경우")
        class Context_with_not_existsId {
            @DisplayName("예외가 발생합니다.")
            @Test
            void updateCatToyNotExistsId() {
                assertThatThrownBy(() -> service.updateCatToy(100L, CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL)))
                        .isInstanceOf(CatToyNotFoundException.class);
            }
        }

    }

    @Nested
    @DisplayName("deleteToy 메서드는")
    class Describe_deleteToy {
        @Nested
        @DisplayName("삭제하려는 장난감의 식별자가 존재할 경우")
        class Context_with_existsId {
            @DisplayName("정상적으로 장난감 정보가 삭제된다.")
            @Test
            void deleteCatToy() {
                service.deleteToy(catToy);

                verify(repository).delete(catToy);
            }
        }
    }


}
