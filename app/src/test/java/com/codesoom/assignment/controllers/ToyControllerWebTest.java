package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ToyNotFoundException;
import com.codesoom.assignment.application.ToyService;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.fixture.Given;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("ToyController 클래스가 받은 요청 중")
class ToyControllerWebTest {
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

    private Given given = new Given();

    @BeforeEach
    void setUp() throws IOException {
        toy = given.newToy();

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
                toyList = List.of(toy);

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
    @DisplayName("GET /products/{id} 요청은")
    class Describe_get_products_id_request {
        private String stringFormat = "/products/%d";
        private Long givenId;

        @Nested
        @DisplayName("저장된 toy의 id라면")
        class Context_with_saved_toy_id {
            @BeforeEach
            void setRequest() {
                givenId = given.savedId;
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
                givenId = given.unsavedId;
                uriTemplate = String.format(stringFormat, givenId);
                requestBuilder = get(uriTemplate);
            }

            @Test
            @DisplayName("404 Not Found를 응답한다.")
            void it_respond_404_not_found() throws Exception {
                given(toyService.getToy(givenId))
                        .willThrow(new ToyNotFoundException(givenId));

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("POST /products 요청은")
    class Describe_post_products_request {
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
    class Describe_patch_products_id_request {
        private String stringFormat = "/products/%d";
        private Long givenId;

        @Nested
        @DisplayName("저장된 toy의 id를 가지고 있다면")
        class Context_with_saved_toy_id {
            @BeforeEach
            void setRequest() throws IOException {
                givenId = given.savedId;

                toy = given.modifiedToy(givenId);

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
        @DisplayName("저장되지 않은 toy의 id를 가지고 있다면")
        class Context_with_unsaved_toy_id {
            @BeforeEach
            void setRequest() throws IOException {
                givenId = given.unsavedId;
                toy = given.modifiedToy(givenId);

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

    @Nested
    @DisplayName("DELETE /products/:id 요청은")
    class Context_delete_products_id_request {
        private String stringFormat = "/products/%d";
        private Long givenId;

        @Nested
        @DisplayName("저장된 toy의 id를 가지고 있다면")
        class Context_with_saved_toy_id {
            @BeforeEach
            void setRequest() {
                givenId = given.savedId;

                uriTemplate = String.format(stringFormat, givenId);
                requestBuilder = delete(uriTemplate);
            }

            @Test
            @DisplayName("204 No Content를 응답한다.")
            void it_respond_204_no_content() throws Exception {
                verify(toyService).deleteToy(any(Long.class));

                mockMvc.perform(requestBuilder)
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("저장되지 않은 toy의 id를 가지고 있다면")
        class Context_with_unsaved_toy_id {
            @BeforeEach
            void setRequest() {
                givenId = given.unsavedId;

                uriTemplate = String.format(stringFormat, givenId);
                requestBuilder = delete(uriTemplate);

                doThrow(ToyNotFoundException.class).when(toyService).deleteToy(givenId);
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
