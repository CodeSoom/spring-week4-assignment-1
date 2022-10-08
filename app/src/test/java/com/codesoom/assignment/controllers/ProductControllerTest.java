package com.codesoom.assignment.controllers;

import com.codesoom.assignment.TestService.ProductDeleteService;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.entity.Product;
import com.codesoom.assignment.repository.ProductRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

  @Autowired
  MockMvc mockMvc;

  private ProductController controller;
  private ProductService service;
  private ProductDeleteService productDeleteService;

  @Autowired
  private ProductRepository repository;

  private Product createProduct1;
  private Product createProduct2;

  private final Long testId = 1L;
  private final String testBrand = "테스트";
  private final Integer testPrice = 1000;

  private String content;


  @BeforeEach
  void setUp() throws Exception {
    productDeleteService = new ProductDeleteService(repository);
    service = new ProductService(repository);
    controller = new ProductController(service);
  }

  @Nested
  @DisplayName("GET '/products' 메소드는")
  class Describe_get_productList{

    @Nested
    @DisplayName("저장된 product들이 있을 때")
    class Context_with_products {

      @BeforeEach
      void prepare() {
        createProduct1 = service.create(new Product(testId, testBrand, testPrice, ""));
        createProduct2 = service.create(new Product(testId, testBrand, testPrice, ""));
      }

      @AfterEach
      void deleteAll() {
        productDeleteService.deleteAll();
      }

      @Test
      @DisplayName("productList를 리턴한다")
      void it_returns_productList() throws Exception {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(createProduct1.getId()))
            .andExpect(jsonPath("$[1].id").value(createProduct2.getId()));
      }
    }

    @Nested
    @DisplayName("저장된 product들이 없을 때")
    class Context_without_product {

      @BeforeEach
      void prepare() {
        productDeleteService.deleteAll();
      }

      @Test
      @DisplayName("비어있는 리스트를 리턴한다")
      void it_returns_empty_productList() throws Exception {
        mockMvc.perform(get("/products"))
            .andExpect(status().isOk())
            .andExpect(content().string("[]"));
      }
    }
  }

    @Nested
    @DisplayName("GET /products/{id} 메소드는")
    class Describe_get_product_with_id {

      @Nested
      @DisplayName("가져올 product의 id가 존재한다면")
      class Context_with_id {

        @BeforeEach
        void prepare() {
          createProduct1 = controller.createProduct(new Product(testId, testBrand, testPrice, ""));
        }

        @AfterEach
        void deleteAll() {
          productDeleteService.deleteAll();
        }

        @Test
        @DisplayName("해당 id를 가진 product를 리턴한다")
        void it_returns_product() throws Exception {
          mockMvc.perform(get("/products/" + createProduct1.getId()))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.brand").value(createProduct1.getBrand()));
        }
      }
    }

    @Nested
    @DisplayName("POST /products 메소드는")
    class Describe_post {

      @Nested
      @DisplayName("저장할 product가 있다면")
      class Context_with_product {

        @BeforeEach
        void prepare() throws JsonProcessingException {
          createProduct1 = new Product(testId, testBrand, testPrice, "");
          content = new ObjectMapper().writeValueAsString(createProduct1);
        }

        @AfterEach
        void deleteAll() {
          productDeleteService.deleteAll();
        }

        @Test
        @DisplayName("해당 객체를 저장한 후 리턴한다")
        void it_returns_product() throws Exception {
          mockMvc.perform(post("/products")
              .content(content)
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isCreated())
              .andExpect(jsonPath("$.brand").value(createProduct1.getBrand()));
        }
      }
    }

//  @Nested
//  @DisplayName("UPDATE /product/{id} 메소드는")
//  class Describe_update {
//    @Nested
//    @DisplayName("update 할 product의 id가 존재한다면")
//    class Context_with_id {
//
//      @BeforeEach
//      void prepare() throws JsonProcessingException {
//        createProduct1 = controller.createProduct(new Product(productId, productBrand, 1000, ""));
//        createProduct2 = new Product(productId, "업데이트", 1000, "");
//        content = new ObjectMapper().writeValueAsString(createProduct2);
//      }
//
//      @Test
//      @DisplayName("해당 id를 가진 product객체의 정보를 업데이트 한다")
//      void it_returns_updateProduct() throws Exception {
//        mockMvc.perform(put("/products/"+createProduct1.getId())
//            .content(content)
//            .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.brand").value(createProduct2.getBrand()));
//      }
//    }
//  }

  @Nested
  @DisplayName("DELTE /product/{id} 메소드는")
  class Describe_delete {

    @Nested
    @DisplayName("delete할 product의 id가 존재한다면")
    class Context_with_id {

      @BeforeEach
      void prepare() throws Exception {
        createProduct1 = controller.createProduct(new Product(testId, testBrand, testPrice, ""));
      }

      @Test
      @DisplayName("해당 id를 가진 Product객체를 삭제 후 리턴한다")
      void it_returns_deleteProduct() throws Exception{
        mockMvc.perform(delete("/products/"+createProduct1.getId()))
            .andExpect(status().isNoContent())
            .andExpect(jsonPath("$.brand").value(createProduct1.getBrand()));
      }
    }

    @Nested
    @DisplayName("delete할 product의 id가 존재하지 않는다면")
    class Context_without_id {

      @BeforeEach
      void prepare () {
        productDeleteService.deleteAll();
      }

      @Test
      @DisplayName("productNotFoundException을 던진다")
      void it_returns_ProductNotFoundException() throws Exception {
        mockMvc.perform(delete("/products/"+testId))
            .andExpect(status().isNotFound());
      }
    }
  }
}
