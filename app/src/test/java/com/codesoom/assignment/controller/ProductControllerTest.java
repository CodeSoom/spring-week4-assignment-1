package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ProductControllerTest {

    ProductService service;
    ProductController controller;

    private Product PRODUCT;
    private final long ID = 1L;
    private final String MAKER = "KOREAN SHORT CAT";
    private final int PRICE = 20000;
    private final String NAME = "CAT FISHING ROD";
    private final String IMAGE = "https://www.zoostore.de/media/image/product/4598/sm/katzenspielzeug-katzenangel-spielangel-zum-zusammenschrauben-mit-heuschrecke~2.jpg";

    @BeforeEach
    void setUp() {
        service = mock(ProductService.class);
        controller = new ProductController(service);

        List<Product> products = new ArrayList<>();

        PRODUCT = new Product();
        PRODUCT.setId(ID);
        PRODUCT.setMaker(MAKER);
        PRODUCT.setPrice(PRICE);
        PRODUCT.setName(NAME);
        PRODUCT.setImage(IMAGE);
        products.add(PRODUCT);

        given(service.getProducts()).willReturn(products);
    }

    @Test
    void getProducts() {
        List<Product> products = controller.list();

        assertThat(products).isNotEmpty();
    }

    @Test
    void getEmptyProducts() {
        given(service.getProducts()).willReturn(new ArrayList<>());

        List<Product> products = controller.list();

        assertThat(products).isEmpty();
    }
}