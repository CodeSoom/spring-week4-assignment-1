package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.repository.CatToyRepository;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import com.codesoom.assignment.service.CatToyCommandService;
import com.codesoom.assignment.service.CatToyQueryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


@DisplayName("CatToyController의")
@DataJpaTest
class CatToyControllerTest {

    public static final long INVALID_ID = 99999L;
    private CatToyController catToyController;

    @Autowired
    private CatToyRepository catToyRepository;

    @BeforeEach
    void setUp() {
        CatToyQueryService catToyQueryService = new CatToyQueryService(catToyRepository);
        CatToyCommandService catToyCommandService = new CatToyCommandService(catToyRepository, catToyQueryService);
        catToyController = new CatToyController(catToyQueryService, catToyCommandService);
    }

    @Nested
    @DisplayName("getCatToyList 메소드는")
    class Describe_getCatToyList {

        @Nested
        @DisplayName("장난감이 없으면")
        class Context_with_no_cat_toy {

            @BeforeEach
            void setUp() {
                catToyRepository.deleteAll();
            }

            @Test
            @DisplayName("빈 리스트를 리턴한다.")
            void getTaskWithoutCatToy() {
                List<CatToy> catToyList = catToyController.getCatToyList();
                Assertions.assertThat(catToyList).isEmpty();
            }
        }

        @Nested
        @DisplayName("장난감이 있으면")
        class Context_with_cat_toy {

            @BeforeEach
            void setUp() {
                catToyController.createCatToy(new CatToy("멍멍", "야옹메이커", 1000, "https://source.unsplash.com/random"));
                catToyController.createCatToy(new CatToy("야오옹", "냥이메이커", 2000, "https://source.unsplash.com/random"));
            }

            @Test
            @DisplayName("장난감 리스트를 리턴한다.")
            void getTaskWithoutCatToy() {
                List<CatToy> catToyList = catToyController.getCatToyList();
                Assertions.assertThat(catToyList).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayName("getCatToy 메소드는")
    class Describe_getCat {

        @Nested
        @DisplayName("id에 해당하는 장난감을 찾지 못하면")
        class Context_with_find_no_cat_toy {
            @Test
            @DisplayName("CatToyNotFound 예외를 던진다.")
            void getCatToyWithNoCatToy() {
                assertThatThrownBy(() -> {
                    catToyController.getCatToy(INVALID_ID);
                }).isInstanceOf(CatToyNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("id에 해당하는 장난감을 찾으면")
        class Context_with_find_cat_toy {

            CatToy catToy;
            CatToy newCatToy;

            @BeforeEach
            void setUp() {
                catToy = new CatToy("멍멍", "야옹메이커", 1000, "https://source.unsplash.com/random");
                newCatToy = catToyController.createCatToy(catToy);
            }

            @Test
            @DisplayName("할 일을 리턴한다.")
            void getCatToy() {
                assertThat(catToyController.getCatToy(newCatToy.getId()).getName()).isEqualTo(catToy.getName());
            }
        }
    }

    @Nested
    @DisplayName("createCatToy 메소드는")
    class Describe_createCatToy {

        CatToy catToy;
        int oldSize;

        @BeforeEach
        void setUp(){
            oldSize = catToyController.getCatToyList().size();
            catToy = new CatToy("멍멍", "야옹메이커", 1000, "https://source.unsplash.com/random");
        }

        @Test
        @DisplayName("새로운 장난감을 등록한다.")
        void createCatToy() {
            CatToy newCatToy = catToyController.createCatToy(catToy);
            int newSize = catToyController.getCatToyList().size();

            CatToy findNewCatToy = catToyController.getCatToy(newCatToy.getId());

            assertThat(newSize - oldSize).isEqualTo(1);
            assertThat(findNewCatToy).isNotNull();
            assertThat(newCatToy.getId()).isEqualTo(findNewCatToy.getId());
            assertThat(newCatToy.getName()).isEqualTo(findNewCatToy.getName());
            assertThat(newCatToy.getMaker()).isEqualTo(findNewCatToy.getMaker());
            assertThat(newCatToy.getPrice()).isEqualTo(findNewCatToy.getPrice());
            assertThat(newCatToy.getImageUrl()).isEqualTo(findNewCatToy.getImageUrl());
        }
    }

    @Nested
    @DisplayName("updateCatToy 메소드는")
    class Describe_updateCatToy {

        CatToy catToy;
        CatToy updateCatToy;

        @BeforeEach
        void setUp() {
            catToy = catToyController.createCatToy(new CatToy("멍멍", "야옹메이커", 1000, "https://source.unsplash.com/random"));
            updateCatToy = new CatToy("수정멍멍", "야옹수정메이커", 5000, "https://source.unsplash.com/random");
        }

        @Nested
        @DisplayName("장난감을 찾지 못했다면")
        class Context_with_find_no_cat_toy {

            @Test
            @DisplayName("CatToyNotFound 예외를 던진다.")
            void updateCatToyWithNoCatToy() {
                assertThatThrownBy(() -> {
                    catToyController.getCatToy(INVALID_ID);
                }).isInstanceOf(CatToyNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("장난감을 찾았다면")
        class Context_with_find_cat_boy {
            @Test
            @DisplayName("장난감 정보를 수정한다.")
            void updateCatToyWithCatToy() {
                CatToy resultCatToy = catToyController.updateCatToy(catToy.getId(), updateCatToy);

                assertThat(resultCatToy.getId()).isEqualTo(catToy.getId());
                assertThat(resultCatToy.getName()).isEqualTo(updateCatToy.getName());
                assertThat(resultCatToy.getMaker()).isEqualTo(updateCatToy.getMaker());
                assertThat(resultCatToy.getPrice()).isEqualTo(updateCatToy.getPrice());
                assertThat(resultCatToy.getImageUrl()).isEqualTo(updateCatToy.getImageUrl());
            }
        }
    }

    @Nested
    @DisplayName("deleteCatToy 메소드는")
    class Describe_deleteCatToy {

        @Nested
        @DisplayName("삭제할 장난감을 찾지 못하면")
        class Context_with_find_no_cat_toy {
            @Test
            @DisplayName("CatToyNotFound 예외를 던진다.")
            void deleteCatToyWithNoCatToy() {
                assertThatThrownBy(() -> {
                    catToyController.deleteCatToy(INVALID_ID);
                }).isInstanceOf(CatToyNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("삭제할 해당하는 장난감을 찾으면")
        class Context_with_find_cat_toy {

            CatToy catToy;
            CatToy newCatToy;

            @BeforeEach
            void setUp() {
                catToy = new CatToy("멍멍", "야옹메이커", 1000, "https://source.unsplash.com/random");
                newCatToy = catToyController.createCatToy(catToy);
            }

            @Test
            @DisplayName("장난감을 삭제한다.")
            void getCatToy() {
                catToyController.deleteCatToy(newCatToy.getId());
                assertThatThrownBy(() -> {
                    catToyController.deleteCatToy(newCatToy.getId());
                }).isInstanceOf(CatToyNotFoundException.class);

            }
        }
    }
}