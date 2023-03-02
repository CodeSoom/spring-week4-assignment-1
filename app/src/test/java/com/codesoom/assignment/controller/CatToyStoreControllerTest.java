package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyStoreService;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.exception.NoDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class CatToyStoreControllerTest {

    private CatToyStoreController catToyStoreController;
    private CatToyStoreService catToyStoreService;

    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_NAME = "첫번째 고양이 장난감";
    private static final Long INVALID_ID = 100L;
    private static final String CREATE_NAME = "새로 생성된 고양이 장난감";
    private static final String UPDATE_NAME = "수정된 고양이 장난감";

    @BeforeEach
    void setUp(){
        catToyStoreService = mock(CatToyStoreService.class);
        catToyStoreController = new CatToyStoreController(catToyStoreService);

        setupCreateCatToy();
        setUpFixtures();
    }

    void setupCreateCatToy(){
        CatToy catToy = new CatToy();
        catToy.setId(DEFAULT_ID);
        catToy.setName(DEFAULT_NAME);
        catToy.setMaker("첫번째 브랜드");
        catToy.setPrice(1000);
        catToy.setImageUrl("https://avatars.githubusercontent.com/u/9374562?s=400&v=4");

        List<CatToy> list = new ArrayList<>();
        list.add(catToy);

        given(catToyStoreService.list()).willReturn(list);
        given(catToyStoreService.detail(DEFAULT_ID)).willReturn(catToy);
    }

    void setUpFixtures(){
        given(catToyStoreService.detail(INVALID_ID))
                .willThrow(new NoDataException(INVALID_ID));

        given(catToyStoreService.create(any(CatToy.class)))
                .will(invocation -> invocation.getArgument(0));

        given(catToyStoreService.update(eq(DEFAULT_ID),any(CatToy.class)))
                .will(invocation -> invocation.getArgument(1));

        given(catToyStoreService.update(eq(INVALID_ID),any(CatToy.class)))
                .willThrow(new NoDataException(INVALID_ID));

        given(catToyStoreService.delete(DEFAULT_ID)).willReturn(null);

        given(catToyStoreService.delete(INVALID_ID))
                .willThrow(new NoDataException(INVALID_ID));
    }
    @Test
    @DisplayName("리스트 조회")
    void getList() {

        assertThat(catToyStoreController.list()).hasSize(1);

    }


    @Test
    @DisplayName("유효 ID에 대한 상세조회")
    void getDetailWithValidId() {

        assertThat(catToyStoreController.detail(DEFAULT_ID).getName()).isEqualTo(DEFAULT_NAME);

    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 상세조회")
    void getDetailWithInvalidId() {

        assertThatThrownBy(() -> catToyStoreController.detail(INVALID_ID))
                .isInstanceOf(NoDataException.class);

    }

    @Test
    @DisplayName("생성")
    void create() {

        CatToy catToy = new CatToy();
        catToy.setName(CREATE_NAME);

        assertThat(catToyStoreController.create(catToy).getName()).isEqualTo(CREATE_NAME);

    }

    @Test
    @DisplayName("유효 ID에 대한 수정")
    void updateWithValidId() {

        CatToy catToy = new CatToy();
        catToy.setName(UPDATE_NAME);

        assertThat(catToyStoreController.update(DEFAULT_ID,catToy).getName())
                .isEqualTo(UPDATE_NAME);

    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 수정")
    void updateWithInvalidId() {

        CatToy catToy = new CatToy();
        catToy.setName(UPDATE_NAME);

        assertThatThrownBy(() -> catToyStoreController.update(INVALID_ID,catToy))
                .isInstanceOf(NoDataException.class);

    }


    @Test
    @DisplayName("유효 ID에 대한 삭제")
    void deleteWithValidId() {

        catToyStoreController.delete(DEFAULT_ID);
        verify(catToyStoreService).delete(DEFAULT_ID);
    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 삭제")
    void deleteWithInvalidId() {

        assertThatThrownBy(()->catToyStoreController.delete(INVALID_ID))
                .isInstanceOf(NoDataException.class);

    }
}