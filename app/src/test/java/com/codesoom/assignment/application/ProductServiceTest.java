package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ResourceNotFoundException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
@DisplayName("ProductService 테스트")
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

    Product newProduct(long number){
        return new Product(number , TITLE + number , MAKER + number , (int) number , null);
    }

    List<Product> newProducts(long size){
        List<Product> prodcucts = new ArrayList<>();
        for(long l = 1 ; l <= size ; l++){
            prodcucts.add(newProduct(l));
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
                products = newProducts(SIZE);
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

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_FindById{

        @Nested
        @DisplayName("{id}가 null이거나 해당하는 자원이 없다면")
        class Context_NullIdAndNotExistedResource{

            private final Long notExistedResourceId = 10L;

            @BeforeEach
            void setUp() {
                given(repository.findById(notExistedResourceId)).willThrow(ResourceNotFoundException.class);
                given(repository.findById(null)).willThrow(ResourceNotFoundException.class);
            }

            @Test
            @DisplayName("자원을 찾을 수 없다는 예외를 던진다")
            void It_ThrowException(){
                assertThatThrownBy(() -> service.findById(null))
                        .isInstanceOf(ResourceNotFoundException.class);

                verify(repository).findById(null);

                assertThatThrownBy(() -> service.findById(notExistedResourceId))
                        .isInstanceOf(ResourceNotFoundException.class);

                verify(repository).findById(notExistedResourceId);
            }
        }

        @Nested
        @DisplayName("id에 해당하는 자원이 있다면")
        class Context_ExistedResource{

            private final Long existedResourceId = 1L;
            private Product product;

            @BeforeEach
            void setUp() {
                product = newProduct(existedResourceId);
                given(repository.findById(existedResourceId)).willReturn(Optional.ofNullable(product));
            }

            @Test
            @DisplayName("자원을 반환한다")
            void It_ReturnResource(){
                assertThat(service.findById(existedResourceId)).isEqualTo(product);
            }
        }
    }


}
