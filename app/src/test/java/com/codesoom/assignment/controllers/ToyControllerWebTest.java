package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.application.ToyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ToyControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToyService toyService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Long NOT_EXISTED_ID = 100L;
    private static final String TOY_NAME = "test 장난감";
    private static final String TOY_MAKER = "애옹이네 장난감 가게";
    private static final Integer TOY_PRICE = 5000;
    private static final String TOY_IMAGE = "someUrl";
    private static final String UPDATE_PREFIX = "Update";

    @BeforeEach
    void setUp() {
        Toy toy = new Toy(TOY_NAME, TOY_MAKER, TOY_PRICE, TOY_IMAGE);
        List<Toy> toys = new ArrayList<>();
        toys.add(toy);

        given(toyService.getProducts()).willReturn(toys);
        given(toyService.getProduct(1L)).willReturn(toy);
        given(toyService.getProduct(NOT_EXISTED_ID)).willThrow(new ProductNotFoundException(NOT_EXISTED_ID));
        given(toyService.deleteProduct(NOT_EXISTED_ID)).willThrow(new ProductNotFoundException(NOT_EXISTED_ID));
        given(toyService.updateProduct(eq(NOT_EXISTED_ID), any(Toy.class)))
                .willThrow(new ProductNotFoundException(NOT_EXISTED_ID));
    }

    @Test
    void products() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(TOY_NAME)));

        verify(toyService).getProducts();
    }

    @Test
    void productWithExistedId() throws Exception {
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());

        verify(toyService).getProduct(1L);
    }

    @Test
    void productWithNotExistedId() throws Exception {
        mockMvc.perform(get("/products/100"))
                .andExpect(status().isNotFound());

        verify(toyService).getProduct(NOT_EXISTED_ID);
    }

    @Test
    void createProduct() throws Exception {
        Toy source = new Toy(TOY_NAME, TOY_MAKER, TOY_PRICE, TOY_IMAGE);
        source.setId(1L);

        String content = objectMapper.writeValueAsString(source);

        mockMvc.perform(
                post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());

        verify(toyService).createProduct(any(Toy.class));
    }

    @Test
    void deleteProductWithExistedId() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(toyService).deleteProduct(1L);
    }

    @Test
    void deleteProductWithNotExistedId() throws Exception {
        mockMvc.perform(delete("/products/100"))
                .andExpect(status().isNotFound());

        verify(toyService).deleteProduct(NOT_EXISTED_ID);
    }

    @Test
    void updateProductWithExistedId() throws Exception {
        Toy source = new Toy(UPDATE_PREFIX + TOY_NAME, TOY_MAKER, TOY_PRICE, TOY_IMAGE);

        String content = objectMapper.writeValueAsString(source);

        mockMvc.perform(
                put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        )
                .andExpect(status().isOk());

        verify(toyService).updateProduct(eq(1L), any(Toy.class));
    }

    @Test
    void updateProductWithNotExistedId() throws Exception {
        Toy source = new Toy(UPDATE_PREFIX + TOY_NAME, TOY_MAKER, TOY_PRICE, TOY_IMAGE);

        String content = objectMapper.writeValueAsString(source);

        mockMvc.perform(
                        put("/products/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isNotFound());

        verify(toyService).updateProduct(eq(100L), any(Toy.class));
    }
}
