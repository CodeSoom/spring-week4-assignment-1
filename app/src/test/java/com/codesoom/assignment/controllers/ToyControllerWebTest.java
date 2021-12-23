package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.services.ToyService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @BeforeEach
    void setUp() {
        Toy toy = new Toy();
        toy.setName("test");
        List<Toy> toys = new ArrayList<>();
        toys.add(toy);

        given(toyService.getProducts()).willReturn(toys);
        given(toyService.getProduct(1L)).willReturn(toy);
    }

    @Test
    void products() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test")));
    }

    @Test
    void product() throws Exception {
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void createProduct() throws Exception {
        Toy source = new Toy();

        source.setId(1L);
        source.setName("test");
        source.setMaker("test maker");
        source.setImage("test image");
        source.setPrice(5000);

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
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());

        verify(toyService).deleteProduct(1L);
    }
}
