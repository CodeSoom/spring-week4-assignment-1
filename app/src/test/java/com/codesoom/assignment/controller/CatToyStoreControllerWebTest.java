package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyStoreService;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.exception.NoDataException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CatToyStoreControllerWebTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext ctx;

    @MockBean
    private CatToyStoreService catToyStoreService;

    private static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_NAME = "첫번째 고양이 장난감";
    private static final Long INVALID_ID = 100L;
    private static final Long CREATE_ID = 100L;
    private static final String CREATE_NAME = "새로 생성된 고양이 장난감";
    private static final String UPDATE_NAME = "수정된 고양이 장난감";

    @BeforeEach
    void setUp(){
        setupCreateCatToy();
        setUpFixtures();
        setUpMockMvcCharacterEncoding();
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

    void setUpMockMvcCharacterEncoding(){
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilter(new CharacterEncodingFilter("UTF-8",true))
                .build();
    }
    @Test
    @DisplayName("리스트 조회")
    void getList() throws Exception {

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"첫번째 고양이 장난감\",\"maker\":\"첫번째 브랜드\",\"price\":1000,\"imageUrl\":\"https://avatars.githubusercontent.com/u/9374562?s=400&v=4\"}]"));

    }


    @Test
    @DisplayName("유효 ID에 대한 상세조회")
    void getDetailWithValidId() throws Exception {

        mockMvc.perform(get("/products/"+DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id",DEFAULT_ID).exists())
                .andExpect(jsonPath("name",DEFAULT_NAME).exists());

    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 상세조회")
    void getDetailWithInvalidId() throws Exception {

        mockMvc.perform(get("/products/"+INVALID_ID))
                .andExpect(status().isNotFound());


    }

    @Test
    @DisplayName("생성")
    void create() throws Exception {

        CatToy catToy = new CatToy();
        catToy.setName(CREATE_NAME);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(catToy)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name",CREATE_NAME).exists());

    }

    @Test
    @DisplayName("유효 ID에 대한 수정")
    void updateWithValidId() throws Exception {

        CatToy catToy = new CatToy();
        catToy.setName(UPDATE_NAME);

        mockMvc.perform(patch("/products/"+DEFAULT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(catToy)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name",UPDATE_NAME).exists());

    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 수정")
    void updateWithInvalidId() throws Exception {

        CatToy catToy = new CatToy();
        catToy.setName(UPDATE_NAME);

        mockMvc.perform(patch("/products/"+INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(catToy)))
                .andExpect(status().isNotFound());

    }


    @Test
    @DisplayName("유효 ID에 대한 삭제")
    void deleteWithValidId() throws Exception {

        mockMvc.perform(delete("/products/"+DEFAULT_ID))
                .andExpect(status().isNoContent());

        verify(catToyStoreService).delete(DEFAULT_ID);
    }

    @Test
    @DisplayName("유효하지 않는 ID에 대한 삭제")
    void deleteWithInvalidId() throws Exception {

        mockMvc.perform(delete("/products/"+INVALID_ID))
                .andExpect(status().isNotFound());

    }
}
