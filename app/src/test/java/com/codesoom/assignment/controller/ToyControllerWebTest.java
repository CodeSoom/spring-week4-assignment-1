package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.dto.ToyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Nested
@DisplayName("ToyController 클래스의")
@SpringBootTest
class ToyControllerWebTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void controllerSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("getList 메소드는")
    class DescribeGetList {

        @Nested
        @DisplayName("장난감에 대한 정보를 요청받으면")
        class ContextWithToyInfo {

            @BeforeEach
            void setUp() throws Exception {
                Toy toy = new Toy.Builder("장난감", "브랜드")
                        .price(1000.0)
                        .build();
                String body = objectMapper.writeValueAsString(toy);
                mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString("장난감")));
            }

            @Test
            @DisplayName("모든 장난감 정보를 반환한다")
            void ItReturnsToyList() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("장난감")))
                        .andExpect(content().string(containsString("브랜드")));
            }
        }
    }

    @Nested
    @DisplayName("getDetail 메소드는")
    class DescribeGetDetail {

        @Nested
        @DisplayName("존재하는 장난감 id를 받으면")
        class ContextWithExistingId {

            private Long id;

            @BeforeEach
            void setUp() throws Exception {
                Toy toy = new Toy.Builder("장난감", "브랜드")
                        .price(1000.0)
                        .build();
                String body = objectMapper.writeValueAsString(toy);
                String content = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString("장난감")))
                        .andReturn().getResponse().getContentAsString();
                this.id = objectMapper.readValue(content, ToyResponse.class).getId();
            }

            @Test
            @DisplayName("장난감의 정보를 반환한다")
            void ItReturnsToyInfo() throws Exception {
                mockMvc.perform(get("/products/" + this.id))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("장난감")))
                        .andExpect(content().string(containsString("브랜드")));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 장난감 id를 받으면")
        class ContextWithNoExistingId {

            @Test
            @DisplayName("NotFound status를 반환한다")
            void ItReturnsToyInfo() throws Exception {
                mockMvc.perform(get("/products/1"))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("createToy 메소드는")
    class DescribeCreateToy {

        @Nested
        @DisplayName("장난감에 대한 정보를 받으면")
        class ContextWithToyInfo {

            private Toy toy;

            @BeforeEach
            void setUp() {
                this.toy = new Toy.Builder("장난감", "브랜드")
                        .price(1000.0)
                        .build();
            }

            @Test
            @DisplayName("정보를 저장하고 반환한다")
            void ItReturnsToy() throws Exception {
                String body = objectMapper.writeValueAsString(this.toy);
                mockMvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString("장난감")));
            }
        }
    }

    @Nested
    @DisplayName("updateToy 메소드는")
    class DescribeUpdateToy {

        @Nested
        @DisplayName("존재하는 장난감의 정보를 받으면")
        class ContextWithExistingToy {

            private Long id;

            @BeforeEach
            void setUp() throws Exception {
                Toy toy = new Toy.Builder("장난감", "브랜드")
                        .price(1000.0)
                        .build();
                String body = objectMapper.writeValueAsString(toy);
                String content = mockMvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString("장난감")))
                        .andReturn().getResponse().getContentAsString();
                this.id = objectMapper.readValue(content, ToyResponse.class).getId();
            }

            @Test
            @DisplayName("장난감의 정보를 수정하고 반환한다")
            void ItReturnsModified() throws Exception {
                Toy modified = new Toy.Builder("고양이 장난감", "브랜드").build();
                String modifiedBody = objectMapper.writeValueAsString(modified);
                mockMvc.perform(patch("/products/" + this.id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(modifiedBody))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("고양이 장난감")));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 장난감에 대한 요청을 받으면")
        class ContextWithInvalidId {

            @Test
            @DisplayName("NotFound status를 반환한다")
            void ItReturnsModified() throws Exception {
                Toy modified = new Toy.Builder("고양이 장난감", "브랜드").build();
                String modifiedBody = objectMapper.writeValueAsString(modified);
                mockMvc.perform(patch("/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(modifiedBody))
                        .andExpect(status().isNotFound());
            }

        }
    }

    @Nested
    @DisplayName("deleteToy 메소드는")
    class DescribeDeleteToy {

        @Nested
        @DisplayName("존재하는 장난감 id를 받으면")
        class ContextWithExistingId {

            private Long id;

            @BeforeEach
            void setUp() throws Exception {
                Toy toy = new Toy.Builder("장난감", "브랜드")
                        .price(1000.0)
                        .build();
                String body = objectMapper.writeValueAsString(toy);
                String content = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                        .andExpect(status().isCreated())
                        .andExpect(content().string(containsString("장난감")))
                        .andReturn().getResponse().getContentAsString();
                this.id = objectMapper.readValue(content, ToyResponse.class).getId();
            }

            @Test
            @DisplayName("NoContent status를 반환한다")
            void ItReturnsNoContent() throws Exception {
                mockMvc.perform(delete("/products/" + this.id))
                        .andExpect(status().isNoContent());
            }
        }

        @Nested
        @DisplayName("존재하지 않는 장난감 id를 받으면")
        class ContextWithNoExsitingId {

            @Test
            @DisplayName("NotFound status를 반환한다")
            void ItReturnsNotFound() throws Exception {
                mockMvc.perform(delete("/products/1"))
                        .andExpect(status().isNotFound());
            }
        }
    }
}
