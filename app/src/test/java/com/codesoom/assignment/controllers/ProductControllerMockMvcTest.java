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
import static org.hamcrest.Matchers.equalTo;
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
public class ProductControllerMockMvcTest {
    private static final String TEST_NAME = "testName";
    private static final String TEST_MAKER = "testMaker";
    private static final Long TEST_PRICE = 5000L;
    private static final String TEST_IMAGE_PATH = "testImagePath.jpg";
    private static final String CREATE_POSTFIX = "...";
    private static final String UPDATE_POSTFIX = "!!!";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product(
                TEST_NAME, TEST_MAKER, TEST_PRICE, TEST_IMAGE_PATH);
        products.add(product);

        given(productService.getProducts()).willReturn(products);

        given(productService.getProduct(1L)).willReturn(product);

        given(productService.getProduct(100L))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.updateProduct(eq(100L), any(Product.class)))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.deleteProduct(100L))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.createProduct(any(Product.class)))
                .will(invocation ->  {
                    Product updatedProduct = new Product(
                            TEST_NAME + CREATE_POSTFIX,
                            TEST_MAKER + CREATE_POSTFIX,
                            TEST_PRICE + 1000L,
                            CREATE_POSTFIX + TEST_IMAGE_PATH);

                    return updatedProduct;
                });

        given(productService.updateProduct(eq(1L), any(Product.class)))
                .will(invocation ->  {
                    Product updatedProduct = new Product(
                            TEST_NAME + UPDATE_POSTFIX,
                            TEST_MAKER + UPDATE_POSTFIX,
                            TEST_PRICE + 2000L,
                            UPDATE_POSTFIX + TEST_IMAGE_PATH);

                    return updatedProduct;
                });
    }

    @Test
    void list() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(TEST_NAME)));

        verify(productService).getProducts();
    }

    @Test
    void detailWithValidId() throws Exception {
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(TEST_NAME)));

        verify(productService).getProduct(1L);
    }

    @Test
    void detailWithInvalidId() throws Exception {
        mockMvc.perform(get("/products/100"))
                .andExpect(status().isNotFound());

        verify(productService).getProduct(100L);
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(
                        post("/products")
                                .contentType(MediaType.APPLICATION_JSON)//body 형식(JSON 형식)
                                .content("{\"id\":1,\"name\":\"testName\"}")//실제 body(JSON 형식만 지키면 아무거나 써도 상관없음)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        equalTo("{\"id\":null," +
                                "\"name\":\"testName...\"," +
                                "\"maker\":\"testMaker...\"," +
                                "\"price\":6000," +
                                "\"imagePath\":\"...testImagePath.jpg\"}")
                ));

        verify(productService).createProduct(any(Product.class));
    }

    @Test
    void updateExistedId() throws Exception {
        mockMvc.perform(
                        patch("/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"name\":\"testName\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(
                        equalTo("{\"id\":null," +
                                "\"name\":\"testName!!!\"," +
                                "\"maker\":\"testMaker!!!\"," +
                                "\"price\":7000," +
                                "\"imagePath\":\"!!!testImagePath.jpg\"}")
                ));

        verify(productService).updateProduct(eq(1L), any(Product.class));

    }

    @Test
    void updateNotExistedId() throws Exception {
        mockMvc.perform(
                        patch("/products/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"name\":\"testName\"}")
                )
                .andExpect(status().isNotFound());


        verify(productService).updateProduct(eq(100L), any(Product.class));
    }

    @Test
    void deleteExistedId() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(1L);
    }

    @Test
    void deleteNotExistedId() throws Exception {
        mockMvc.perform(delete("/products/100"))
                .andExpect(status().isNotFound());

        verify(productService).deleteProduct(100L);
    }
}
