package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.CatToyDTO;
import com.codesoom.assignment.exceptions.ToyNotFoundException;
import com.codesoom.assignment.models.CatToy;
import com.codesoom.assignment.models.Toy;
import com.codesoom.assignment.services.ToyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DisplayName("ProductsController 테스트")
class ProductsControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToyService catToyService;

    private ProductsController productsController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Toy givenToy1 = new CatToy(0L, "cat nip", "cat company. co", 1000D, "https://cat.toy/cat-nip.png");
    private final Toy givenToy2 = new CatToy(1L, "cat tower", "cat company. co", 10000D, "https://cat.toy/cat-tower.png");

    private final CatToyDTO givenToyDTO1 = new CatToyDTO(givenToy1);
    private final CatToyDTO givenToyDTO2 = new CatToyDTO(givenToy2);

    @Nested
    @DisplayName("[GET] /products 요청은")
    class Describe_getAllProducts {
        private final List<Toy> givenToys = new ArrayList<>(Arrays.asList(givenToy1, givenToy2));
        private final List<CatToyDTO> givenToyDTOs = new ArrayList<>(Arrays.asList(givenToyDTO1, givenToyDTO2));

        @BeforeEach
        void setup() {
            given(catToyService.find())
                    .willReturn(givenToys);
        }

        @Test
        @DisplayName("모든 장난감들을 리턴한다.")
        void It_returns_all_toys() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(objectMapper.writeValueAsString(givenToyDTOs)));
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("주어진 id의 장난감을 찾을 수 없을 때")
        class Context_when_not_exist_toy_id {
            @Test
            @DisplayName("장난감이 없다는 예외를 던진다.")
            void It_throws_toy_not_found_exception() {
                assertThatThrownBy(() -> productsController.getProduct(givenToy1.id()))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("주어진 id의 장난감이 있을 때")
        class Context_when_exist_toy_id {
            @BeforeEach
            void setup() {
                catToyService.insert(givenToy1);
            }

            @Test
            @DisplayName("장난감을 리턴한다.")
            void It_returns_toy() {
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_createProduct {
        @Test
        @DisplayName("주어진 장난감을 저장한다.")
        void It_save_given_toy() {
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_updateProduct {
        private final Toy modifiedToy1 = new CatToy(givenToy1.id(), "mattattabi stick", givenToy1.brand(), 2000D, "https://cat.toy/maddaddabi-stick.png");

        @Nested
        @DisplayName("주어진 id의 장난감을 찾을 수 없을 때")
        class Context_when_not_exist_toy_id {
            @Test
            @DisplayName("장난감이 없다는 예외를 던진다.")
            void It_throws_toy_not_found_exception() {
            }
        }

        @Nested
        @DisplayName("주어진 id의 장난감이 있을 때")
        class Context_when_exist_toy_id {
            @BeforeEach
            void setup() {
                catToyService.insert(givenToy1);
            }

            @Test
            @DisplayName("장난감의 정보를 변경한다.")
            void It_returns_toy() {
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("주어진 id의 장난감을 찾을 수 없을 때")
        class Context_when_not_exist_toy_id {
            @Test
            @DisplayName("장난감이 없다는 예외를 던진다.")
            void It_throws_toy_not_found_exception() {
            }
        }

        @Nested
        @DisplayName("주어진 id의 장난감이 있을 때")
        class Context_when_exist_toy_id {
            @BeforeEach
            void setup() {
                catToyService.insert(givenToy1);
            }

            @Test
            @DisplayName("주어진 id의 장난감을 삭제한다.")
            void It_delete_given_id_toy() {
            }
        }
    }
}
