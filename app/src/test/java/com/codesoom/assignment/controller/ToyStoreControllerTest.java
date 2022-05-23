package com.codesoom.assignment.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ToyStoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("고양이 장난감 등록 후 등록된 정보 반환")
    @Test
    void addToyTest() throws Exception {

        ProductDto source = new ProductDto("장난감 뱀", "애옹이네 장난감", 5000, "abc_aaa.jpeg");

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productToString(source)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("장난감 뱀")));
    }

    private String productToString(Object source) throws JsonProcessingException {
        return objectMapper.writeValueAsString(source);
    }

    public class ProductDto {
        private String name;
        private String maker;
        private int price;
        private String imageUrl;

        public ProductDto(String name, String maker, int price, String imageUrl) {
            this.name = name;
            this.maker = maker;
            this.price = price;
            this.imageUrl = imageUrl;
        }
    }
}
