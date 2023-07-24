package com.codesoom.assignment.product.infra.api;

import com.codesoom.assignment.product.application.ProductNotFoundException;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.dto.ProductRequest;
import com.codesoom.assignment.product.infra.persistence.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("상품을 등록 후 생성된 상품정보를 반환한다.")
    void createProduct() throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest("catToy", "CatMaker", 1200, "test/img.jpg");
        String jsonString = objectMapper.writeValueAsString(productRequest);

        // expected
        mockMvc.perform(post("/products")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("catToy"))
                .andExpect(jsonPath("maker").value("CatMaker"))
                .andExpect(jsonPath("price").value(1200))
                .andExpect(jsonPath("imageUrl").value("test/img.jpg"))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 리스트 요청 시 상품리스트 반환한다.")
    void getProducts() throws Exception {
        // given
        Product product = Product.builder()
                .name("catToy1")
                .price(2000)
                .maker("maker1")
                .imageUrl("test/img1.jpg")
                .build();

        productRepository.save(product);

        // expected
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value("catToy1"))
                .andExpect(jsonPath("[0].maker").value("maker1"))
                .andExpect(jsonPath("[0].price").value(2000))
                .andExpect(jsonPath("[0].imageUrl").value("test/img1.jpg"))
                .andDo(print());
    }

    @Test
    @DisplayName("단일 상품 조회 시 해당 상품정보를 반환한다.")
    void getProduct() throws Exception {
        // given
        Product product = Product.builder()
                .name("catToy1")
                .price(2000)
                .maker("maker1")
                .imageUrl("test/img1.jpg")
                .build();

        Product savedProduct = productRepository.save(product);

        // expected
        mockMvc.perform(get("/products/" + savedProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("catToy1"))
                .andExpect(jsonPath("maker").value("maker1"))
                .andExpect(jsonPath("price").value(2000))
                .andExpect(jsonPath("imageUrl").value("test/img1.jpg"))
                .andDo(print());
    }

    @Test
    @DisplayName("단일 상품 조회 시 없는 경우 ProductNotFound 예외 발생")
    void getProductNotFound() throws Exception {

        // expected
        mockMvc.perform(get("/products/" + 1L))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
                .andExpect(jsonPath("message").value(ProductNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 수정 요청시 해당 상품정보를 수정한다.")
    void updateProduct() throws Exception {
        // given
        Product product = Product.builder()
                .name("catToy1")
                .price(2000)
                .maker("maker1")
                .imageUrl("test/img1.jpg")
                .build();
        Product savedProduct = productRepository.save(product);
        ProductRequest productRequest = new ProductRequest("update", "update", 3000, "test/update.jpg");

        // expected
        mockMvc.perform(patch("/products/" + savedProduct.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("update"))
                .andExpect(jsonPath("maker").value("update"))
                .andExpect(jsonPath("price").value(3000))
                .andExpect(jsonPath("imageUrl").value("test/update.jpg"))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 수정 요청시 없는 경우 ProductNotFound 예외 발생")
    void updateProductNotFound() throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest("update", "update", 3000, "test/update.jpg");

        // expected
        mockMvc.perform(patch("/products/" + 100L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                )
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
                .andExpect(jsonPath("message").value(ProductNotFoundException.MESSAGE))
                .andDo(print());
    }

    @Test
    @DisplayName("상품 삭제 요청 시 해당 상품을 삭제한다.")
    void deleteProducts() throws Exception {
        // given
        Product product = Product.builder()
                .name("deleteTarget")
                .price(2000)
                .maker("deleteMaker")
                .imageUrl("test/delete.jpg")
                .build();
        productRepository.save(product);

        // expected
        mockMvc.perform(delete("/products/" + product.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());
        
    }
}
