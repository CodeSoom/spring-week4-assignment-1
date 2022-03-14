package com.codesoom.assignment.controller;


import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("CatToyContorller Web Test")
public class CatToyControllerWebTest {
    private static final String CAT_TOY_NAME = "test_cat_toy";
    private static final String CAT_TOY_MAKER = "test_maker";
    private static final String CAT_TOY_IMAGE = "http://test.jpg";
    private static final Integer CAT_TOY_PRICE = 10000;

    private static final String NEW_CAT_TOY_NAME = "new_test_cat_toy";
    private static final String NEW_CAT_TOY_MAKER = "new_test_maker";
    private static final String NEW_CAT_TOY_IMAGE = "http://new_test.jpg";
    private static final Integer NEW_CAT_TOY_PRICE = 20000;

    private static final Long NOT_EXISTED_ID = 0L;

    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CatToyRepository catToyRepository;

    private CatToy existedCatToy;
    private String requestBody;

    @BeforeEach
    void prepareRepository() {
        catToyRepository.deleteAll();
    }

    void prepareExistedCatToy() {
        CatToy catToy = new CatToy();
        catToy.setName(CAT_TOY_NAME);
        catToy.setMaker(CAT_TOY_MAKER);
        catToy.setImageUrl(CAT_TOY_IMAGE);
        catToy.setPrice(CAT_TOY_PRICE);

        existedCatToy = catToyRepository.save(catToy);
    }

    void prepareNewCatToy() throws JsonProcessingException {
        CatToy catToy = new CatToy();
        catToy.setName(NEW_CAT_TOY_NAME);
        catToy.setMaker(NEW_CAT_TOY_MAKER);
        catToy.setImageUrl(NEW_CAT_TOY_IMAGE);
        catToy.setPrice(NEW_CAT_TOY_PRICE);

        requestBody = objectMapper.writeValueAsString(catToy);
    }

    @DisplayName("GET /products")
    @Nested
    class Describe_get_products {
        @DisplayName("등록된 CatToy가 있다면")
        @Nested
        class Context_with_existed_cat_toy {
            @BeforeEach
            void prepare() {
                prepareExistedCatToy();
            }

            @DisplayName("등록된 모든 CatToy 리스트와 OK를 응답한다.")
            @Test
            void it_returns_cat_toy_list() throws Exception {
                mockmvc.perform(get("/products"))
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString(existedCatToy.getName())))
                        .andExpect(content().string(containsString(existedCatToy.getImageUrl())))
                        .andExpect(content().string(containsString(existedCatToy.getMaker())))
                        .andExpect(content().string(containsString(existedCatToy.getPrice().toString())))
                        .andExpect(status().isOk());
            }
        }

        @DisplayName("등록된 CatToy가 없다면")
        @Nested
        class Context_without_existed_cat_toy {
            @BeforeEach
            void prepare() {
                catToyRepository.deleteAll();
            }

            @DisplayName("비어있는 리스트와 OK를 응답한다.")
            @Test
            void it_returns_empty_list() throws Exception {
                mockmvc.perform(get("/products"))
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString("[]")))
                        .andExpect(status().isOk());
            }
        }
    }

    @DisplayName("GET /products/{id}")
    @Nested
    class Describe_get_products_id {
        @DisplayName("등록된 CatToy id가 주어진다면")
        @Nested
        class Context_with_existed_cat_toy {
            @BeforeEach
            void prepare() {
                prepareExistedCatToy();
            }

            @DisplayName("해당 id의 CatToy와 Ok를 응답한다")
            @Test
            void it_returns_cat_toy() throws Exception {
                mockmvc.perform(get("/products/" + existedCatToy.getId()))
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString(CAT_TOY_NAME)))
                        .andExpect(status().isOk());
            }
        }

        @DisplayName("등록되지않은 CatToy id가 주어진다면")
        @Nested
        class Context_without_existed_cat_toy {
            @BeforeEach
            void prepare() {
                prepareExistedCatToy();
            }

            @DisplayName("에러메시지와 Not Found를 응답한다.")
            @Test
            void it_returns_not_found() throws Exception {
                mockmvc.perform(get("/products/" + NOT_EXISTED_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @DisplayName("DELETE /products/{id}")
    @Nested
    class Describe_delete_products_id {
        @DisplayName("등록된 CatToy id가 주어진다면")
        @Nested
        class Context_with_existed_cat_toy {
            @BeforeEach
            void prepare() {
                prepareExistedCatToy();
            }

            @DisplayName("해당 id의 CatToy를 삭제하고, No Content를 응답한다.")
            @Test
            void it_returns_no_content() throws Exception {
                mockmvc.perform(delete("/products/" + existedCatToy.getId()))
                        .andExpect(status().isNoContent());

                assertThat(catToyRepository.findById(existedCatToy.getId()).orElse(null)).isNull();
            }
        }

        @DisplayName("등록되지않은 CatToy id가 주어진다면")
        @Nested
        class Context_without_existed_cat_toy {
            @DisplayName("에러메시지와 Not Found를 응답한다.")
            @Test
            void it_returns_not_found() throws Exception {
                mockmvc.perform(delete("/products/" + NOT_EXISTED_ID))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @DisplayName("POST /products/{id}")
    @Nested
    class Describe_post_products_id {
        @DisplayName("CatToy가 주어진다면")
        @Nested
        class Context_with_new_cat_toy {
            @BeforeEach
            void prepare() throws JsonProcessingException {
                prepareNewCatToy();
            }

            @DisplayName("동일한 CatToy를 생성하고, 생성한 CatToy와 Created를 응답한다.")
            @Test
            void it_returns_created_cat_toy() throws Exception {
                mockmvc.perform(post("/products")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString(NEW_CAT_TOY_NAME)))
                        .andExpect(status().isCreated());
            }
        }
    }

    @DisplayName("PATCH /products/{id}")
    @Nested
    class Describe_patch_products_id {
        @BeforeEach
        void prepare() throws JsonProcessingException {
            prepareExistedCatToy();
            prepareNewCatToy();
        }

        @DisplayName("등록된 CatToy id와 CatToy가 주어진다면")
        @Nested
        class Context_with_existed_cat_toy {
            @DisplayName("해당 id의 CatToy를 주어진 CatToy와 동일하게 변경하고 리턴한다.")
            @Test
            void it_returns_created_cat_toy() throws Exception {
                mockmvc.perform(patch("/products/" + existedCatToy.getId())
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(containsString(NEW_CAT_TOY_NAME)))
                        .andExpect(status().isOk());
            }
        }

        @DisplayName("등록되지않은 CatToy id가 주어진다면")
        @Nested
        class Context_without_existed_cat_toy {
            @DisplayName("에러메시지와 Not Found를 응답한다.")
            @Test
            void it_returns_not_found() throws Exception {
                mockmvc.perform(patch("/products/" + NOT_EXISTED_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            }
        }
    }
}


