package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("ProductController 클래스의")
class ProductControllerTest {
//
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//
//    private static final String URL_PREFIX = "/products/";
//
//    private static final String NAME = "name";
//    private static final String MAKER = "maker";
//    private static final Integer PRICE = 100;
//    private static final String IMAGE_URL = "http://localhost:8080/original";
//
//    private static final String UPDATED_NAME = "updated name";
//    private static final String UPDATED_MAKER = "updated maker";
//    private static final Integer UPDATED_PRICE = 500;
//    private static final String UPDATED_IMAGE_URL = "http://localhost:8080/updated";
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    ResultActions performGet(Long... id) throws Exception {
//        String url = URL_PREFIX;
//        if (id.length > 0) {
//            url += id[0];
//        }
//
//        return mockMvc.perform(get(url));
//    }
//
//    ResultActions performPost(com.codesoom.assignment.dto.ProductDto dto) throws Exception {
//        final String jsonString = objectMapper.writeValueAsString(dto);
//        return mockMvc.perform(
//                post(URL_PREFIX)
//                        .content(jsonString)
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//    }
//
//    ResultActions performPatch(Long id, Product source) throws Exception {
//        final String jsonString = objectMapper.writeValueAsString(source);
//        return mockMvc.perform(
//                patch(URL_PREFIX + id)
//                        .content(jsonString)
//                        .contentType(MediaType.APPLICATION_JSON)
//        );
//    }
//
//    ResultActions performDelete(Long id) throws Exception {
//        return mockMvc.perform(delete(URL_PREFIX + id));
//    }
//
//    static Stream<String> nullAndBlankString() {
//        return Stream.of(null, "", "   ");
//    }
//
//    @Test
//    @DisplayName("GET /products API는 등록된 제품이 없으면 빈 리스트를 json 형식으로 응답한다.")
//    void given_none_products_registered_when_requested_to_get_products_api_it_responses_with_empty_list_as_json() throws Exception {
//        performGet()
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string(containsString("[]")));
//    }
//
//    @Test
//    @DisplayName("GET /products API는 등록된 제품이 있으면 비어 있지 않은 리스트를 json 형식으로 응답한다.")
//    void given_products_registered_exists_when_requested_to_get_products_api_it_responses_with_not_empty_list_as_json() throws Exception {
//        performPost(new com.codesoom.assignment.dto.ProductDto(NAME, MAKER, PRICE, IMAGE_URL));
//
//        performGet()
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].name").value(NAME))
//                .andExpect(jsonPath("$[0].maker").value(MAKER))
//                .andExpect(jsonPath("$[0].price").value(PRICE))
//                .andExpect(jsonPath("$[0].imageUrl").value(IMAGE_URL));
//    }
//
//    @Test
//    @DisplayName("GET /products/{id} API는 등록된 id로 호출하면 해당 제품을 json 형식으로 응답한다.")
//    void when_requested_to_get_products_id_api_with_existing_id_it_responses_with_corresponding_product_json() throws Exception {
//        MvcResult mvcResult = performPost(new com.codesoom.assignment.dto.ProductDto(NAME, MAKER, PRICE, IMAGE_URL)).andReturn();
//        Product savedProduct = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class);
//        Long id = savedProduct.getId();
//
//        performGet(id)
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().json(objectMapper.writeValueAsString(savedProduct)));
//    }
//
//    @Test
//    @DisplayName("GET /products/{id} API는 등록되지 않은 id로 호출하면 404 코드로 응답한다.")
//    void when_requested_to_get_products_id_api_with_not_existing_id_it_responses_with_404() throws Exception {
//        performGet(Long.MAX_VALUE)
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("POST /products API는 등록된 제품을 json 형식으로 응답한다.")
//    void when_requested_to_post_products_api_it_responses_with_json_of_registered_product() throws Exception {
//        performPost(new com.codesoom.assignment.dto.ProductDto(NAME, MAKER, PRICE, IMAGE_URL))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value(NAME))
//                .andExpect(jsonPath("$.maker").value(MAKER))
//                .andExpect(jsonPath("$.price").value(PRICE))
//                .andExpect(jsonPath("$.imageUrl").value(IMAGE_URL));
//    }
//
//    @ParameterizedTest(name = "POST /products API는 name을 입력하지 않거나 공백 문자열을 입력하면 400 코드로 응답한다.")
//    @MethodSource("nullAndBlankString")
//    void when_requested_to_post_products_api_with_none_or_blank_name_it_responses_with_400_code(String name) throws Exception {
//        performPost(new com.codesoom.assignment.dto.ProductDto(name, MAKER, PRICE, IMAGE_URL))
//                .andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest(name = "POST /products API는 maker을 입력하지 않거나 공백 문자열을 입력하면 400 코드로 응답한다.")
//    @MethodSource("nullAndBlankString")
//    void when_requested_to_post_products_api_with_none_or_blank_maker_then_responses_with_400_code(String maker) throws Exception {
//        performPost(new com.codesoom.assignment.dto.ProductDto(NAME, maker, PRICE, IMAGE_URL))
//                .andExpect(status().isBadRequest());
//    }
//
//    @ParameterizedTest(name = "POST /products API는 price을 입력하지 않으면 400 코드로 응답한다.")
//    @NullSource
//    void when_requested_to_post_products_api_with_none_or_blank_price_then_responses_with_400_code(Integer price) throws Exception {
//        performPost(new com.codesoom.assignment.dto.ProductDto(NAME, MAKER, price, IMAGE_URL))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("PATCH /products/{id} API는 존재하는 id로 호출하면 해당 제품을 수정한다.")
//    void when_requested_to_patch_products_id_with_existing_id_then_corresponding_product_is_updated() throws Exception {
//        MvcResult mvcResult = performPost(new com.codesoom.assignment.dto.ProductDto(NAME, MAKER, PRICE, IMAGE_URL)).andReturn();
//        Long id = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class).getId();
//
//        performPatch(id, new Product(UPDATED_NAME, UPDATED_MAKER, UPDATED_PRICE, UPDATED_IMAGE_URL))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value(UPDATED_NAME))
//                .andExpect(jsonPath("$.maker").value(UPDATED_MAKER))
//                .andExpect(jsonPath("$.price").value(UPDATED_PRICE))
//                .andExpect(jsonPath("$.imageUrl").value(UPDATED_IMAGE_URL));
//    }
//
//    @Test
//    @DisplayName("PATCH /products/{id} API는 등록되지 않은 id로 호출하면 404 코드로 응답한다.")
//    void when_requested_to_patch_products_id_api_with_not_existing_id_it_responses_with_404() throws Exception {
//        performPost(new com.codesoom.assignment.dto.ProductDto(NAME, MAKER, PRICE, IMAGE_URL));
//
//        performPatch(Long.MAX_VALUE, new Product(UPDATED_NAME, UPDATED_MAKER, UPDATED_PRICE, UPDATED_IMAGE_URL))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("PATCH /products/{id} API는 클라이언트가 변경할 정보를 모두 입력하지 않으면 400 코드로 응답한다.")
//    void when_requested_to_patch_products_id_api_with_none_update_information_it_responses_with_400() throws Exception {
//        MvcResult mvcResult = performPost(new com.codesoom.assignment.dto.ProductDto(NAME, MAKER, PRICE, IMAGE_URL)).andReturn();
//        Long id = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class).getId();
//
//        performPatch(id, new Product(null, null, null, null))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("DELETE /products/{id} API는 존재하는 id로 호출하면 해당 제품을 삭제한다.")
//    void when_requested_to_delete_products_id_with_existing_id_then_corresponding_product_is_deleted() throws Exception {
//        MvcResult mvcResult = performPost(new com.codesoom.assignment.dto.ProductDto(NAME, MAKER, PRICE, IMAGE_URL)).andReturn();
//        Long id = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Product.class).getId();
//
//        performDelete(id)
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @DisplayName("DELETE /products/{id} API는 존재하지 않는 id로 호출하면 해당 제품을 삭제한다.")
//    void when_requested_to_delete_products_id_with_not_existing_id_then_corresponding_product_is_deleted() throws Exception {
//        performPost(new com.codesoom.assignment.dto.ProductDto(NAME, MAKER, PRICE, IMAGE_URL)).andReturn();
//
//        performDelete(Long.MAX_VALUE)
//                .andExpect(status().isNotFound());
//    }
}
