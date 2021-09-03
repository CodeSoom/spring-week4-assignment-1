package com.codesoom.assignment;

import com.codesoom.assignment.applications.ProductService;
import com.codesoom.assignment.domains.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerWebTest {
    private String TITLE = "Test1";
    private String NEW_TITLE = "Test2";
    private String UPDATED_TITLE = "Test1_updated";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService ProductService;

    @BeforeEach
    void setUp(){
        Product source = new Product();
        source.setTitle(TITLE);
        ProductService.createProduct(source);
    }

    @Test
    //@Order(3)
    @DisplayName("할일 목록 가져오기")
    void list() throws Exception{
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThan(0))))
                .andDo(print());
    }

    @Test
    //@Order(2)
    @DisplayName("1번 할일 가져오기 Validation : isOk, expect TITLE")
    void getProduct() throws Exception{
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(TITLE)));
    }

    @Test
    //@Order(1)
    @DisplayName("할일 생성하기  Validation : isCreated, expect NEW_TITLE")
    void createProduct() throws Exception{
        Product newProduct = new Product();
        newProduct.setTitle(NEW_TITLE);
        String content = objectMapper.writeValueAsString(newProduct);
        mockMvc.perform(post("/products").content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(NEW_TITLE));
    }

    @Test
    //@Order(4)
    @DisplayName("할일 수정하기 Validation : isOk, expect UPDATED_TITLE")
    void updateProduct() throws Exception{
        Product newProduct = new Product();
        newProduct.setTitle(UPDATED_TITLE);

        String content = objectMapper.writeValueAsString(newProduct);
        mockMvc.perform(put("/products/1").content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(UPDATED_TITLE));
    }

    @Test
    //@Order(5)
    @DisplayName("할일 삭제하기 Validation : isNoContent")
    void deleteProduct() throws Exception{
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}
