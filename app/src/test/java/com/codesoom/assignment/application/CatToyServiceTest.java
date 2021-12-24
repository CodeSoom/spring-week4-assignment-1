package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("CatToyService")
public class CatToyServiceTest {

    private static final String CAT_TOY_NAME = "test_cat_toy";
    private static final String CAT_TOY_MAKER = "test_maker";
    private static final String CAT_TOY_IMAGE = "http://test.jpg";
    private static final Integer CAT_TOY_PRICE = 10000;
    private static final Long NOT_EXISTED_ID = 0L;

    @Autowired
    CatToyRepository catToyRepository;

    CatToyService catToyService;

    CatToy existedCatToy;

    @BeforeEach
    void prepareService() {
        catToyService = new CatToyService(catToyRepository);
    }

    @BeforeEach
    void prepareRepository() {
        catToyRepository.deleteAll();
    }

    void prepareExistedCatToy() {
        existedCatToy = new CatToy();
        existedCatToy.setName(CAT_TOY_NAME);
        existedCatToy.setMaker(CAT_TOY_MAKER);
        existedCatToy.setPrice(CAT_TOY_PRICE);
        existedCatToy.setImageUrl(CAT_TOY_IMAGE);
        catToyRepository.save(existedCatToy);
    }

    @DisplayName("findAllCatToys")
    @Nested
    class Describe_findAllCatToys {
        List<CatToy> subject() {
            return catToyService.findAllCatToys();
        }

        @BeforeEach
        void prepare() {
            prepareExistedCatToy();
        }

        @DisplayName("등록된 CatToy가 있다면")
        @Nested
        class Context_with_cat_toy {
            @DisplayName("등록된 모든 CatToy의 리스트를 리턴한다.")
            @Test
            void it_returns_cat_toy_list() {
                assertThat(subject()).hasSize(1);
            }
        }

        @DisplayName("등록된 CatToy가 없다면")
        @Nested
        class Context_without_cat_toy {
            @BeforeEach
            void prepare() {
                catToyRepository.deleteAll();
            }

            @DisplayName("비어있는 리스트를 리턴한다.")
            @Test
            void it_returns_empty_list() {
                assertThat(subject()).isEmpty();
            }
        }
    }


    @DisplayName("findCatToy")
    @Nested
    class Describe_findCatToy {
        @BeforeEach
        void prepare() {
            prepareExistedCatToy();
        }

        CatToy subject(Long id) {
            return catToyService.findCatToy(id);
        }

        @DisplayName("등록된 CatToy id가 주어진다면")
        @Nested
        class Context_with_existed_id {
            @DisplayName("해당 id의 CatToy를 리턴한다.")
            @Test
            void it_returns_cat_toy() {
                CatToy result = subject(existedCatToy.getId());
                assertThat(result.getName()).isEqualTo(existedCatToy.getName());
                assertThat(result.getMaker()).isEqualTo(existedCatToy.getMaker());
                assertThat(result.getPrice()).isEqualTo(existedCatToy.getPrice());
                assertThat(result.getImageUrl()).isEqualTo(existedCatToy.getImageUrl());
            }
        }


    }

    @DisplayName("deleteCatToy")
    @Nested
    class Describe_deleteCatToy {
        void subject(Long id) {
            catToyService.deleteCatToy(id);
        }

        @BeforeEach
        void prepare() {
            prepareExistedCatToy();
        }

        @DisplayName("등록된 CatToy id가 주어진다면")
        @Nested
        class Context_with_cat_toy_id {
            @DisplayName("해당 id의 CatToy를 삭제하고, 아무것도 리턴하지 않는다.")
            @Test
            void it_returns_nothing() {
                subject(existedCatToy.getId());
                assertThat(catToyRepository.findById(existedCatToy.getId())).isEmpty();
            }
        }

        @DisplayName("등록되지않은 CatToy id가 주어진다면")
        @Nested
        class Context_with_not_existed_id {
            @DisplayName("'CatToy를 찾을 수 없다'는 예외를 던진다.")
            @Test
            void it_returns_cat_toy() {
                assertThatThrownBy(() -> subject(NOT_EXISTED_ID)).isInstanceOf(CatToyNotFoundException.class);
            }
        }
    }

    @DisplayName("createCatToy")
    @Nested
    class Describe_createCatToy {
        @DisplayName("CatToy가 주어진다면")
        @Nested
        class Context_with_cat_toy {
            CatToy newCatToy;

            CatToy subject() {
                return catToyService.createCatToy(newCatToy);
            }

            @BeforeEach
            void prepareNewCatToy() {
                newCatToy = new CatToy();
                newCatToy.setName(CAT_TOY_NAME);
                newCatToy.setMaker(CAT_TOY_MAKER);
                newCatToy.setPrice(CAT_TOY_PRICE);
                newCatToy.setImageUrl(CAT_TOY_IMAGE);
            }

            @DisplayName("동일한 CatToy를 생성하고, 리턴한다.")
            @Test
            void it_returns_created_cat_toy() {
                CatToy result = subject();
                assertThat(result.getPrice()).isEqualTo(CAT_TOY_PRICE);
                assertThat(result.getMaker()).isEqualTo(CAT_TOY_MAKER);
                assertThat(result.getImageUrl()).isEqualTo(CAT_TOY_IMAGE);
                assertThat(result.getName()).isEqualTo(CAT_TOY_NAME);
            }
        }
    }

    @DisplayName("updateCatToy")
    @Nested
    class Describe_updateCatToy {
        private final String UPDATE_CAT_TOY_NAME = "update_test_cat_toy";
        private final String UPDATE_CAT_TOY_MAKER = "update_test_maker";
        private final String UPDATE_CAT_TOY_IMAGE = "http://update_test.jpg";
        private final Integer UPDATE_CAT_TOY_PRICE = 20000;

        @DisplayName("등록된 CatToy id와 CatToy가 주어진다면")
        @Nested
        class Context_with_cat_toy_and_existed_id {
            CatToy updateCatToy;

            void prepareUpdateCatToy() {
                updateCatToy = new CatToy();
                updateCatToy.setName(UPDATE_CAT_TOY_NAME);
                updateCatToy.setMaker(UPDATE_CAT_TOY_MAKER);
                updateCatToy.setPrice(UPDATE_CAT_TOY_PRICE);
                updateCatToy.setImageUrl(UPDATE_CAT_TOY_IMAGE);
            }

            @BeforeEach
            void prepare() {
                prepareExistedCatToy();
                prepareUpdateCatToy();
            }

            CatToy subject() {
                return catToyService.updateCatToy(existedCatToy.getId(), updateCatToy);
            }

            @DisplayName("해당 id의 CatToy를 주어진 CatToy와 동일하게 변경하고 리턴한다.")
            @Test
            void it_returns_updated_cat_toy() {
                CatToy result = subject();
                assertThat(result.getName()).isEqualTo(UPDATE_CAT_TOY_NAME);
                assertThat(result.getMaker()).isEqualTo(UPDATE_CAT_TOY_MAKER);
                assertThat(result.getPrice()).isEqualTo(UPDATE_CAT_TOY_PRICE);
                assertThat(result.getImageUrl()).isEqualTo(UPDATE_CAT_TOY_IMAGE);
            }
        }
    }
}
