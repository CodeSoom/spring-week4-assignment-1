package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ToyNotFoundException;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ToyController 클래스가 받은 요청 중")
class ToyControllerWebTest {
    private final Long givenSavedId = 1L;
    private final Long givenUnsavedId = 100L;
    private final String givenName = "장난감 칼";
    private final String givenBrand = "코드숨";
    private final int givenPrice = 5000;
    private final String givenImageUrl = "https://cdn.shopify.com/s/files/1/0940/6942/products/DSC0243_800x.jpg";
    private final String givenUpdatePostfixText = "+";
    private final int givenUpdatePostfixNumber = 1;

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
    private String toyJsonString;

    @BeforeEach
    void setUp() throws IOException {
        toy = new Toy(givenName, givenBrand, givenPrice, givenImageUrl);
        toy.setId(givenSavedId);

        outputStream = new ByteArrayOutputStream();
        objectMapper.writeValue(outputStream, toy);
        toyJsonString = outputStream.toString();
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
                toyJsonString = outputStream.toString();

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json(toyJsonString));
            }
        }
    }

    @Nested
    @DisplayName("GET /product/{id} 요청은")
    class Describe_get_product_id_request {
        private String stringFormat = "/products/%d";
        private Long givenId;

        @Nested
        @DisplayName("저장된 toy의 id라면")
        class Context_with_saved_toy_id {
            @BeforeEach
            void setRequest() {
                givenId = givenSavedId;
                uriTemplate = String.format(stringFormat, givenId);
                requestBuilder = get(uriTemplate);
            }

            @Test
            @DisplayName("200 OK와 저장된 task를 응답한다.")
            void it_respond_200_ok_and_saved_toy() throws Exception {
                given(toyService.getToy(givenId)).willReturn(toy);

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json(toyJsonString));
            }
        }

        @Nested
        @DisplayName("저장되지 않은 toy의 id라면")
        class Context_with_unsaved_toy_id {
            @BeforeEach
            void setRequest() {
                givenId = givenUnsavedId;
                uriTemplate = String.format(stringFormat, givenId);
                requestBuilder = get(uriTemplate);
            }

            @Test
            @DisplayName("404 Not Found를 응답한다.")
            void it_respond_404_not_found() throws Exception {
                given(toyService.getToy(givenId))
                        .willThrow(new ToyNotFoundException(givenUnsavedId));

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("POST /products 요청은")
    class Describe_post_toys_request {
        @BeforeEach
        void setRequest() {
            requestBuilder = post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(toyJsonString);
        }

        @Test
        @DisplayName("toy를 추가하고, 201 Created와 추가된 toy를 응답한다.")
        void it_add_toy_and_respond_201_created_and_added_toy() throws Exception {
            given(toyService.createToy(any(Toy.class))).willReturn(toy);

            mockMvc.perform(requestBuilder)
                    .andExpect(status().isCreated())
                    .andExpect(content().json(toyJsonString));
        }
    }

    @Nested
    @DisplayName("PATCH /products/{id} 요청은")
    class Describe_patch_task_id_request {
        private String stringFormat = "/products/%d";
        private Long givenId;

        @Nested
        @DisplayName("저장된 toy의 id를 가지고 있다면")
        class Context_with_saved_toy_id {
            @BeforeEach
            void setRequest() throws IOException {
                givenId = givenSavedId;

                toy = new Toy(
                        givenName + givenUpdatePostfixText,
                        givenBrand + givenUpdatePostfixText,
                        givenPrice + givenUpdatePostfixNumber,
                        givenImageUrl + givenUpdatePostfixText
                );
                toy.setId(givenSavedId);

                outputStream = new ByteArrayOutputStream();
                objectMapper.writeValue(outputStream, toy);
                toyJsonString = outputStream.toString();

                uriTemplate = String.format(stringFormat, givenId);
                requestBuilder = patch(uriTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toyJsonString);
            }

            @Test
            @DisplayName("toy를 수정하고, 200 Ok와 수정된 toy를 응답한다.")
            void it_modify_toy_and_respond_200_ok_and_modified_toy() throws Exception {
                given(toyService.updateToy(any(Toy.class))).willReturn(toy);

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isOk())
                        .andExpect(content().json(toyJsonString));
            }
        }

        @Nested
        @DisplayName("저장되지 않은 toy의 id를 가지고 있다")
        class Context_with_unsaved_toy_id {
            @BeforeEach
            void setRequest() throws IOException {
                givenId = givenUnsavedId;
                toy = new Toy(
                        givenName + givenUpdatePostfixText,
                        givenBrand + givenUpdatePostfixText,
                        givenPrice + givenUpdatePostfixNumber,
                        givenImageUrl + givenUpdatePostfixText
                );
                toy.setId(givenSavedId);

                outputStream = new ByteArrayOutputStream();
                objectMapper.writeValue(outputStream, toy);
                toyJsonString = outputStream.toString();

                uriTemplate = String.format(stringFormat, givenId);
                requestBuilder = patch(uriTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toyJsonString);

                given(toyService.updateToy(any(Toy.class)))
                        .willThrow(new ToyNotFoundException(givenId));
            }

            @Test
            @DisplayName("404 Not Found를 응답한다.")
            void it_respond_404_not_found() throws Exception {
                mockMvc.perform(requestBuilder)
                        .andExpect(status().isNotFound());
            }
        }
    }
}