package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@DisplayName("ProductService Mock 테스트")
@ExtendWith(MockitoExtension.class)
public class ProductServiceMockTest {

    @DisplayName("ProductCreateServiceImpl 클래스")
    @Nested
    class ProductSaveServiceTest {

        @InjectMocks
        private ProductSaveServiceImpl service;

        @Mock
        private ProductRepository repository;

        @DisplayName("장난감을 성공적으로 등록한다.")
        @Test
        void createToyTest() {
            final ProductDto productDto
                    = new ProductDto("name", "maker", BigDecimal.valueOf(2000), "image");

            service.saveProduct(productDto);

            verify(repository).save(any(ProductSaveRequest.class));
        }
    }

    @DisplayName("ProductReadServiceImpl 클래스")
    @Nested
    class ProductReadServiceTest {

        @InjectMocks
        private ProductReadServiceImpl service;

        @Mock
        private ProductRepository repository;

        private final Long EXIST_ID = 1L;
        private final Long NOT_EXIST_ID = 100L;

        private final Product SAVED_PRODUCT_1
                = new Product("쥐돌이", "캣이즈락스타", BigDecimal.valueOf(4000), "");
        private final Product SAVED_PRODUCT_2
                = new Product("곰돌이", "캣이즈락스타", BigDecimal.valueOf(4000), "");

        private final List<Product> PRODUCTS = List.of(SAVED_PRODUCT_1, SAVED_PRODUCT_2);

        @DisplayName("findAll은 모든 장난감을 반환한다.")
        @Test
        void findAllTest() {
            given(repository.findAll()).willReturn(PRODUCTS);

            List<Product> products = service.findAll();

            verify(repository).findAll();
            assertThat(products).isNotEmpty();
            assertThat(products).hasSize(2);
        }

        @DisplayName("findById는 id에 해당하는 장난감을 반환한다.")
        @Test
        void findByIdTest() {
            given(repository.findById(eq(EXIST_ID))).willReturn(Optional.of(SAVED_PRODUCT_1));

            Product product = service.findById(EXIST_ID);

            assertThat(product).isNotNull();
            assertThat(product.getName()).isEqualTo(SAVED_PRODUCT_1.getName());
        }

        @DisplayName("findById는 존재하지 않는 id로 조회할 경우 예외를 던진다.")
        @Test
        void findByNotExistIdTest() {
            given(repository.findById(eq(NOT_EXIST_ID))).willReturn(Optional.empty());

            assertThatThrownBy(() -> service.findById(NOT_EXIST_ID))
                    .isInstanceOf(ProductNotFoundException.class);
        }

    }

    @DisplayName("ProductUpdateServiceImpl 클래스")
    @Nested
    class ProductUpdateServiceTest {

        @InjectMocks
        private ProductUpdateServiceImpl service;

        @Mock
        private ProductRepository repository;

        private final Long EXIST_ID = 1L;
        private final Long NOT_EXIST_ID = 100L;

        private final Product OLD_PRODUCT_ENTITY
                = new Product("쥐돌이", "캣이즈락스타", BigDecimal.valueOf(4000), "");
        private final String UPDATE_NAME = "꿈돌이";

        @DisplayName("장난감을 성공적으로 수정한다.")
        @Test
        void updateTest() {
            given(repository.findById(eq(EXIST_ID))).willReturn(Optional.of(OLD_PRODUCT_ENTITY));
            final ProductDto productDto = new ProductDto(UPDATE_NAME, "", BigDecimal.valueOf(3000), "");

            final Product product = service.update(EXIST_ID, productDto);
            assertThat(product).isInstanceOf(Product.class);
            assertThat(product.getName()).isEqualTo(UPDATE_NAME);
        }

        @DisplayName("존재하지 않는 장난감을 수정할 경우 예외를 던진다..")
        @Test
        void updateWithNotExistIdTest() {
            given(repository.findById(eq(NOT_EXIST_ID))).willReturn(Optional.empty());

            final ProductDto productDto = new ProductDto("", "", BigDecimal.valueOf(3000), "");
            assertThatThrownBy(()->service.update(NOT_EXIST_ID, productDto))
                    .isInstanceOf(ProductNotFoundException.class);
        }

    }

    @DisplayName("ProductDeleteServiceImpl 클래스")
    @Nested
    class ProductDeleteServiceTest {

        @InjectMocks
        private ProductSafeDeleteService service;

        @Mock
        private ProductRepository repository;

        private final Long EXIST_ID = 1L;
        private final Long NOT_EXIST_ID = 100L;

        private final Product PRODUCT
                = new Product("쥐돌이", "캣이즈락스타", BigDecimal.valueOf(4000), "");

        @DisplayName("delete는 id에 해당하는 상품을 삭제한다.")
        @Test
        void deleteTest() {
            given(repository.findById(eq(EXIST_ID))).willReturn(Optional.of(PRODUCT));

            service.deleteById(EXIST_ID);
            verify(repository).delete(eq(PRODUCT));
        }

        @DisplayName("delete는 존재하지 않는 상품을 삭제할 경우 예외를 던진다.")
        @Test
        void deleteWithNotExistId() {
            given(repository.findById(eq(NOT_EXIST_ID))).willReturn(Optional.empty());

            assertThatThrownBy(() -> service.deleteById(NOT_EXIST_ID))
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }

}
