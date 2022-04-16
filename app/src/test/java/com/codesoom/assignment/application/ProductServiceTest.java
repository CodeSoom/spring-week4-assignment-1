package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * 단순 의존 관계를 테스트하기 위한 mock 테스트입니다.
 */
class ProductServiceTest {
    private ProductService service;
    private ProductRepository repository;

    private Product PRODUCT;
    private final long ID = 1L;
    private final String MAKER = "KOREAN SHORT CAT";
    private final int PRICE = 20000;
    private final String NAME = "CAT FISHING ROD";
    private final String IMAGE = "https://www.zoostore.de/media/image/product/4598/sm/katzenspielzeug-katzenangel-spielangel-zum-zusammenschrauben-mit-heuschrecke~2.jpg";

    @BeforeEach
    void setUp() {
        repository = mock(ProductRepository.class);
        service = new ProductService(repository);

        setUpFixtures();
    }

    private void setUpFixtures() {
        List<Product> products = new ArrayList<>();

        PRODUCT = new Product();
        PRODUCT.setId(ID);
        PRODUCT.setMaker(MAKER);
        PRODUCT.setPrice(PRICE);
        PRODUCT.setName(NAME);
        PRODUCT.setImage(IMAGE);

        products.add(PRODUCT);

        given(repository.findAll()).willReturn(products);
        given(repository.findById(ID)).willReturn(Optional.of(PRODUCT));
        given(repository.save(PRODUCT)).willReturn(PRODUCT);
    }

    @Test
    void getProducts() {
        List<Product> products = service.getProducts();

        verify(repository).findAll();

        assertThat(products).hasSize(1);
    }

    @Test
    void getProduct() {
        Product product = service.getProduct(ID);

        verify(repository).findById(ID);

        verifyProduct(product);
    }

    @Test
    void createProduct() {
        Product newProduct = service.createProduct(PRODUCT);

        verify(repository).save(PRODUCT);

        verifyProduct(newProduct);
    }

    @Test
    void deleteProduct() {
        Product removeProduct = service.removeProduct(PRODUCT);

        verify(repository).delete(PRODUCT);

        verifyProduct(removeProduct);
    }

    @Test
    void updateProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setName("updated" + PRODUCT.getName());
        productDto.setImage("updated" + PRODUCT.getImage());
        productDto.setMaker("updated" + PRODUCT.getMaker());
        productDto.setPrice(1000 + PRODUCT.getPrice());


        Product updateProduct = service.updateProduct(ID, productDto);

        verify(repository).findById(ID);

        verifyUpdateProduct(updateProduct);
    }

    private void verifyProduct(Product product) {
        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImage()).isEqualTo(IMAGE);
        assertThat(product.getName()).isEqualTo(NAME);
    }

    private void verifyUpdateProduct(Product product) {
        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getMaker()).isEqualTo("updated" + MAKER);
        assertThat(product.getPrice()).isEqualTo(1000 + PRICE);
        assertThat(product.getImage()).isEqualTo("updated" + IMAGE);
        assertThat(product.getName()).isEqualTo("updated" + NAME);
    }
}