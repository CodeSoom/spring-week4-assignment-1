package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.exception.BadRequestException;
import com.codesoom.assignment.exception.ResourceNotFoundException;
import com.codesoom.assignment.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ProductController 테스트")
class ProductControllerTest {

    private ProductController controller;
    private ProductService service;

    private final Long SIZE = 3L;
    private final String TITLE = "proudct";
    private final String MAKER = "maker";

    @BeforeEach
    void setUp() {
        service = mock(ProductService.class);
        controller = new ProductController(service);
    }

    ProductDTO newDTO(long number){
        return new ProductDTO(TITLE + number , MAKER + number , (int) number , null);
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
    @DisplayName("findAllProduct 메소드는")
    class Describe_FindAllProduct{

        @Nested
        @DisplayName("상품들이 있다면")
        class Context_ExistedProducts{

            private List<Product> products;

            @BeforeEach
            void setUp(){
                products = newProducts(SIZE);
                given(service.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("List를 반환한다")
            void It_ReturnList(){
                assertThat(controller.findAllProduct()).hasSize(Math.toIntExact(SIZE));

                verify(service).findAll();
            }
        }

        @Nested
        @DisplayName("상품들이 없다면")
        class Context_NotExistedProduct{

            private final List<Product> EmptyList = new ArrayList<>();

            @BeforeEach
            void setUp() {
                given(service.findAll()).willReturn(EmptyList);
            }

            @Test
            @DisplayName("빈 List를 반환한다")
            void It_ReturnJSON() {
                assertThat(controller.findAllProduct()).isEqualTo(EmptyList);

                verify(service).findAll();
            }
        }
    }

    @Nested
    @DisplayName("findProduct 메소드는")
    class Describe_FindProduct{

        @Nested
        @DisplayName("{id}가 null이거나 해당하는 자원이 없다면")
        class Context_NullIdAndNotExistedResource{

            private final Long notExistedResourceId = 10L;

            @BeforeEach
            void setUp() {
                given(service.findById(notExistedResourceId)).willThrow(ResourceNotFoundException.class);
                given(service.findById(null)).willThrow(ResourceNotFoundException.class);
            }

            @Test
            @DisplayName("자원을 찾을 수 없다는 예외를 던진다")
            void It_ThrowException(){
                assertThatThrownBy(() -> controller.findProduct(null))
                        .isInstanceOf(ResourceNotFoundException.class);

                verify(service).findById(null);

                assertThatThrownBy(() -> controller.findProduct(notExistedResourceId))
                        .isInstanceOf(ResourceNotFoundException.class);

                verify(service).findById(notExistedResourceId);
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
                given(service.findById(existedResourceId)).willReturn(product);
            }

            @Test
            @DisplayName("자원을 반환한다")
            void It_ReturnResource(){
                assertThat(service.findById(existedResourceId)).isEqualTo(product);

                verify(service).findById(existedResourceId);
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_CreateProduct{

        @Nested
        @DisplayName("DTO가 null이라면")
        class Context_NullDTO{

            @BeforeEach
            void setUp() {
                given(service.save(null)).willThrow(BadRequestException.class);
            }

            @Test
            @DisplayName("요청이 잘못됐다는 예외를 던진다")
            void It_ThrowExceptipon(){
                controller.findProduct(null);
            }
        }

        @Nested
        @DisplayName("DTO가 null이 아니라면")
        class Context_NotNullDTO{

            private ProductDTO dto = newDTO(1);
            private Product entity;

            @BeforeEach
            void setUp() {
                entity = dto.toProduct();
                given(service.save(entity)).willReturn(entity);
            }

            @Test
            @DisplayName("DTO를 엔티티로 변환하고 저장한다")
            void It_DTOtoEntityAndSave(){
                assertThat(dto.getName()).isEqualTo(entity.getName());
                assertThat(dto.getMaker()).isEqualTo(entity.getMaker());
                assertThat(dto.getPrice()).isEqualTo(entity.getPrice());
                assertThat(dto.getFileName()).isEqualTo(entity.getFileName());

                assertThat(controller.createProduct(dto).getName()).isEqualTo(entity.getName());
            }
        }
    }
}
