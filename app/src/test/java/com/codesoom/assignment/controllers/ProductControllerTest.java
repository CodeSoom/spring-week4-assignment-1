package com.codesoom.assignment.controllers;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.infra.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductControllerTest {

    private static final String TASK_TITLE = "test1";
    private static final String TASK_TITLE2 = "test2";
    private static final String UPDATE_POSTFIX = "!!!";

    private ProductController controller;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryRepository();

        productService = new ProductService(productRepository);

        controller = new ProductController(productService);

        Product product = new Product();
        product.setMaker(TASK_TITLE);

        controller.create(product);
    }

//    @Test
//    void listWithoutProducts() {
//        // TODO : service -> returns empty list
//
//        // taskService.getTask
//        //Controller -> Sqy -< Real Object
//
//        assertThat(controller.list()).isEmpty();
//    }
    
    @Test
    void listWithSomeProducts() {
        // TODO : service -> returns list that contains one task.

        Product product = new Product();
        product.setMaker(TASK_TITLE);
        controller.create(product);

        assertThat(controller.list()).isNotEmpty();
    }

    @Test
    void createNewProduct() {
        int oldSize = controller.list().size();

        Product product = new Product();
        product.setMaker(TASK_TITLE2);
        controller.create(product);

        int newSize = controller.list().size();

        assertThat(newSize - oldSize).isEqualTo(1);
        assertThat(controller.list().get(1).getId()).isEqualTo(2L);
        assertThat(controller.list().get(1).getMaker()).isEqualTo(TASK_TITLE2);
    }


    @Test
    void detailWithValidId() {
        assertThat(controller.detail(1L).getId()).isEqualTo(1L);
        assertThat(controller.detail(1L).getMaker()).isEqualTo(TASK_TITLE);
    }

    @Test
    void detailWithInvalidId() {
        assertThatThrownBy(() -> controller.detail(0L)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void updateWithValidId() {
        Product source = new Product();
        source.setMaker(UPDATE_POSTFIX + TASK_TITLE);
        productService.updateProduct(1L, source);

        Product product = productService.getProduct(1L);
        assertThat(product.getMaker()).isEqualTo(UPDATE_POSTFIX + TASK_TITLE);
    }

    @Test
    void patchWithValidId() {
        Product source = new Product();
        source.setMaker(UPDATE_POSTFIX + TASK_TITLE2);
        productService.updateProduct(1L, source);

        Product product = productService.getProduct(1L);
        assertThat(product.getMaker()).isEqualTo(UPDATE_POSTFIX + TASK_TITLE2);
    }

    @Test
    void delete() {
        controller.delete(1L);

        assertThat(controller.list().size()).isEqualTo(0);
    }
}
