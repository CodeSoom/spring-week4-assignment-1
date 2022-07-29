package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collection;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@DataJpaTest
class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private static final String TOY_NAME = "test";
    @BeforeEach
    void setUp(){
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);

        Product toy = new Product();
        productService.createToy(toy);
    }

    @Test
    void getTasks(){
        given(productRepository.findAll()).willReturn(new ArrayList<>());
        Collection<Product> products = productService.getProducts();

        verify(productRepository).findAll();

//        assertThat(catToys).hasSize(1);
//        CatToy toy = catToys.get(0);
//        assertThat(toy.getName()).isEqualTo(TOY_NAME);
    }


}