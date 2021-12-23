package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CatToyService")
public class CatToyServiceTest {

    private static final String CAT_TOY_NAME = "test_cat_toy";
    private static final String CAT_TOY_MAKER = "test_maker";
    private static final String CAT_TOY_IMAGE = "http://test.jpg";
    private static final Integer CAT_TOY_PRICE = 10000;

    @Autowired
    CatToyRepository catToyRepository;

    CatToyService catToyService;

    void prepareService() {
        catToyService = new CatToyService(catToyRepository);
    }

    @DisplayName("findAllCatToys")
    @Nested
    class Describe_findAllCatToys {
        List<CatToy> subject() {
            return catToyService.findAllCatToys();
        }

        @BeforeEach
        void prepare() {
            prepareService();
            CatToy catToy1 = new CatToy();
            CatToy catToy2 = new CatToy();
            catToyRepository.save(catToy1);
            catToyRepository.save(catToy2);
        }

        @DisplayName("등록된 CatToy가 있다면")
        @Nested
        class Context_with_cat_toy {
            @DisplayName("등록된 모든 CatToy의 리스트를 리턴한다.")
            @Test
            void it_returns_cat_toy_list() {
                assertThat(subject()).hasSize(2);
            }

        }
    }

    @DisplayName("findCatToy")
    @Nested
    class Describe_findCatToy {
        @DisplayName("등록된 CatToy id가 주어진다면")
        @Nested
        class Context_with_existed_id {
            CatToy catToy;

            void prepareCatToy() {
                catToy = new CatToy();
                catToy.setName(CAT_TOY_NAME);
                catToy.setMaker(CAT_TOY_MAKER);
                catToy.setPrice(CAT_TOY_PRICE);
                catToy.setImage(CAT_TOY_IMAGE);
                catToyRepository.save(catToy);
            }

            @BeforeEach
            void prepare() {
                prepareService();
                prepareCatToy();
            }

            CatToy subject() {
                Long existedId = catToy.getId();
                return catToyService.findCatToy(existedId);
            }

            @DisplayName("해당 id의 CatToy를 리턴한다.")
            @Test
            void it_returns_cat_toy() {
                CatToy result = subject();
                assertThat(result).isEqualTo(catToy);
            }
        }

    }

    @DisplayName("deleteCatToy")
    @Nested
    class Describe_deleteCatToy {
        @DisplayName("등록된 CatToy id가 주어진다면")
        @Nested
        class Context_with_cat_toy_id {
            CatToy catToy;

            void subject() {
                catToyService.deleteCatToy(catToy.getId());
            }

            void prepareCatToy() {
                catToy = new CatToy();
                catToy.setName(CAT_TOY_NAME);
                catToy.setMaker(CAT_TOY_MAKER);
                catToy.setPrice(CAT_TOY_PRICE);
                catToy.setImage(CAT_TOY_IMAGE);
                catToyRepository.save(catToy);
            }

            @BeforeEach
            void prepare() {
                prepareService();
                prepareCatToy();
            }

            @DisplayName("해당 id의 CatToy를 삭제하고, 아무것도 리턴하지 않는다.")
            @Test
            void it_returns_nothing() {
                subject();
                assertThat(catToyRepository.findById(catToy.getId())).isEmpty();
            }
        }
    }

    @DisplayName("createCatToy")
    @Nested
    class Describe_createCatToy {
        @DisplayName("CatToy가 주어진다면")
        @Nested
        class Context_with_cat_toy {
            CatToy catToy;

            CatToy subject() {
                return catToyService.createCatToy(catToy);
            }

            void prepareNewCatToy() {
                catToy = new CatToy();
                catToy.setName(CAT_TOY_NAME);
                catToy.setMaker(CAT_TOY_MAKER);
                catToy.setPrice(CAT_TOY_PRICE);
                catToy.setImage(CAT_TOY_IMAGE);
            }

            @BeforeEach
            void prepare() {
                prepareService();
                prepareNewCatToy();
            }

            @DisplayName("동일한 CatToy를 생성하고, 리턴한다.")
            @Test
            void it_returns_created_cat_toy() {
                assertThat(subject().getId()).isEqualTo(1L);
                assertThat(subject().getPrice()).isEqualTo(CAT_TOY_PRICE);
                assertThat(subject().getMaker()).isEqualTo(CAT_TOY_MAKER);
                assertThat(subject().getImage()).isEqualTo(CAT_TOY_IMAGE);
                assertThat(subject().getName()).isEqualTo(CAT_TOY_NAME);
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
            CatToy catToy;
            CatToy updateCatToy;

            void prepareCatToy() {
                catToy = new CatToy();
                catToy.setName(CAT_TOY_NAME);
                catToy.setMaker(CAT_TOY_MAKER);
                catToy.setPrice(CAT_TOY_PRICE);
                catToy.setImage(CAT_TOY_IMAGE);
                catToyRepository.save(catToy);
            }

            void prepareUpdateCatToy() {
                updateCatToy = new CatToy();
                updateCatToy.setName(UPDATE_CAT_TOY_NAME);
                updateCatToy.setMaker(UPDATE_CAT_TOY_MAKER);
                updateCatToy.setPrice(UPDATE_CAT_TOY_PRICE);
                updateCatToy.setImage(UPDATE_CAT_TOY_IMAGE);
            }

            @BeforeEach
            void prepare() {
                prepareService();
                prepareCatToy();
                prepareUpdateCatToy();
            }

            CatToy subject() {
                return catToyService.updateCatToy(catToy.getId(), updateCatToy);
            }

            @DisplayName("해당 id의 CatToy를 주어진 CatToy와 동일하게 변경하고 리턴한다.")
            @Test
            void it_returns_updated_cat_toy() {
                CatToy result = subject();
                assertThat(result.getName()).isEqualTo(UPDATE_CAT_TOY_NAME);
                assertThat(result.getMaker()).isEqualTo(UPDATE_CAT_TOY_MAKER);
                assertThat(result.getPrice()).isEqualTo(UPDATE_CAT_TOY_PRICE);
                assertThat(result.getImage()).isEqualTo(UPDATE_CAT_TOY_IMAGE);
            }
        }
    }
}
