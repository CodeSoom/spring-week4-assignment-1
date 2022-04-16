package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {
    private ProductService service;
    private ProductRepository repository;

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

        Product product = new Product();
        product.setId(ID);
        product.setMaker(MAKER);
        product.setPrice(PRICE);
        product.setName(NAME);
        product.setImage(IMAGE);

        products.add(product);

        given(repository.findAll()).willReturn(products);
        given(repository.findById(ID)).willReturn(Optional.of(product));
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

        assertThat(product.getId()).isEqualTo(ID);
        assertThat(product.getMaker()).isEqualTo(MAKER);
        assertThat(product.getPrice()).isEqualTo(PRICE);
        assertThat(product.getImage()).isEqualTo(IMAGE);
        assertThat(product.getName()).isEqualTo(NAME);
    }
}