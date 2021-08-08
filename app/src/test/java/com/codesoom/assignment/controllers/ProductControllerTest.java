// REST API
// 1. GET /products
// 2. GET /products/{id}
// 3. POST /products
// 4. PUT/PATCH /products/{id}
// 5. DELETE /products/{id}

package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp(){
        Product product = new Product(1L,"쥐돌이","냥이월드",5000);
        given(productService.getProducts()).willReturn(List.of(product));
        given(productService.getProduct(1L)).willReturn(product);
        given(productService.getProduct(1000L)).willThrow(new ProductNotFoundException(1000L));
        given(productService.createProduct(any(Product.class))).willReturn(product);
        given(productService.updateProduct(eq(1L),any(Product.class)))
                .will(invocation -> {
                    Long id = invocation.getArgument(0);
                    Product source = invocation.getArgument(1);
                    return new Product(
                            source.getId(),
                            source.getName(),
                            source.getMaker(),
                            source.getPrice()
                    );
        });
        given(productService.updateProduct(eq(1000L),any(Product.class)))
                .willThrow(new ProductNotFoundException(1000L));

        given(productService.deleteProduct(1000L))
                .willThrow(new ProductNotFoundException(1000L));
    }

    @Test
    void list() throws Exception{
        mockMvc.perform(
                get("/products")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("쥐돌이")));

    }

    @Test
    void deatilWithExsitedProduct() throws Exception{
        mockMvc.perform(
                get("/products/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("쥐돌이")));

    }

    @Test
    void deatilWithNotExsitedProduct() throws Exception{
        mockMvc.perform(
                get("/products/1000"))
                .andExpect(status().isNotFound());
    }
    @Test
    void create() throws Exception{
        mockMvc.perform(
                post("/products")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"쥐돌이\",\"maker\":\"냥이월드\",\"price\":5000}")
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("쥐돌이")));

       verify(productService).createProduct(any(Product.class));
    }

    @Test
    void updateWithExistedProduct() throws Exception{
        mockMvc.perform(
                patch("/products/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"쥐순이\",\"maker\":\"냥이월드\",\"price\":5000}")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("쥐순이")));

        verify(productService).updateProduct(eq(1L),any(Product.class));
    }

    @Test
    void updateWithNotExistedProduct() throws Exception{
        mockMvc.perform(
                patch("/products/1000")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"쥐순이\",\"maker\":\"냥이월드\",\"price\":5000}")
        )
                .andExpect(status().isNotFound());

        verify(productService).updateProduct(eq(1000L),any(Product.class));
    }

    @Test
    void destroyExistedProduct() throws Exception{
        mockMvc.perform(
                delete("/products/1"))
                .andExpect(status().isOk());

        verify(productService).deleteProduct(1L);
    }

    @Test
    void destroyNotExistedProduct() throws Exception{
        mockMvc.perform(
                delete("/products/1000"))
                .andExpect(status().isNotFound());

        verify(productService).deleteProduct(1000L);
    }
}