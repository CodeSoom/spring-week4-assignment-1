package com.codesoom.assignment.toy.infra.api;

import com.codesoom.assignment.toy.domain.Product;
import com.codesoom.assignment.toy.domain.dto.ProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("상품을 등록 후 생성된 상품정보를 반환한다.")
    void createProduct() throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest("catToy", "CatMaker", 1200, "test/img.jpg");
        String jsonString = objectMapper.writeValueAsString(productRequest);

        // expected
        mockMvc.perform(post("/products")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("catToy"))
                .andExpect(jsonPath("maker").value("CatMaker"))
                .andExpect(jsonPath("price").value(1200))
                .andExpect(jsonPath("imageUrl").value("test/img.jpg"))
                .andDo(print());
    }

}
