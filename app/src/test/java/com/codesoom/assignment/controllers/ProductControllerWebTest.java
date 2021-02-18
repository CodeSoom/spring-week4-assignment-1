package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {

        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setName("Test Product");
        products.add(product);

        given(productService.getProducts()).willReturn(products);

        given(productService.getProduct(1L)).willReturn(product);

        given(productService.getProduct(100L))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.updateProduct(eq(100L), any(Product.class)))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.deleteProduct(100L))
                .willThrow(new ProductNotFoundException(100L));
    }

    @Test
    void list() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Product")));

        verify(productService).getProducts();
    }

    @Test
    void detailWithValidId() throws Exception {
        mockMvc.perform(get("/products/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Product")));

        verify(productService).getProduct(1L);
    }

    @Test
    void detailWithInvalidId() throws Exception {
        mockMvc.perform(get("/products/100"))
                .andExpect(status().isNotFound());

        verify(productService).getProduct(100L);
    }

    @Test
    void createProduct() throws Exception {
        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"new Product\"}")
        )
                .andExpect(status().isCreated());

        verify(productService).createProduct(any(Product.class));
    }

    @Test
    void updateProduct() throws Exception {
        mockMvc.perform(
                patch("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Renamed Product\"}")
        )
                .andExpect(status().isOk());

        verify(productService).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void updateNotExistedProduct() throws Exception {
        mockMvc.perform(
                patch("/products/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Renamed Product\"}")
        )
                .andExpect(status().isNotFound());

        verify(productService).updateProduct(eq(100L), any(Product.class));
    }

    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());

        verify(productService).deleteProduct(1L);
    }

    @Test
    void deleteNotExistedProduct() throws Exception {
        mockMvc.perform(delete("/products/100"))
                .andExpect(status().isNotFound());

        verify(productService).deleteProduct(100L);
    }
}
