package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.exception.CatToyInvalidPriceException;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apiguardian.api.API;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("CatToyController API Web 테스트")
@SpringBootTest
@AutoConfigureMockMvc
class CatToyControllerWebTest {
    private static final String NAME = "장난감 뱀";
    private static final Long PRICE = 5000L;
    private static final String MAKER = "애옹이네 장난감";
    private static final String IMAGE_URL = "https://cdn.pixabay.com/photo/2018/09/11/22/19/the-3670813_960_720.jpg";

    private static final String OTHER_NAME = "스크래처";
    private static final Long OTHER_PRICE = 8000L;
    private static final String OTHER_MAKER = "뽀떼";
    private static final String OTHER_IMAGE_URL = "https://www.biteme.co.kr/data/goods/21/06/24/1000005510/1000005510_detail_015.png";

    private static final String API_PATH = "/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatToyService catToyService;

    private ObjectMapper objectMapper;

    private CatToy catToy;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        setUpFixture();
    }

    private void setUpFixture() {
        catToy = new CatToy(1L, NAME, MAKER, PRICE, IMAGE_URL);


        given(catToyService.findAll()).willReturn(new ArrayList<>());

        //given(catToyService.save(catToy)).willReturn(catToy);

        given(catToyService.findById(1L)).willReturn(catToy);

        given(catToyService.findById(100L)).willThrow(new CatToyNotFoundException(100L));

        given(catToyService.updateCatToy(eq(1L), any(CatToy.class)))
                .willReturn(CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL));

        given(catToyService.updateCatToy(eq(100L), any(CatToy.class)))
                .willThrow(new CatToyNotFoundException(100L));

    }


    @DisplayName("장난감 목록을 얻을 수 있습니다 - GET /products")
    @Test
    void getProducts() throws Exception {
        mockMvc.perform(get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @DisplayName("식별자를 통해 장난감 상세조회를 할 수 있습니다. - GET /products/{id}")
    @Test
    void getProductById() throws Exception {
        mockMvc.perform(get(API_PATH + "/" + catToy.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(catToy.getName()))
                .andExpect(jsonPath("$.maker").value(catToy.getMaker()))
                .andExpect(jsonPath("$.price").value(catToy.getPrice()))
                .andExpect(jsonPath("$.imageUrl").value(catToy.getImageUrl()));
    }

    @DisplayName("존재하지 않는 식별자로 장난감을 조회할 경우 NOT_FOUND를 반환합니다. - GET /products/{notExistsId}")
    @Test
    void getProductByNotExistsId() throws Exception {
        mockMvc.perform(get(API_PATH + "/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(String.format(CatToyNotFoundException.DEFAULT_MESSAGE, 100L)));

    }

    @DisplayName("장난감을 등록할 수 있습니다. - POST /products")
    @Test
    void createProduct() throws Exception {
        final String jsonCatToy = objectMapper.writeValueAsString(catToy);

        mockMvc.perform(
                        post(API_PATH)
                                .content(jsonCatToy)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(catToy.getName()))
                .andExpect(jsonPath("$.maker").value(catToy.getMaker()))
                .andExpect(jsonPath("$.price").value(catToy.getPrice()))
                .andExpect(jsonPath("$.imageUrl").value(catToy.getImageUrl()));
    }

    @DisplayName("잘못 된 정보로 장난감을 등록할 수 없습니다 - POST /products")
    @Test
    void createProductInvalidPrice() throws Exception {
        final Long minusPrice = -3000L;

        final String jsonCatToy = "{\"id\":1," +
                "\"name\":\"장난감 뱀\"," +
                "\"maker\":\"애옹이네 장난감\"," +
                "\"price\":"+minusPrice+"," +
                "\"imageUrl\":\"https://cdn.pixabay.com/photo/2018/09/11/22/19/the-3670813_960_720.jpg\"}";

        mockMvc.perform(
                        post(API_PATH)
                                .content(jsonCatToy)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(String.format(CatToyInvalidPriceException.DEFAULT_MESSAGE, minusPrice)));

    }

    @DisplayName("식별자를 이용해 장난감 정보를 수정할 수 있습니다 - PATCH /products/{id}")
    @Test
    void updateProduct() {

    }

    @DisplayName("존재하지 않는 식별자의 장난감 정보를 수정하려 할 경우 NOT_FOUND로 실패합니다 - PATCH /products/{notExistsId}")
    @Test
    void updateProductNotExistsId() {

    }

    @DisplayName("식별자를 이용해 장난감 정보를 삭제할 수 있습니다 - DELETE /product/{id}")
    @Test
    void deleteProduct() {

    }

    @DisplayName("존재하지 않는 식별자의 장난감 정보를 삭제하려 할 경우 NOT_FOUND로 실패합니다. - DELETE /product/{notExistsId}")
    @Test
    void deleteProductNotExistsId() {

    }

}
