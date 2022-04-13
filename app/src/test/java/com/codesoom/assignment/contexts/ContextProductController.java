package com.codesoom.assignment.contexts;

import com.codesoom.assignment.controllers.ProductController;
import com.codesoom.assignment.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class ContextProductController {

    protected MockMvc mockMvc;

    @Autowired
    protected ProductService productService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductController(productService))
                .build();
    }

}
