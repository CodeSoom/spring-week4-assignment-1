package com.codesoom.assignment.controller;

import com.codesoom.assignment.ToyTestHelper;
import com.codesoom.assignment.domain.CatToyRepository;
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
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CatToyController 클래스의")
public class CatToyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatToyRepository catToyRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        catToyRepository.deleteAll();
    }

    private Map<String, Object> givenInput() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", ToyTestHelper.GIVEN_TOY_NAME);
        input.put("maker", ToyTestHelper.GIVEN_MAKER);
        input.put("price", ToyTestHelper.GIVEN_PRICE);
        input.put("imageUrl", ToyTestHelper.GIVEN_URL);

        return input;
    }

    private Map<String, Object> givenChangeInput() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", ToyTestHelper.CHANGED_TOY_NAME);
        input.put("maker", ToyTestHelper.CHANGED_MAKER);
        input.put("price", ToyTestHelper.CHANGED_PRICE);
        input.put("imageUrl", ToyTestHelper.CHANGED_URL);

        return input;
    }

    private ResultActions createPerform(Object input) throws Exception {
        return mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));
    }

    private Map<String, Object> createAndConvertToMap(Object input) throws Exception {
        return objectMapper.readValue(createPerform(input)
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString(),
                new TypeReference<Map<String, Object>>() {}
        );
    }

    @Nested
    @DisplayName("POST /products 요청은")
    class Describe_create {
        @Nested
        @DisplayName("장난감 정보가 주어지면")
        class Context_with_toyInfo {
            @Test
            @DisplayName("장난감과 상태코드 201을 응답한다")
            void It_returns_catToy_and_statusCreated() throws Exception {
                createPerform(givenInput())
                        .andExpect(jsonPath("$.name").value(ToyTestHelper.GIVEN_TOY_NAME))
                        .andExpect(jsonPath("$.maker").value(ToyTestHelper.GIVEN_MAKER))
                        .andExpect(jsonPath("$.price").value(ToyTestHelper.GIVEN_PRICE))
                        .andExpect(jsonPath("$.imageUrl").value(ToyTestHelper.GIVEN_URL))
                        .andExpect(status().isCreated());
            }
        }
    }

    @Nested
    @DisplayName("GET /products/{id} 요청은")
    class Describe_get {
        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 있다면")
        class Context_with_toyHasId {
            @Test
            @DisplayName("장난감과 상태코드 200을 응답한다")
            void It_returns_catToy_and_statusOk() throws Exception {
                Map<String, Object> response = createAndConvertToMap(givenInput());

                mockMvc.perform(get("/products/" + response.get("id")))
                        .andExpect(jsonPath("$.id").value(response.get("id")))
                        .andExpect(jsonPath("$.name").value(ToyTestHelper.GIVEN_TOY_NAME))
                        .andExpect(jsonPath("$.maker").value(ToyTestHelper.GIVEN_MAKER))
                        .andExpect(jsonPath("$.price").value(ToyTestHelper.GIVEN_PRICE))
                        .andExpect(jsonPath("$.imageUrl").value(ToyTestHelper.GIVEN_URL))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 없다면")
        class Context_without_toyHasId {
            @Test
            @DisplayName("예외 메시지와 상태코드 404를 응답한다")
            void It_throws_exception() throws Exception {
                mockMvc.perform(get("/products/" + ToyTestHelper.IMPOSSIBLE_ID))
                        .andExpect(jsonPath("$.message").isString())
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("GET /products 요청은")
    class Describe_getAll {
        @Nested
        @DisplayName("장난감들이 있다면")
        class Context_with_toyList {
            @Test
            @DisplayName("장난감 목록과 상태코드 200을 응답한다")
            void It_returns_toyList() throws Exception {
                createPerform(givenInput());
                createPerform(givenInput());

                mockMvc.perform(get("/products"))
                        .andExpect(jsonPath("$.[0]").exists())
                        .andExpect(jsonPath("$.[1]").exists())
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("장난감이 없다면")
        class Context_without_toyList {
            @Test
            @DisplayName("빈 장난감 목록과 상태코드 200을 응답한다")
            void It_returns_toyList() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(jsonPath("$").isEmpty())
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /products/{id}")
    class Describe_delete {
        @Nested
        @DisplayName("장난감이 주어지면")
        class Context_with_toy {
            @Test
            @DisplayName("장난감을 제거하고 상태코드 204를 응답한다.")
            void It_returns_NoContent() throws Exception {
                Map<String, Object> toy = createAndConvertToMap(givenInput());

                mockMvc.perform(delete("/products/" + toy.get("id")))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 없으면")
        class Context_without_toyWithId {
            @Test
            @DisplayName("상태코드 404를 응답한다")
            void It_returns_Not_FOUND() throws Exception {
                mockMvc.perform(delete("/products/" + ToyTestHelper.IMPOSSIBLE_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {
        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 주어지면")
        class Context_with_toy {
            @Test
            @DisplayName("장난감 정보를 변경 후 리턴하고 상태코드 200을 응답한다")
            void It_returns_toyAndOk() throws Exception {
                Map<String, Object> toy = createAndConvertToMap(givenChangeInput());

                mockMvc.perform(put("/products/" + toy.get("id"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(givenChangeInput())))
                        .andExpect(jsonPath("$.id").value(toy.get("id")))
                        .andExpect(jsonPath("$.name").value(ToyTestHelper.CHANGED_TOY_NAME))
                        .andExpect(jsonPath("$.maker").value(ToyTestHelper.CHANGED_MAKER))
                        .andExpect(jsonPath("$.price").value(ToyTestHelper.CHANGED_PRICE))
                        .andExpect(jsonPath("$.imageUrl").value(ToyTestHelper.CHANGED_URL))
                        .andExpect(status().isOk());
            }
        }
    }
}
