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
    private static final Integer TEST_PRICE = 5000;
    private static final String TEST_IMAGE_PATH = "testImagePath.jpg";
    private static final String CREATE_POSTFIX = "...";
    private static final String UPDATE_POSTFIX = "!!!";

    private static final int PRODUCTS_MAX_SIZE = 5;
    private static final Long VALID_PRODUCT_ID = 1L;
    private static final Long INVALID_PRODUCT_ID = 100L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();

        Product product = null;
        for(int i = 0; i < PRODUCTS_MAX_SIZE; i++) {
            product = new Product(TEST_NAME + (i + 1),
                    TEST_MAKER + (i + 1),
                    TEST_PRICE + (i + 1),
                    (i + 1) + TEST_IMAGE_PATH);
            products.add(product);
        }
    }

    @Test
    void list() throws Exception {
        given(productService.getProducts()).willReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(TEST_NAME)));

        verify(productService).getProducts();
    }

    @Test
    void detailWithValidId() throws Exception {
        given(productService.getProduct(VALID_PRODUCT_ID))
                .willReturn(products.get(VALID_PRODUCT_ID.intValue() - 1));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        equalTo("{\"id\":null," +
                                "\"name\":\"testName1\"," +
                                "\"maker\":\"testMaker1\"," +
                                "\"price\":5001," +
                                "\"imagePath\":\"1testImagePath.jpg\"}")));


        verify(productService).getProduct(VALID_PRODUCT_ID);
    }

    @Test
    void detailWithInvalidId() throws Exception {
        given(productService.getProduct(INVALID_PRODUCT_ID))
                .willThrow(new ProductNotFoundException(INVALID_PRODUCT_ID));

        mockMvc.perform(get("/products/100"))
                .andExpect(status().isNotFound());

        verify(productService).getProduct(INVALID_PRODUCT_ID);
    }

    @Test
    void create() throws Exception {
        given(productService.createProduct(any(Product.class)))
                .will(invocation ->  {
                    Product newProduct = new Product(
                            TEST_NAME + CREATE_POSTFIX,
                            TEST_MAKER + CREATE_POSTFIX,
                            TEST_PRICE + 1000,
                            CREATE_POSTFIX + TEST_IMAGE_PATH);

                    return newProduct;
                });

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

//        given(productService.updateProduct(eq(VALID_PRODUCT_ID), any(Product.class)))
//                .will(invocation ->  {
//                    Product updatedProduct = new Product(
//                            TEST_NAME + UPDATE_POSTFIX,
//                            TEST_MAKER + UPDATE_POSTFIX,
//                            TEST_PRICE + 2000L,
//                            UPDATE_POSTFIX + TEST_IMAGE_PATH);
//
//                    return updatedProduct;
//                });


//        mockMvc.perform(
//                        patch("/products/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content("{\"id\":1,\"name\":\"testName\"}")
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string(
//                        equalTo("{\"id\":null," +
//                                "\"name\":\"testName!!!\"," +
//                                "\"maker\":\"testMaker!!!\"," +
//                                "\"price\":7000," +
//                                "\"imagePath\":\"!!!testImagePath.jpg\"}")
//                ));
//
//        verify(productService).updateProduct(eq(VALID_PRODUCT_ID), any(Product.class));


        given(productService.updateProduct(eq(1L), any(Product.class)))
                .will(invocation -> {
                    Product source = invocation.getArgument(1);
                    Long id = invocation.getArgument(0);
                    return new Product(
                            id,
                            source.getName(),
                            source.getMaker(),
                            source.getPrice()
                    );
                });

        mockMvc.perform(
                        patch("/products/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"쥐순이\",\"maker\":\"냥이월드\",\"price\":6000}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("쥐순이")))
                .andExpect(content().string(containsString("냥이월드")))
                .andExpect(content().string(containsString("6000")));

        verify(productService).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void updateNotExistedId() throws Exception {
        given(productService.updateProduct(eq(INVALID_PRODUCT_ID), any(Product.class)))
                .willThrow(new ProductNotFoundException(INVALID_PRODUCT_ID));

        mockMvc.perform(
                        patch("/products/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\":1,\"name\":\"testName\"}")
                )
                .andExpect(status().isNotFound());


        verify(productService).updateProduct(eq(INVALID_PRODUCT_ID), any(Product.class));
    }

    @Test
    void deleteExistedId() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(VALID_PRODUCT_ID);
    }

    @Test
    void deleteNotExistedId() throws Exception {
        given(productService.deleteProduct(INVALID_PRODUCT_ID))
                .willThrow(new ProductNotFoundException(INVALID_PRODUCT_ID));

        mockMvc.perform(delete("/products/100"))
                .andExpect(status().isNotFound());

        verify(productService).deleteProduct(INVALID_PRODUCT_ID);
    }
}
