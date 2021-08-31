package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import com.codesoom.assignment.service.CatToyCommandService;
import com.codesoom.assignment.service.CatToyQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CatToy Controller WebTest의")
public class CatToyControllerWebTest {

    public static final long INVALID_ID = 99999L;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CatToyCommandService catToyCommandService;

    @MockBean
    private CatToyQueryService catToyQueryService;

    @BeforeEach
    void setUp() {
        ArrayList<CatToy> catToyList = new ArrayList<>();
        CatToy catToy = new CatToy("멍멍", "야옹메이커", 1000, "https://source.unsplash.com/random");
        catToyList.add(catToy);

        given(catToyQueryService.getCatToyList()).willReturn(catToyList);
        given(catToyQueryService.getCatToy(catToy.getId())).willReturn(catToy);
    }

    @Nested
    @DisplayName("getCatToyList 메소드는")
    class Describe_getCatToyList {

        @Nested
        @DisplayName("장난감이 없으면")
        class Context_with_no_cat_toy {

            @BeforeEach
            void setUp() {
                given(catToyQueryService.getCatToyList()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("빈 리스트를 리턴하고 OK(200)을 응답한다.")
            void list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("[]"))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("장난감이 존재하면")
        class Context_with_cat_toy {

            @Test
            @DisplayName("장난감 리스트를 리턴하고 OK(200)을 응답한다.")
            void list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("1000")))
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("getCatToy 메소드는")
    class Describe_getCatToy {

        @Nested
        @DisplayName("장난감이 있으면")
        class Context_with_cat_toy {

            @Test
            @DisplayName("장난감을 리턴하고 OK(200)를 응답한다.")
            void getCatToyWithValidId() throws Exception {
                mockMvc.perform(get("/products/{id}", 1L))
                        .andExpect(status().isOk())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("장난감이 없으면")
        class Context_with_no_cat_toy {

            @BeforeEach
            void setUp() {
                given(catToyQueryService.getCatToy(INVALID_ID)).willThrow(new CatToyNotFoundException(Long.toString(INVALID_ID)));
            }

            @Test
            @DisplayName("CatToyNotFound를 던지고 Not Found(404)를 응답한다.")
            void getCatToyWithInvalidId() throws Exception {
                mockMvc.perform(get("/products/{id}", INVALID_ID))
                        .andExpect(status().isNotFound())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof CatToyNotFoundException))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("@PathVariable에 문자가 넘어오면")
        class Context_with_bad_path_variable {

            @Test
            @DisplayName("Not Found를 응답한다.")
            void detailWithInvalidPathVariable() throws Exception {
                mockMvc.perform(get("/products/undefined"))
                        .andExpect(status().isNotFound())
                        .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException))
                        .andDo(print());
            }
        }
    }



}
