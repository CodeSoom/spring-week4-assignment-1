package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.service.CatToyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CatToyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatToyService catToyService;

    private static final long ID = 1L;
    private static final String NAME = "TEST NAME";
    private static final String MAKER = "TEST MAKER";
    private static final int PRICE = 10000;
    private static final String IMAGE_URL = "TEST IMAGE URL";

    @BeforeEach
    public void setUp() {
        CatToy catToy = new CatToy(ID, NAME, MAKER, PRICE, IMAGE_URL);
        List<CatToy> catToyList = new ArrayList<>();
        catToyList.add(catToy);

        given(catToyService.getCatToys()).willReturn(catToyList);
        given(catToyService.findCatToyById(ID)).willReturn(catToy);
    }

    @Nested
    @DisplayName("getCatToys 메서드는")
    class getAllCatToys {
        @Test
        @DisplayName("고양이 장난감 목록 전체를 반환한다.")
        void getCatToys() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(NAME)));
        }
    }

    @Nested
    @DisplayName("findCatTiyById 메서드는")
    class findCatToyById {
        @Test
        @DisplayName("식별자에 해당하는 장난감을 반환한다.")
        void findCatToyById() throws Exception {
            mockMvc.perform(get("/products/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString(NAME)));
            verify(catToyService).findCatToyById(1L);
        }
    }

    @Nested
    @DisplayName("registerCatToy 메서드는")
    class registerCatToy {
        @Test
        void registerCatToy() throws Exception {
            mockMvc.perform(
                    post("/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"New name\"}")
            ).andExpect(status().isCreated());
            verify(catToyService).addCatToy(any(CatToy.class));
        }
    }

    @Nested
    @DisplayName("updateCatToy 메서드는")
    class updateCatToy {
        @Test
        void updateCatToy() throws Exception {
            mockMvc.perform(
                    patch("/products/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"New name\"}")
            ).andExpect(status().isOk());
            verify(catToyService).updateCatToy(eq(1L), any(CatToy.class));
        }
    }

    @Nested
    @DisplayName("deleteCatToy 메서드는")
    class deleteCatToy {
        @Test
        void deleteCatToy() throws Exception {
            mockMvc.perform(delete("/products/1"))
                    .andExpect(status().isNoContent());
            verify(catToyService).deleteCatToyById(1L);
        }
    }
}
