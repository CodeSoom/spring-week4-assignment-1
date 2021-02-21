package com.codesoom.assignment.controller;

import com.codesoom.assignment.adapter.InMemoryProductRepository;
import com.codesoom.assignment.application.ProductApplicationService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductControllerTest {
    private ProductController controller;
    private ProductApplicationService service;

    @BeforeEach
    void initController() {
        ProductRepository repository = new InMemoryProductRepository();
        service = new ProductApplicationService(repository);
        controller = new ProductController(service);
    }

    @Test
    void getAllProduct() {
        List<ProductDTO> products = controller.getAllProduct();

        assertThat(products).hasSize(0);
    }

    @Test
    void getSpecificProduct() {
        String name = "고양이 인형";
        String maker = "라스 공방";
        String price = "1000원";
        String imageURL = "https://magical.dev/static/las.jpg";

        Product createdProduct = service.createProduct(name, maker, price, imageURL);

        ProductDTO product = controller.getSpecificProduct(createdProduct.productId().id());

        assertThat(product).isNotNull();
        assertThat(product.id).isEqualTo(createdProduct.productId().id());
    }

    @Test
    void createProduct() {
        String name = "고양이 인형";
        String maker = "라스 공방";
        String price = "1000원";
        String imageURL = "https://magical.dev/static/las.jpg";
        ProductDTO product = new ProductDTO(null, name, maker, price, imageURL);

        ProductDTO createdProduct = controller.createProduct(product);

        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.name).isEqualTo(name);
    }
}
