package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductJPARepository;
import com.codesoom.assignment.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
class ProductServiceTest {

    private ProductJPARepository repository;
    private ProductService service;

    @BeforeEach
    void setUp(){
        repository = mock(ProductJPARepository.class);
        service = new ProductService(repository);
    }

    @Test
    void getProducts(){
        List<Product> products = new ArrayList<>();
        Product product = new Product(1L , "첫번째 고양이" , "코드숨" , 10000 , null);
        products.add(product);

        given(repository.findAll()).willReturn(products);

        List<Product> mockProducts = service.findAll();
        assertThat(mockProducts).hasSize(1);
        assertThat(mockProducts.get(0)).isEqualTo(product);

        verify(repository).findAll();
    }

    @Test
    void createProduct(){
        Product product = new Product(1L , "첫번째 고양이" , "코드숨" , 10000 , null);
        given(repository.save(product)).will(invocation -> {
            return invocation.getArgument(0);
        });

        assertThat(service.save(product)).isEqualTo(product);

        verify(repository).save(any(Product.class));
    }

    @Test
    void deleteProduct(){
//        given(repository.deleteById(100L)).willThrow(NullPointerException.class);

        assertThatThrownBy(() -> service.deleteById(100L))
                .isInstanceOf(NullPointerException.class);

        verify(repository).deleteById(100L);
    }
}
