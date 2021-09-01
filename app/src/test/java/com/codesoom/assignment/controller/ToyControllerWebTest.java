package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Toy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ToyControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    private Toy toyFixture;
    private Toy newToyFixture;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final static Long EXISTENT_ID = 1L;
    private final static Long NON_EXISTENT_ID = 1000000L;

    @BeforeEach
    void setupFixtures() {
        toyFixture = new Toy(
                EXISTENT_ID,
                "cat",
                "new balance",
                12345,
                "https://source.unsplash.com/random"
        );

        newToyFixture = new Toy(
                2L,
                "dog",
                "adidas",
                124212342,
                "https://source.unsplash.com/random"
        );
    }

    private <T> T getResponseContent(ResultActions actions, TypeReference<T> type)
            throws UnsupportedEncodingException, JsonProcessingException {
        MvcResult mvcResult = actions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        return objectMapper.readValue(contentAsString, type);
    }

    private Toy addToyForSetup() throws Exception {
        ResultActions actions = mockMvc.perform(post("/products")
                .content(objectMapper.writeValueAsString(toyFixture))
                .contentType(MediaType.APPLICATION_JSON));

        Toy toyInService = getResponseContent(actions, new TypeReference<Toy>() {});

        return toyInService;
    }

    private List<Toy> getCreatedToyList() throws Exception {
        return getResponseContent(
                mockMvc.perform(get("/products")),
                new TypeReference<List<Toy>>() {}
        );
    }

    @Nested
    @DisplayName("Post Request")
    class PostRequest {
        @Test
        @DisplayName("returns a created toy with 201 HTTP status code")
        void returnsCreatedToy() throws Exception {
            mockMvc.perform(post("/products")
                            .content(objectMapper.writeValueAsString(toyFixture))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().string(containsString(toyFixture.getName())))
                    .andExpect(content().string(containsString(toyFixture.getMaker())));
        }

        @Test
        @DisplayName("increases the number of toys by one")
        void increasesNumberOfToy() throws Exception {
            List<Toy> toysBeforePost = getCreatedToyList();

            mockMvc.perform(post("/products")
                    .content(objectMapper.writeValueAsString(toyFixture))
                    .contentType(MediaType.APPLICATION_JSON));

            List<Toy> toysAfterPost = getCreatedToyList();

            assertThat(toysAfterPost.size()).isEqualTo(toysBeforePost.size() + 1);
        }
    }

    @Nested
    @DisplayName("Get Request")
    class GetRequest {
        private Toy toyInService;
        private String toyContent;

        @BeforeEach
        void setup() throws Exception {
            toyInService = addToyForSetup();
            toyContent = objectMapper.writeValueAsString(toyInService);
        }

        @Nested
        @DisplayName("Without path parameter")
        class WithoutPathParameter {
            @Test
            @DisplayName("returns toys with HTTP status code 200")
            void returnsToys() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(toyContent)));
            }
        }

        @Nested
        @DisplayName("With an existent id")
        class WithExistentId {
            @Test
            @DisplayName("returns a toy with HTTP status code 200")
            void returnsToy() throws Exception {
                mockMvc.perform(get("/products/" + toyInService.getId()))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(toyContent)));
            }
        }

        @Nested
        @DisplayName("With a non existent id")
        class WithNonExistentId {
            @Test
            @DisplayName("returns an error with HTTP status code 404")
            void returnsError() throws Exception {
                mockMvc.perform(get("/products/" + NON_EXISTENT_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("Put Request")
    class PutRequest {
        private Toy toyInService;

        @BeforeEach
        void setup() throws Exception {
            toyInService = addToyForSetup();
        }

        @Nested
        @DisplayName("With an existent id")
        class WithExistentId {
            @Test
            @DisplayName("returns an updated toy with HTTP status code 200")
            void returnsUpdatedToy() throws Exception {
                ResultActions actions = mockMvc.perform(put("/products/"+ toyInService.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newToyFixture)));

                Toy result = getResponseContent(actions, new TypeReference<Toy>() {});

                assertThat(result.getId()).isEqualTo(toyInService.getId());
                assertThat(result.getName()).isEqualTo(newToyFixture.getName());
                assertThat(result.getPrice()).isEqualTo(newToyFixture.getPrice());
                assertThat(result.getImageUrl()).isEqualTo(newToyFixture.getImageUrl());
                assertThat(result.getMaker()).isEqualTo(newToyFixture.getMaker());
            }
        }

        @Nested
        @DisplayName("With a non existent id")
        class WithNonExistentId {
            @Test
            @DisplayName("returns an error with HTTP status code 404")
            void returnsError() throws Exception {
                mockMvc.perform(put("/products/" + NON_EXISTENT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newToyFixture)))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("Patch Request")
    class PatchRequest {
        private Toy toyInService;

        @BeforeEach
        void setup() throws Exception {
            toyInService = addToyForSetup();
        }

        @Nested
        @DisplayName("With an existent id")
        class WithExistentId {
            @Test
            @DisplayName("returns an updated toy with HTTP status code 200")
            void returnsUpdatedToy() throws Exception {
                ResultActions actions = mockMvc.perform(patch("/products/"+ toyInService.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newToyFixture)));

                Toy result = getResponseContent(actions, new TypeReference<Toy>() {});

                assertThat(result.getId()).isEqualTo(toyInService.getId());
                assertThat(result.getName()).isEqualTo(newToyFixture.getName());
                assertThat(result.getPrice()).isEqualTo(newToyFixture.getPrice());
                assertThat(result.getImageUrl()).isEqualTo(newToyFixture.getImageUrl());
                assertThat(result.getMaker()).isEqualTo(newToyFixture.getMaker());
            }
        }

        @Nested
        @DisplayName("With a non existent id")
        class WithNonExistentId {
            @Test
            @DisplayName("returns an error with HTTP status code 404")
            void returnsError() throws Exception {
                mockMvc.perform(patch("/products/" + NON_EXISTENT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newToyFixture)))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("Delete Request")
    class DeleteRequest {
        private Toy toyInService;

        @Nested
        @DisplayName("With an existent id")
        class WithExistentId {
            @BeforeEach
            void setup() throws Exception {
                toyInService = addToyForSetup();
            }

            @Test
            @DisplayName("returns nothing with HTTP status code 200")
            void returnsUpdatedToy() throws Exception {
                mockMvc.perform(delete("/products/"+ toyInService.getId()))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("With a non existent id")
        class WithNonExistentId {
            @Test
            @DisplayName("returns an error with HTTP status code 404")
            void returnsError() throws Exception {
                mockMvc.perform(delete("/products/" + NON_EXISTENT_ID))
                       .andExpect(status().isNotFound());
            }
        }
    }
}
