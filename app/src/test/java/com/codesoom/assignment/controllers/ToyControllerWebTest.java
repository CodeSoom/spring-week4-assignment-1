package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyService;
import com.codesoom.assignment.domain.Toy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("요청을 받은 ToyController 클래스는")
class ToyControllerWebTest {
    private final Long givenSavedId = 1L;
    private final Long givenUnsavedId = 100L;
    private final String givenName = "장난감 칼";
    private final String givenBrand = "코드숨";
    private final int givenPrice = 5000;
    private final String givenImageUrl = "https://cdn.shopify.com/s/files/1/0940/6942/products/DSC0243_800x.jpg";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToyService toyService;

    private RequestBuilder requestBuilder;
    private String uriTemplate;

    private List<Toy> toyList;
    private Toy toy;

    private OutputStream outputStream;
    private ObjectMapper objectMapper = new ObjectMapper();
    private String taskJsonString;

    @BeforeEach
    void setUp() {
        toyList = new ArrayList<Toy>();

        toy = new Toy(givenName, givenBrand, givenPrice, givenImageUrl);
        toy.setId(givenSavedId);
    }

    @Nested
    @DisplayName("GET /products 요청은")
    class Describe_get_products_request {
        @BeforeEach
        void setRequest() {
            requestBuilder = get("/products");
        }

        @Nested
        @DisplayName("저장된 toy가 없다면,")
        class Context_without_any_toy {
            @Test
            @DisplayName("200 Ok와 비어 있는 toy리스트를 응답한다.")
            void it_responds_200_ok_and_empty_list() throws Exception {
                given(toyService.getToys()).willReturn(List.of());

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json("[]"));
            }
        }

        @Nested
        @DisplayName("저장된 toy가 있다면,")
        class Context_with_toy {
            @BeforeEach
            void setSavedToy() {
                toyList = new ArrayList<Toy>();
                toyList.add(toy);

                given(toyService.getToys()).willReturn(toyList);
            }

            @Test
            @DisplayName("200 Ok와 비어있지 않은 toy리스트를 응답한다.")
            void it_responds_200_ok_and_not_empty_list() throws Exception {
                outputStream = new ByteArrayOutputStream();
                objectMapper.writeValue(outputStream, toyList);
                taskJsonString = outputStream.toString();

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json(taskJsonString));
            }
        }
    }
}