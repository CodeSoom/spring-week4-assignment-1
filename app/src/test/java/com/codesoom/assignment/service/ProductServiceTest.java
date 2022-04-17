package com.codesoom.assignment.service;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private static final String PRODUCT_NAME = "캣타워";
    private static final String PRODUCT_MAKER = "야옹이";
    private static final int PRODUCT_PRICE = 20000;
    private static final String PRODUCT_IMAGE_URL = "https://pixabay.com/photos/cat-kitten-playful-pet-feline-5694895/";
    private static final String PRODUCT_NAME_CREATED_OR_UPDATED = "장난감뱀";
    private static final String PRODUCT_MAKER_CREATED_OR_UPDATED = "호랑이";
    private static final int PRODUCT_PRICE_CREATED_OR_UPDATED = 30000;
    private static final String PRODUCT_IMAGE_URL_CREATED_OR_UPDATED = "https://pixabay.com/photos/animal-close-up-cobra-outdoors-1836120/";

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);

        productService = new ProductService(productRepository);

        setUpFixtures();
        setUpSaveProduct();
    }

    void setUpFixtures() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName(PRODUCT_NAME);
        product.setMaker(PRODUCT_MAKER);
        product.setPrice(PRODUCT_PRICE);
        product.setImageUrl(PRODUCT_IMAGE_URL);
        products.add(product);

        given(productRepository.findAll()).willReturn(products);

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(productRepository.findById(100L)).willReturn(Optional.empty());
    }

    void setUpSaveProduct() {
        given(productRepository.save(any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(2L);
            return product;
        });

    }

    @Test
    void getProducts() {
        List<Product> products = productService.getProducts();

        verify(productRepository).findAll();

        assertThat(products).hasSize(1);

        Product product = products.get(0);
        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);
    }

    @Test
    void getProductWithExistedId() {
        Product product = productService.getProduct(1L);

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME);

        verify(productRepository).findById(1L);
    }

    @Test
    void getProductWithNotExistedId() {
        assertThatThrownBy(() -> productService.getProduct(100L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(100L);
    }

    @Test
    void createProduct() {
        Product source = new Product();
        source.setName(PRODUCT_NAME_CREATED_OR_UPDATED);
        source.setMaker(PRODUCT_MAKER_CREATED_OR_UPDATED);
        source.setPrice(PRODUCT_PRICE_CREATED_OR_UPDATED);
        source.setImageUrl(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);

        Product product = productService.createProduct(source);

        verify(productRepository).save(any(Product.class));

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME_CREATED_OR_UPDATED);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER_CREATED_OR_UPDATED);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE_CREATED_OR_UPDATED);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);
    }

    @Test
    void updateProduct() {
        Product source = new Product();
        source.setName(PRODUCT_NAME_CREATED_OR_UPDATED);
        source.setMaker(PRODUCT_MAKER_CREATED_OR_UPDATED);
        source.setPrice(PRODUCT_PRICE_CREATED_OR_UPDATED);
        source.setImageUrl(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);

        Product product = productService.updateProduct(1L, source);

        assertThat(product.getName()).isEqualTo(PRODUCT_NAME_CREATED_OR_UPDATED);
        assertThat(product.getMaker()).isEqualTo(PRODUCT_MAKER_CREATED_OR_UPDATED);
        assertThat(product.getPrice()).isEqualTo(PRODUCT_PRICE_CREATED_OR_UPDATED);
        assertThat(product.getImageUrl()).isEqualTo(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);

        verify(productRepository).findById(1L);
    }

    @Test
    void updateProductWithNotExistedId() {
        Product source = new Product();
        source.setName(PRODUCT_NAME_CREATED_OR_UPDATED);
        source.setMaker(PRODUCT_MAKER_CREATED_OR_UPDATED);
        source.setPrice(PRODUCT_PRICE_CREATED_OR_UPDATED);
        source.setImageUrl(PRODUCT_IMAGE_URL_CREATED_OR_UPDATED);

        assertThatThrownBy(() -> productService.updateProduct(100L, source))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(100L);
    }

    @Test
    void deleteProductWithExistedId() {
        productService.deleteProduct(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProductWithNotExistedId() {
        assertThatThrownBy(() -> productService.deleteProduct(100L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository).findById(100L);
    }
}