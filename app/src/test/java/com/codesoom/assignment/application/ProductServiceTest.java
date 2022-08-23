package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductJPARepository;
import com.codesoom.assignment.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
class ProductServiceTest {

    private ProductJPARepository repository;
    private ProductService service;

    private final Long SIZE = 3L;
    private final String TITLE = "proudct";
    private final String MAKER = "maker";

    public ProductServiceTest(){
        repository = mock(ProductJPARepository.class);
        service = new ProductService(repository);
    }

    Product addProduct(long number){
        Product product = new Product(number , TITLE + number , MAKER + number , (int) number , null);
        return product;
    }

    List<Product> addProducts(long size){
        List<Product> prodcucts = new ArrayList<>();
        for(long l = 1 ; l <= size ; l++){
            prodcucts.add(addProduct(l));
        }
        return prodcucts;
    }

    @Nested
    @DisplayName("findAll 메소드는")
    class Describe_FindAll{

        @Nested
        @DisplayName("상품들이 있다면")
        class Context_ExistedProducts{

            private List<Product> products;

            @BeforeEach
            void setUp(){
                products = addProducts(SIZE);
                given(repository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("상품들을 List로 반환한다")
            void It_ReturnJSON(){
                assertThat(service.findAll()).hasSize(products.size());

                verify(repository).findAll();
            }
        }

        @Nested
        @DisplayName("상품들이 없다면")
        class Context_NotExistedProduct{

            @BeforeEach
            void setUp() {
                given(repository.findAll()).willReturn(new ArrayList<>());
            }

            @Test
            @DisplayName("빈 List를 반환한다")
            void It_ReturnEmptyList(){
                assertThat(service.findAll()).isEqualTo(new ArrayList<>());

                verify(repository).findAll();
            }
        }
    }




}
