package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.CategoryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("CategoryController 클래스의")
class CategoryControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String URL_PREFIX = "/categories/";

    private List<CategoryDto> dummyCategoryDtos;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        dummyCategoryDtos = Arrays.asList(
                new CategoryDto("category1", false),
                new CategoryDto("category2", false),
                new CategoryDto("category3", false)
        );
    }

    private ResultActions performGet() throws Exception {
        return mockMvc.perform(get(URL_PREFIX));
    }

    private ResultActions performPost(CategoryDto dto) throws Exception {
        String asString = objectMapper.writeValueAsString(dto);

        return mockMvc.perform(
                post(URL_PREFIX)
                        .content(asString)
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("GET /categories API는 등록된 카테고리가 없으면 빈 리스트로 응답한다.")
    void given_none_categories_registered_when_requested_to_get_categories_api_then_responses_with_empty_list() throws Exception {
        performGet()
                .andExpect(content().string("[]"));
    }

    @Test
    @DisplayName("GET /categories API는 등록된 카테고리가 있으면 등록된 모든 카테고리를 이름 순으로 담은 리스트를 json 형식으로 응답한다.")
    void given_categories_registered_exists_when_requested_to_get_categories_api_then_responses_list_containing_all_categories() throws Exception {
        for (CategoryDto dummyCategoryDto : dummyCategoryDtos) {
            performPost(dummyCategoryDto);
        }

        dummyCategoryDtos.sort(Comparator.comparing(CategoryDto::getName));

        ResultActions resultActions = performGet();
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        for (int i = 0; i < dummyCategoryDtos.size(); i++) {
            resultActions
                    .andExpect(jsonPath("$.[" + i + "].name").value(dummyCategoryDtos.get(i).getName()));
        }
    }

}
