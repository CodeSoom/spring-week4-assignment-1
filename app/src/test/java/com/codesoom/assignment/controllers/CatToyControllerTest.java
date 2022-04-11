package com.codesoom.assignment.controllers;

import com.codesoom.assignment.CatToyNotFoundException;
import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CatController 클래스는")
public class CatToyControllerTest {
    private static final String TEST_NAME = "testName";
    private static final String TEST_MAKER = "testMaker";
    private static final Long TEST_PRICE = 5000L;
    private static final String TEST_IMAGE_PATH = "testImagePath.jpg";
    private static final String CREATE_POSTFIX = "...";
    private static final String UPDATE_POSTFIX = "!!!";

    private CatToyController catToyController;
    private CatToyService catToyService;

    @BeforeEach
    void setUp() {
        CatToyRepository catToyRepository = new CatToyRepository();
        catToyService = new CatToyService(catToyRepository);
        catToyController = new CatToyController(catToyService);

        CatToy catToy = new CatToy();
        catToy.setName(TEST_NAME);
        catToy.setMaker(TEST_MAKER);
        catToy.setPrice(TEST_PRICE);
        catToy.setImagePath(TEST_IMAGE_PATH);

        catToyController.create(catToy);
    }

    @Test
    @DisplayName("list 메소드는 CatToys에 있는 모든 CatToy를 반환합니다.")
    void list() {
        assertThat(catToyController.list()).isNotEmpty();
        assertThat(catToyController.list()).hasSize(1);
    }

    @Nested
    @DisplayName("detail 메소드는")
    class Describe_detail {
        @Nested
        @DisplayName("클라이언트가 요청한 CatToy의 id가 CatToys에 있으면")
        class Context_with_valid_id {
            @Test
            @DisplayName("id에 해당하는 CatToy를 반환합니다.")
            void detailWithValidId() {
                assertThat(catToyController.detail(1L)
                        .getName()).isEqualTo(TEST_NAME);
            }
        }

        @Nested
        @DisplayName("클라이언트가 요청한 CatToy의 id가 CatToys에 없으면")
        class Context_with_invalid_id {
            @Test
            @DisplayName("CatToyNotFoundException 예외를 던집니다.")
            void detailWithInvalidId() {
                assertThatThrownBy(() -> catToyController.detail(100L))
                        .isInstanceOf(CatToyNotFoundException.class);
            }
        }
    }

    @Test
    @DisplayName("create 메소드는 클라이언트가 요청한 새로운 CatToy를 CatToys에 추가해줍니다.")
    void create() {
        int oldSize = catToyController.list().size();

        CatToy source = new CatToy();
        source.setName(TEST_NAME + CREATE_POSTFIX);
        source.setMaker(TEST_MAKER + CREATE_POSTFIX);
        source.setPrice(TEST_PRICE + 1000L);
        source.setImagePath(CREATE_POSTFIX + TEST_IMAGE_PATH);

        catToyController.create(source);

        int newSize = catToyController.list().size();

        assertThat(newSize - oldSize).isEqualTo(1);
        assertThat(catToyController.list()).hasSize(2);
        assertThat(catToyController.list().get(0).getId()).isEqualTo(1L);
        assertThat(catToyController.list().get(0).getName()).isEqualTo(TEST_NAME);
        assertThat(catToyController.list().get(1).getId()).isEqualTo(2L);
        assertThat(catToyController.list().get(1).getName()).isEqualTo(TEST_NAME + CREATE_POSTFIX);
    }

    @Test
    @DisplayName("update 메소드는 CatToys에서 클라이언트가 요청한 id에 해당하는 CatToy의 " +
            "name, maker, price, imagePath를 변경해줍니다.")
    void update() {
        Long id = Long.valueOf(catToyController.list().size());
        CatToy catToy = catToyController.detail(id);

        assertThat(catToy.getName()).isEqualTo(TEST_NAME);

        catToy.setName(TEST_NAME + UPDATE_POSTFIX);

        catToyController.update(id, catToy);
        catToy = catToyController.detail(id);

        assertThat(catToy.getName()).isEqualTo(TEST_NAME + UPDATE_POSTFIX);
    }

    @Test
    @DisplayName("patch 메소드는 CatToys에서 클라이언트가 요청한 id에 해당하는 CatToy의 " +
            "name, maker, price, imagePath를 변경해줍니다.")
    void patch() {
        Long id = Long.valueOf(catToyController.list().size());
        CatToy catToy = catToyController.detail(id);

        assertThat(catToy.getMaker()).isEqualTo(TEST_MAKER);

        catToy.setMaker(TEST_MAKER + UPDATE_POSTFIX);

        catToyController.update(id, catToy);
        catToy = catToyController.detail(id);

        assertThat(catToy.getMaker()).isEqualTo(TEST_MAKER + UPDATE_POSTFIX);
    }

    @Test
    @DisplayName("delete 메소드는 CatToys에서 클라이언트가 요청한 id에 해당하는 CatToy를 지웁니다.")
    void delete() {
        int oldSize = catToyController.list().size();

        catToyController.delete(Long.valueOf(oldSize));

        int newSize = catToyController.list().size();

        assertThat(oldSize - newSize).isEqualTo(1);
        assertThat(catToyController.list()).hasSize(0);
    }
}
