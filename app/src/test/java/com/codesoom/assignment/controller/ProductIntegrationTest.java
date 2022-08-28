package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductJPARepository;
import com.codesoom.assignment.service.ProductSearchService;
import com.codesoom.assignment.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductIntegrationTest {

    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ProductSearchService searchService;
    @Autowired
    private ProductService service;
    @Autowired
    private ProductControllerAdvice advice;
    @Autowired
    private ProductController controller;

    private final Long SIZE = 3L;
    private final String TITLE = "proudct";
    private final String MAKER = "maker";

    @BeforeEach
    void setUp() {
        advice = new ProductControllerAdvice();
        controller = new ProductController(service , searchService);

        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(advice)
                .build();
    }

    Product newProduct(long number){
        return new Product(number , TITLE + number , MAKER + number , (int) number , null);
    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_FindById{

        @Nested
        @DisplayName("{id}에 해당하는 자원이 있다면")
        class Context_ExistedId{

            private final Product product = newProduct(1L);
            private String content;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                service.save(product);
                content = mapper.writeValueAsString(product);
            }

            @Test
            @DisplayName("상태 200 , 자원을 반환한다")
            void It_ReturnResource() throws Exception {
                mvc.perform(get("/products/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(equalTo(content)));
            }
        }
    }
}
