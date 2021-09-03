package com.codesoom.assignment.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(ProductController.class)
@WebAppConfiguration
@DisplayName("CatProductController 클래스")
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductService productService;
  private final Long CREATED_ID = 2L;


  private final String CREATED_PRODUCT_NAME = "createdName";
  private final String CREATED_PRODUCT_MAKER = "createdMaker";
  private final int CREATED_PRODUCT_PRICE = 200;
  private final String CREATED_PRODUCT_IMAGEURL = "createdImage";

  private List<Product> products;
  private Product product;

  @Nested
  @DisplayName("GET /products 요청은")
  class Describe_get_request {
    private final Product mockProduct = Product.builder()
        .id(CREATED_ID)
        .name(CREATED_PRODUCT_NAME)
        .maker(CREATED_PRODUCT_MAKER)
        .price(CREATED_PRODUCT_PRICE)
        .imgUrl(CREATED_PRODUCT_IMAGEURL)
        .build();

    Product product = new Product(CREATED_ID, CREATED_PRODUCT_NAME, CREATED_PRODUCT_MAKER, CREATED_PRODUCT_PRICE, CREATED_PRODUCT_IMAGEURL);


    @Nested
    @DisplayName("cat상품목록에 데이터가 있으면")
    class Context_with_products {

      @BeforeEach
      void setUp() {
        products = new ArrayList<>();
        products.add(product);
        System.out.println(products);
      }

      @Test
      @DisplayName("200 코드와 저장된 cat상품목록을 응답한다.")
      void it_responds_200_and_all_products() throws Exception {
        given(productService.getProducts()).willReturn(products);
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(content().string(objectMapper.writeValueAsString(products)));
      }
    }

    @Nested
    @DisplayName("cat상품목록에 데이터가 없으면")
    class Context_with_no_product {

      @BeforeEach
      void setUp() {
        products = new ArrayList<>();
        given(productService.getProducts()).willReturn(products);
      }

      @Test
      @DisplayName("200 코드와 빈 list를 리턴한다.")
      void it_responds_200_and_empty_array() throws Exception {
        mockMvc.perform(get("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("[]"));
      }
    }
  }
}
