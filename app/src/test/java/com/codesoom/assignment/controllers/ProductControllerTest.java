package com.codesoom.assignment.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
@DisplayName("CatProductController 클래스")
class ProductControllerTest {


  @Nested
  @DisplayName("GET /products 요청은")
  class Describe_get_request {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private List<Product> products;
    private Product product;

    @Nested
    @DisplayName("cat상품목록에 데이터가 있으면")
    class Context_with_products {

      @BeforeEach
      void setUp() {
        products.add(product);
        given(productService.getCatProducts())
            .willReturn(products);
      }

      @Test
      @DisplayName("200 코드와 저장된 cat상품목록을 응답한다.")
      void it_responds_200_and_all_products() throws Exception {
        mockMvc.perform(get("/cat-products"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(products)));
      }
    }

    @Nested
    @DisplayName("cat상품목록에 데이터가 없으면")
    class Context_with_no_product {
      @BeforeEach
      void setUp() {
        given(productService.getCatProducts()).willReturn(products);
      }

      @Test
      @DisplayName("200 코드와 빈 list를 리턴한다.")
      void it_responds_200_and_empty_array() throws Exception {
        mockMvc.perform(get("/cat-products")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("[]"));
      }
    }
  }
}
