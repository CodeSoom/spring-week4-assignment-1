package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductControllerTest {
    private ProductController productController;
    private ProductService productService;
    private static final String PRODUCT_NAME = "캣타워";
    private static final String PRODUCT_MAKER = "야옹이";
    private static final int PRODUCT_PRICE = 20000;
    private static final String PRODUCT_IMAGE_URL = "https://pixabay.com/photos/cat-kitten-playful-pet-feline-5694895/";
    private static final String PRODUCT_NAME_CREATED_OR_UPDATED = "장난감뱀";
    private static final String PRODUCT_MAKER_CREATED_OR_UPDATED = "호랑이";
    private static final int PRODUCT_PRICE_CREATED_OR_UPDATED = 30000;
    private static final String PRODUCT_IMAGE_URL_CREATED_OR_UPDATED = "https://pixabay.com/photos/animal-close-up-cobra-outdoors-1836120/";

    @BeforeEach
    void setUp(){
        // subject
        productService = mock(ProductService.class);
        productController = new ProductController(productService);

        // fixtures
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setId(1L);
        product.setName(PRODUCT_NAME);
        product.setMaker(PRODUCT_MAKER);
        product.setPrice(PRODUCT_PRICE);
        product.setImageUrl(PRODUCT_IMAGE_URL);
        products.add(product);

        given(productController.list()).willReturn(products);

        given(productController.detail(1L)).willReturn(product);

        setUpSaveProduct();
        setUpUpdateProduct();
    }

    void setUpSaveProduct() {
        given(productController.create(any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(2L);
            return product;
        });
    }

    void setUpUpdateProduct() {
        given(productController.updatePut(any(Long.class), any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(1);
            product.setId(invocation.getArgument(0));
            return product;
        });
    }

    @Test
    void list() {
        assertThat(productController.list().size()).isEqualTo(1);

        verify(productService).getProducts();
    }

    @Test
    void detail() {
        Product product = productController.detail(1L);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL);

        verify(productService).getProduct(1L);
    }

    @Test
    void create() {
        Product source = new Product();
        source.setName(PRODUCT_NAME_CREATED_OR_UPDATED);
        source.setMaker(PRODUCT_MAKER_CREATED_OR_UPDATED);
        source.setPrice(PRODUCT_PRICE_CREATED_OR_UPDATED);
        source.setImageUrl(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);
        Product product = productController.create(source);

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME_CREATED_OR_UPDATED);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER_CREATED_OR_UPDATED);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE_CREATED_OR_UPDATED);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);

        verify(productService).createProduct(source);
    }

    @Test
    void updatePut() {
        Product source = new Product();
        source.setName(PRODUCT_NAME_CREATED_OR_UPDATED);
        source.setMaker(PRODUCT_MAKER_CREATED_OR_UPDATED);
        source.setPrice(PRODUCT_PRICE_CREATED_OR_UPDATED);
        source.setImageUrl(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);

        Product product = productController.updatePut(1L, source);

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME_CREATED_OR_UPDATED);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER_CREATED_OR_UPDATED);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE_CREATED_OR_UPDATED);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);

        verify(productService).updateProduct(1L, source);
    }

    @Test
    void updatePatch() {
        Product source = new Product();
        source.setName(PRODUCT_NAME_CREATED_OR_UPDATED);
        source.setMaker(PRODUCT_MAKER_CREATED_OR_UPDATED);
        source.setPrice(PRODUCT_PRICE_CREATED_OR_UPDATED);
        source.setImageUrl(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);

        Product product =  productController.updatePatch(1L, source);

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME_CREATED_OR_UPDATED);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER_CREATED_OR_UPDATED);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE_CREATED_OR_UPDATED);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);

        verify(productService).updateProduct(1L, source);
    }

    @Test
    void delete() {
        productController.delete(1L);

        verify(productService).deleteProduct(1L);
    }
}