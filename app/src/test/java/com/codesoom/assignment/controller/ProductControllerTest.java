package com.codesoom.assignment.controller;

import com.codesoom.assignment.adapter.InMemoryProductRepository;
import com.codesoom.assignment.application.ProductApplicationService;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductControllerTest {
    private ProductController controller;

    @BeforeEach
    void initController() {
        ProductRepository repository = new InMemoryProductRepository();
        ProductApplicationService service = new ProductApplicationService(repository);
        controller = new ProductController(service);
    }

    @Test
    void getAllProduct() {
        List<ProductDTO> products = controller.getAllProduct();

        assertThat(products).hasSize(0);
    }
}
