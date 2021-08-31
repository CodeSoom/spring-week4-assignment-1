package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.exception.CatToyInvalidPriceException;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.codesoom.assignment.Constant.IMAGE_URL;
import static com.codesoom.assignment.Constant.MAKER;
import static com.codesoom.assignment.Constant.NAME;
import static com.codesoom.assignment.Constant.PRICE;
import static com.codesoom.assignment.Constant.OTHER_IMAGE_URL;
import static com.codesoom.assignment.Constant.OTHER_MAKER;
import static com.codesoom.assignment.Constant.OTHER_NAME;
import static com.codesoom.assignment.Constant.OTHER_PRICE;

@DisplayName("CatToyController API Web 테스트")
@SpringBootTest
@AutoConfigureMockMvc
class CatToyControllerWebTest {


    private static final Long MINUS_PRICE = -3000L;

    private static final String API_PATH = "/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatToyService catToyService;

    private ObjectMapper objectMapper;

    private CatToy catToy;

    private CatToy catToyWithInvalidPrice;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        setUpFixture();
    }

    private void setUpFixture() {
        catToy = new CatToy(1L, NAME, MAKER, PRICE, IMAGE_URL);

        catToyWithInvalidPrice = new CatToy(NAME, MAKER, PRICE, IMAGE_URL);
        ReflectionTestUtils.setField(catToyWithInvalidPrice, "price", MINUS_PRICE);


        given(catToyService.findAll()).willReturn(new ArrayList<>());

        given(catToyService.save(catToy)).willReturn(catToy);

        given(catToyService.save(catToyWithInvalidPrice))
                .willThrow(new CatToyInvalidPriceException(MINUS_PRICE));

        given(catToyService.findById(1L))
                .willReturn(catToy)
                .willReturn(catToy)
                .willThrow(new CatToyNotFoundException(1L));

        given(catToyService.findById(100L))
                .willThrow(new CatToyNotFoundException(100L));

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
        final String jsonCatToy = objectMapper.writeValueAsString(catToyWithInvalidPrice);

        mockMvc.perform(
                        post(API_PATH)
                                .content(jsonCatToy)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(String.format(CatToyInvalidPriceException.DEFAULT_MESSAGE, MINUS_PRICE)));

    }

    @DisplayName("식별자를 이용해 장난감 정보를 수정할 수 있습니다 - PATCH /products/{id}")
    @Test
    void updateProduct() throws Exception {
        final CatToy otherCatToy = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        final String jsonOtherCatToy = objectMapper.writeValueAsString(otherCatToy);

        mockMvc.perform(
                        patch(API_PATH + "/1")
                                .content(jsonOtherCatToy)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(otherCatToy.getName()))
                .andExpect(jsonPath("$.maker").value(otherCatToy.getMaker()))
                .andExpect(jsonPath("$.price").value(otherCatToy.getPrice()))
                .andExpect(jsonPath("$.imageUrl").value(otherCatToy.getImageUrl()));

    }

    @DisplayName("존재하지 않는 식별자의 장난감 정보를 수정하려 할 경우 NOT_FOUND로 실패합니다 - PATCH /products/{notExistsId}")
    @Test
    void updateProductNotExistsId() throws Exception {
        final CatToy otherCatToy = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        final String jsonOtherCatToy = objectMapper.writeValueAsString(otherCatToy);

        mockMvc.perform(
                        patch(API_PATH + "/100")
                                .content(jsonOtherCatToy)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(String.format(CatToyNotFoundException.DEFAULT_MESSAGE, 100L)));

    }

    @DisplayName("식별자를 이용해 장난감 정보를 삭제할 수 있습니다 - DELETE /product/{id}")
    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(get(API_PATH + "/" + catToy.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete(API_PATH +"/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(API_PATH + "/" + catToy.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(String.format(CatToyNotFoundException.DEFAULT_MESSAGE, 1L)));
    }

    @DisplayName("존재하지 않는 식별자의 장난감 정보를 삭제하려 할 경우 NOT_FOUND로 실패합니다. - DELETE /product/{notExistsId}")
    @Test
    void deleteProductNotExistsId() throws Exception {
        mockMvc.perform(delete(API_PATH +"/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(String.format(CatToyNotFoundException.DEFAULT_MESSAGE, 100L)));
    }

}
