package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CatToyController 클래스의")
public class CatToyControllerTest {
    public static final String CHANGED = "변경된 ";
    public static final Integer CHANGED_PRICE = 100;
    public static final String GIVEN_TOY_NAME = "고양이";
    public static final String GIVEN_MAKER = "허먼밀러";
    public static final Integer GIVEN_PRICE = 90000;
    public static final String GIVEN_URL = "url";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatToyRepository catToyRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        catToyRepository.deleteAll();
    }

    private final CatToy givenToy = new CatToy(GIVEN_TOY_NAME, GIVEN_MAKER, GIVEN_PRICE, GIVEN_URL);

    private Map<String, Object> givenInput() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", GIVEN_TOY_NAME);
        input.put("maker", GIVEN_MAKER);
        input.put("price", GIVEN_PRICE);
        input.put("url", GIVEN_URL);

        return input;
    }

    private Map<String, Object> givenChangeInput() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", CHANGED + GIVEN_TOY_NAME);
        input.put("maker", CHANGED + GIVEN_MAKER);
        input.put("price", CHANGED_PRICE);
        input.put("url", CHANGED + GIVEN_URL);

        return input;
    }

    private ResultActions createPerform(Object input) throws Exception {
        return mockMvc.perform(post("/toys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));
    }

    private Map<String, Object> createAndConvertToMap(Object input) throws Exception {
        return objectMapper.readValue(
                createPerform(input)
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                new TypeReference<Map<String, Object>>() {}
        );
    }

    @Nested
    @DisplayName("POST /toys 요청은")
    class Describe_create {
        @Nested
        @DisplayName("장난감이 주어지면")
        class Context_with_catToy {
            @Test
            @DisplayName("장난감과 상태코드 201을 응답한다")
            void It_returns_catToy_and_statusCreated() throws Exception {
                createPerform(givenInput())
                        .andExpect(jsonPath("$.name").value(GIVEN_TOY_NAME))
                        .andExpect(jsonPath("$.maker").value(GIVEN_MAKER))
                        .andExpect(jsonPath("$.price").value(GIVEN_PRICE))
                        .andExpect(jsonPath("$.url").value(GIVEN_URL))
                        .andExpect(status().isCreated());
            }
        }
    }

    @Nested
    @DisplayName("GET /toys/{id} 요청은")
    class Describe_get {
        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 있다면")
        class Context_with_toyHasId {
            @Test
            @DisplayName("장난감과 상태코드 200을 응답한다")
            void It_returns_catToy_and_statusOk() throws Exception {
                Map<String, Object> response = createAndConvertToMap(givenInput());

                mockMvc.perform(get("/toys/" + response.get("id")))
                        .andExpect(jsonPath("$.id").value(response.get("id")))
                        .andExpect(jsonPath("$.name").value(GIVEN_TOY_NAME))
                        .andExpect(jsonPath("$.maker").value(GIVEN_MAKER))
                        .andExpect(jsonPath("$.price").value(GIVEN_PRICE))
                        .andExpect(jsonPath("$.url").value(GIVEN_URL))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("주어진 식별자를 가진 장난감이 없다면")
        class Context_without_toyHasId {
            @Test
            @DisplayName("예외 메시지와 상태코드 404를 응답한다")
            void It_throws_exception() throws Exception {
                mockMvc.perform(get("/toys/" + Long.MIN_VALUE))
                        .andExpect(jsonPath("$.message").isString())
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("GET /toys 요청은")
    class Describe_getAll {
        @Nested
        @DisplayName("장난감들이 있다면")
        class Context_with_toyList {
            @Test
            @DisplayName("장난감 목록과 상태코드 200을 응답한다")
            void It_returns_toyList() throws Exception {
                createPerform(givenInput());
                createPerform(givenInput());

                mockMvc.perform(get("/toys"))
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
                mockMvc.perform(get("/toys"))
                        .andExpect(jsonPath("$").isEmpty())
                        .andExpect(status().isOk());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /toys/{id}")
    class Describe_delete {
        @Nested
        @DisplayName("장난감이 주어지면")
        class Context_with_toy {
            @Test
            @DisplayName("장난감을 제거하고 상태코드 204를 응답한다.")
            void It_returns_NoContent() throws Exception {
                Map<String, Object> toy = createAndConvertToMap(givenInput());

                mockMvc.perform(delete("/toys/" + toy.get("id")))
                        .andExpect(status().isNoContent());
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
                Map<String, Object> toy = createAndConvertToMap(givenToy);

                mockMvc.perform(put("/toys/" + toy.get("id"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(givenChangeInput())))
                        .andExpect(jsonPath("$.id").value(toy.get("id")))
                        .andExpect(jsonPath("$.name").value(CHANGED + GIVEN_TOY_NAME))
                        .andExpect(jsonPath("$.maker").value(CHANGED + GIVEN_MAKER))
                        .andExpect(jsonPath("$.price").value(CHANGED_PRICE))
                        .andExpect(jsonPath("$.url").value(CHANGED + GIVEN_URL))
                        .andExpect(status().isOk());
            }
        }
    }
}
