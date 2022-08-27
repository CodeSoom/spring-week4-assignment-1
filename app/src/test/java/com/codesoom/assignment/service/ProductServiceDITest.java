package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ResourceNotFoundException;
import com.codesoom.assignment.repository.ProductJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class ProductServiceDITest {

    private ProductService service;
    private ProductSearchService searchService;

    @Autowired
    private ProductJPARepository repository;

    private final Long SIZE = 3L;
    private final String TITLE = "proudct";
    private final String MAKER = "maker";

    @BeforeEach
    void setUp() {
        searchService = new ProductSearchService(repository);
        service = new ProductService(repository);
    }

    Product newProduct(long number){
        return new Product(number , TITLE + number , MAKER + number , (int) number , null);
    }

    void repositoryClear(){
        for(Product product : searchService.findAll()){
            repository.deleteById(product.getId());
        }
    }

    List<Product> newProducts(long size){
        List<Product> prodcucts = new ArrayList<>();
        for(long l = 1 ; l <= size ; l++){
            Product product = newProduct(l);
            prodcucts.add(product);
        }
        return prodcucts;
    }

    void saveProduct(Product product){
        repository.save(product);
    }

    void saveAllProduct(List<Product> products){
        repository.saveAll(products);
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
                saveAllProduct(products);
            }

            @Test
            @DisplayName("상품들을 List로 반환한다")
            void It_ReturnList(){
                assertThat(searchService.findAll()).hasSize(products.size());
            }
        }

        @Nested
        @DisplayName("상품들이 없다면")
        class Context_NotExistedProduct{

            @BeforeEach
            void setUp() {
                repositoryClear();
            }

            @Test
            @DisplayName("빈 List를 반환한다")
            void It_ReturnEmptyList(){
                assertThat(searchService.findAll()).isEqualTo(new ArrayList<>());
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_FindById{

        @Nested
        @DisplayName("{id}에 해당하는 자원이 없다면")
        class Context_NullIdAndNotExistedResource{

            private final Long notExistedResourceId = 10L;

            @BeforeEach
            void setUp() {
                repositoryClear();
            }

            @Test
            @DisplayName("자원을 찾을 수 없다는 예외를 던진다")
            void It_ThrowException(){
                // id가 null일 상황은 없다
                assertThatThrownBy(() -> searchService.findById(notExistedResourceId))
                        .isInstanceOf(ResourceNotFoundException.class);
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
                saveProduct(product);
            }

            @Test
            @DisplayName("자원을 반환한다")
            void It_ReturnResource(){
                assertThat(searchService.findById(existedResourceId)).isEqualTo(product);
            }
        }
    }

    @Nested
    @DisplayName("save 메소드는")
    class Describe_Save{

        @Nested
        @DisplayName("엔티티가 null이 아니라면")
        class Context_NotNullEntity{

            private Product product;

            @BeforeEach
            void setUp() {
                product = newProduct(1);
                repository.save(product);
            }

            @Test
            @DisplayName("저장한다")
            void It_SaveResource(){
                assertThat(service.save(product)).isEqualTo(product);
            }
        }
    }

    @Nested
    @DisplayName("update메서드는")
    class Describe_Update{

        @Nested
        @DisplayName("{id}에 해당하는 자원이 있고 수정할 정보가 있다면")
        class Context_ExistedIdAndResoruce{

            private Product beforeProduct;
            private Product afterProduct;
            private final int beforeId = 1;
            private final int afterId = 2;

            @BeforeEach
            void setUp() {
                repositoryClear();
                beforeProduct = newProduct(beforeId);
                afterProduct = newProduct(afterId);
                service.save(beforeProduct);
            }

            @Test
            @DisplayName("수정한다")
            void It_Update(){
                Product updateProduct = service.update((long) beforeId , afterProduct);
                assertThat(updateProduct.getId()).isEqualTo(beforeId);
                assertThat(updateProduct.getName()).isEqualTo(afterProduct.getName());
                assertThat(updateProduct.getMaker()).isEqualTo(afterProduct.getMaker());
                assertThat(updateProduct.getPrice()).isEqualTo(afterProduct.getPrice());
                assertThat(updateProduct.getFileName()).isEqualTo(afterProduct.getFileName());
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메서드는")
    class Describe_DeleteById{

        @Nested
        @DisplayName("{id}에 해당하는 자원이 있다면")
        class Context_ExistedId{

            private final int id = 1;
            private Product product;

            @BeforeEach
            void setUp() {
                repositoryClear();
                product = newProduct(id);
                repository.save(product);
            }

            @Test
            @DisplayName("삭제한다")
            void It_Delete(){
                assertThat(searchService.findAll()).hasSize(1);
                service.deleteById((long)id);
                assertThat(searchService.findAll()).hasSize(0);
            }
        }
    }
}
