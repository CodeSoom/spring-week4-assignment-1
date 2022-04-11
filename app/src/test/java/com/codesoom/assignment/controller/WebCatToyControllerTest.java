package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.CatToyService;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.dto.CatToySaveDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CatToyController.class)
@AutoConfigureMockMvc
@DisplayName("고양이 장난감에 대한 HTTP 요청")
public class WebCatToyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CatToyService catToyService;

    @Nested
    @DisplayName("GET - /products 요청시")
    class Describe_list {

        @Nested
        @DisplayName("고양이 장난감 상품 수 만큼")
        class Context_givenCount {

            final int givenCount = 10;

            @BeforeEach
            void setUp() {
                List<CatToy> catToys = LongStream.rangeClosed(1, givenCount)
                        .mapToObj(CatToy::new)
                        .collect(Collectors.toList());

                given(catToyService.getCatToys()).willReturn(catToys);
            }

            @Test
            @DisplayName("고양이 장난감 상품을 응답한다. [200]")
            void it_response_products_and_http_status_200() throws Exception {

                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(givenCount)));
            }
        }
    }

    @Nested
    @DisplayName("POST - /products 요청시")
    class Describe_save {

        @Nested
        @DisplayName("고양이 장난감 등록에 필요한 데이터")
        class Context_valid {

            @BeforeEach
            void setUp() {
                CatToy catToy = new CatToy(1L, "aaa", 1000, "/images/test.jpg");
                given(catToyService.saveCatToy(any(CatToySaveDto.class))).willReturn(catToy);
            }

            @Test
            @DisplayName("고양이 장난감을 등록하고 리턴한다.")
            void it_save_and_return_catToy() throws Exception {

                mockMvc.perform(post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{maker: \"maker\", price: 1000, \"/image/test.jpg\"}"))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("id").exists())
                        .andExpect(jsonPath("maker").exists())
                        .andExpect(jsonPath("price").exists())
                        .andExpect(jsonPath("imagePath").exists());
            }
        }
    }
}
