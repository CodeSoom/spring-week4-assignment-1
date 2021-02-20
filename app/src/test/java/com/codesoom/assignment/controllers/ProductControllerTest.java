package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @Test
    void list() throws Exception {
        //현재 상품이 등록되어있는 상태
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("오뎅꼬치")));
    }

}