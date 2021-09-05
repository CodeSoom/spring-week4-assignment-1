package com.codesoom.assignment.controller;


import com.codesoom.assignment.dto.ProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.UnsupportedEncodingException;

import static com.codesoom.assignment.constant.ProductTestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private ProductDto request;
    private ProductDto changeRequest;
    private Long productId;

    @BeforeEach
    void characterEncodingSetup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @BeforeEach
    void setup() throws Exception {
        request = new ProductDto.Request(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL);
        productId = createProductResponse().getId();
        changeRequest = new ProductDto.Request(CHANGE_NAME, CHANGE_MAKER, CHANGE_PRICE, CHANGE_IMAGE_URL);
    }

    @Test
    @DisplayName("고양이 장난감을 생성 하여 반환한다.")
    void createCatToy() throws Exception {
        // when
        MvcResult mvcResult = mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // then
        ProductDto.Response response = getResponse(mvcResult);

        assertThat(response.getName()).isEqualTo(TOY_NAME);
        assertThat(response.getId()).isNotNull();

    }

    @Test
    @DisplayName("고양이 장난감 리스트를 검색하여 반환한다.")
    void selectCatToyList() throws Exception {
        // when
        // then
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("고양이 장난감을 검색하여 반환한다.")
    void selectCatToy() throws Exception {
        // when
        MvcResult mvcResult = mockMvc.perform(get("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        ProductDto.Response response = getResponse(mvcResult);
        assertThat(response.getName()).isEqualTo(TOY_NAME);
        assertThat(response.getId()).isNotNull();
    }

    @Test
    @DisplayName("고양이 장난감을 수정하여 반환한다.")
    void modifyCatToy() throws Exception {
        // when
        MvcResult mvcResult = mockMvc.perform(patch("/products/" + productId)
                        .content(objectMapper.writeValueAsString(changeRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        ProductDto.Response response = getResponse(mvcResult);
        assertThat(response.getName()).isEqualTo(CHANGE_NAME);
        assertThat(response.getMaker()).isEqualTo(CHANGE_MAKER);

    }

    @Test
    @DisplayName("고양이 장난감을 삭제")
    void deleteCatToy() throws Exception {
        // when
        // then
        mockMvc.perform(delete("/products/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private ProductDto.Response getResponse(MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        return new ObjectMapper()
                .readValue(mvcResult.getResponse().getContentAsString(), ProductDto.Response.class);
    }

    private ProductDto.Response createProductResponse() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        return getResponse(mvcResult);
    }
}
