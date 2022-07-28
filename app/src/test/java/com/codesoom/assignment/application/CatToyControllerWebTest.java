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

import static org.assertj.core.api.Assertions.assertThat;
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
            @BeforeEach
            void prepare() {
                repository.save(new CatToy(FIXTURE_NAME + 1, FIXTURE_MAKER + 1, FIXTURE_PRICE + 1));
                repository.save(new CatToy(FIXTURE_NAME + 2, FIXTURE_MAKER + 2, FIXTURE_PRICE + 2));
            }

            @Test
            @DisplayName("OK status, 저장된 고양이 장난감 목록을 반환한다")
            void it_returnsOkStatusAndEmptyList() throws Exception {
                final List<CatToy> expectedToys = List.of(
                    new CatToy(1L, FIXTURE_NAME + 1, FIXTURE_MAKER + 1, FIXTURE_PRICE + 1),
                    new CatToy(2L, FIXTURE_NAME + 2, FIXTURE_MAKER + 2, FIXTURE_PRICE + 2)
                );
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
