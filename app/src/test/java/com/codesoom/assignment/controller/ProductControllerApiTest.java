package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.eception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest //통합테스트
@AutoConfigureMockMvc //use Spring’s MockMvc and ask for that to be injected for you by using the @AutoConfigureMockMvc annotation on the test case.
public class ProductControllerApiTest {

    //Todo 개별적으로 테스트 했을 때는 테스트가 통과되나 모두 테스트 했을 때는 통과되지 않는 상태

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    public static final long VALID_ID = 1L;
    public static final long INVALID_ID = 100L;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();

        Product product1 = Product.builder()
                .name("name1")
                .maker("maker1")
                .price(300)
                .imagePath("path1")
                .build();

        products.add(product1);

        Product product2 = Product.builder()
                .name("name2")
                .maker("maker2")
                .price(300)
                .imagePath("path2")
                .build();

        products.add(product2);

        given(productService.getProducts()).willReturn(products);

        given(productService.getProduct(VALID_ID)).willReturn(product1);

        given(productService.getProduct(INVALID_ID)).willThrow(new ProductNotFoundException(INVALID_ID));

        given(productService.createProduct(any(Product.class)))
                .willReturn(product1);

        given(productService.updateProduct(eq(100L), any(Product.class)))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.deleteProduct(100L))
                .willThrow(new ProductNotFoundException(100L));
    }

    //Todo 개별적으로 테스트 했을 때는 테스트가 통과되나 모두 테스트 했을 때는 통과되지 않는 상태

    @Nested
    class Describe_list{

        @Test
        void it_returns_a_product_list() throws Exception {
            mockMvc.perform(get("/products"))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class Describe_findById{

        @Nested
        class Context_with_valid_parameter{
            Long id = VALID_ID;

            @Test
            void it_returns_a_status_Ok() throws Exception {
                mockMvc.perform(get("/products/" + id))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        class Context_with_invalid_parameter{
            Long id = INVALID_ID;

            @Test
            void it_returns_a_status_NotFound() throws Exception {
                mockMvc.perform(get("/products/" + id))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Describe_create{
        String name = "create_test";
        Product source = Product.builder()
                    .name(name)
                    .maker("TEST_VAR_MAKER")
                    .price(3000)
                    .imagePath("TEST_VAR_IMAGE_PATH")
                    .build();

        @Test
        void it_returns_a_created_product() throws Exception {
            mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(source)))
                .andExpect(status().isCreated());
        }

    }

    @Nested
    class Describce_update{
        Product updateSource = Product.builder()
                .name("update_test")
                .maker("TEST_VAR_MAKER")
                .price(3000)
                .imagePath("TEST_VAR_IMAGE_PATH")
                .build();

        @Nested
        class Context_with_valid_parameter{
            Long id = VALID_ID;

            @Test
            void it_returns_a_status_Ok() throws Exception {
                mockMvc.perform(patch("/products/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateSource)))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        class Context_with_invalid_parameter{
            Long id = INVALID_ID;

            @Test
            void it_returns_a_status_NotFound() throws Exception {
                mockMvc.perform(patch("/products/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateSource)))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class Describce_delete{

        @Nested
        class Context_with_valid_parameter{
            Long id = VALID_ID;

            @Test
            void it_returns_a_status_Ok() throws Exception {
                mockMvc.perform(delete("/products/"+id))
                        .andExpect(status().isOk());
            }
        }

        @Nested
        class Context_with_invalid_parameter{
            Long id = INVALID_ID;

            @Test
            void it_returns_a_status_NotFound() throws Exception {
                mockMvc.perform(delete("/products/"+id))
                        .andExpect(status().isNotFound());
            }
        }
    }

}
