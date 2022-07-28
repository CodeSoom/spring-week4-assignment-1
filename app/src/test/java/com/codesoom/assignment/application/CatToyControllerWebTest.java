package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.infra.InMemoryCatToyRepository;
import com.codesoom.assignment.service.CatToyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("CatToyController Web")
public class CatToyControllerWebTest {
    private final String FIXTURE_NAME = "name";
    private final String FIXTURE_MAKER = "maker";
    private final int FIXTURE_PRICE = 10000;

    private CatToyRepository repository;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        repository = new InMemoryCatToyRepository();
        final CatToyService service = new CatToyService(repository);
        final CatToyController controller = new CatToyController(service);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Nested
    @DisplayName("GET /products 요청은")
    class Describe_getProducts {
        @Nested
        @DisplayName("저장된 고양이 장난감이 없을 때")
        class Context_didNotSaveCatToy {
            @BeforeEach
            void prepare() {
                repository.deleteAll();
            }
        }

        @Test
        @DisplayName("OK status, 빈 목록을 반환한다")
        void it_returnsOkStatusAndEmptyList() throws Exception {
            final List<CatToy> expectedToys = List.of();
            final String expectedContent = writeValueAsString(expectedToys);

            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(expectedContent));
        }

        @Nested
        @DisplayName("저장된 고양이 장난감이 있을 때")
        class Context_didSaveCatToy {
            final int NUMBER_OF_TOY_LIST = 3;

            @BeforeEach
            void prepare() {
                IntStream.rangeClosed(1, NUMBER_OF_TOY_LIST)
                        .mapToObj(value -> {
                            return new CatToy(FIXTURE_NAME + value, FIXTURE_MAKER + value, FIXTURE_PRICE + value);
                        })
                        .forEach(catToy -> {
                            repository.save(catToy);
                        });
            }

            @Test
            @DisplayName("OK status, 저장된 고양이 장난감 목록을 반환한다")
            void it_returnsOkStatusAndEmptyList() throws Exception {
                List<CatToy> expectedToys = IntStream.rangeClosed(1, NUMBER_OF_TOY_LIST)
                    .mapToObj(value -> {
                        return new CatToy((long) value, FIXTURE_NAME + value, FIXTURE_MAKER + value, FIXTURE_PRICE + value);
                    })
                    .collect(Collectors.toList());

                final String expectedContent = writeValueAsString(expectedToys);

                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(expectedContent));
            }
        }
    }

    private String writeValueAsString(List<CatToy> catToys) throws JsonProcessingException {
        return objectMapper.writeValueAsString(catToys);
    }
}
