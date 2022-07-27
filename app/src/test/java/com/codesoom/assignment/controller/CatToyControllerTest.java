package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.exception.ToyExceptionHandler;
import com.codesoom.assignment.service.CatToyService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CatToyController 클래스의")
public class CatToyControllerTest {
    public static final String GIVEN_TOY_NAME = "고양이";
    public static final String GIVEN_MAKER = "허먼밀러";
    public static final Integer GIVEN_PRICE = 90000;
    public static final String GIVEN_URL = "url";

    private MockMvc mockMvc;

    @Autowired
    private CatToyService catToyService;

    @Autowired // 데이터를 초기화하기 위해 임시방편..ㅠ 과연 컨트롤러에서 알 필요없는 로직인 레포지토리를 쓰는게 맞을까하는 고민이 있습니다.
    private CatToyRepository catToyRepository;

    private CatToy givenToy = new CatToy(GIVEN_TOY_NAME, GIVEN_MAKER, GIVEN_PRICE, GIVEN_URL);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CatToyController(catToyService))
                .setControllerAdvice(ToyExceptionHandler.class)
                .build();

        catToyRepository.deleteAll();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, Object> givenInput() {
        Map<String, Object> input = new HashMap<>();
        input.put("name", GIVEN_TOY_NAME);
        input.put("maker", GIVEN_MAKER);
        input.put("price", GIVEN_PRICE);
        input.put("url", GIVEN_URL);

        return input;
    }

    private ResultActions createPerform(Object input) throws Exception {
        return mockMvc.perform(post("/toys")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)));
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
                CatToy toy = catToyRepository.save(givenToy);

                mockMvc.perform(get("/toys/" + toy.getId()))
                        .andDo(print())
                        .andExpect(jsonPath("$.id").value(toy.getId()))
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
                mockMvc.perform(get("/toys/100"))
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
}
