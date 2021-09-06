package com.codesoom.assignment.product.controller;

import com.codesoom.assignment.ProvideInvalidProductArguments;
import com.codesoom.assignment.product.application.ProductService;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.exception.ProductInvalidFieldException;
import com.codesoom.assignment.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codesoom.assignment.Constant.IMAGE_URL;
import static com.codesoom.assignment.Constant.MAKER;
import static com.codesoom.assignment.Constant.NAME;
import static com.codesoom.assignment.Constant.OTHER_IMAGE_URL;
import static com.codesoom.assignment.Constant.OTHER_MAKER;
import static com.codesoom.assignment.Constant.OTHER_NAME;
import static com.codesoom.assignment.Constant.OTHER_PRICE;
import static com.codesoom.assignment.Constant.PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@DisplayName("ProductController 컨트롤러 테스트")
class ProductControllerTest {

    private ProductController controller;
    private ProductService productService;

    private Product product;
    private Product otherProduct;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);

        controller = new ProductController(productService);

        setUpFixture();
    }

    private void setUpFixture() {
        product = Product.of(NAME, MAKER, PRICE, IMAGE_URL);
        ReflectionTestUtils.setField(product, "id", 1L);

        otherProduct = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);

        given(productService.findAll())
                .willReturn(new ArrayList<>())
                .willReturn(Collections.singletonList(product));

        given(productService.findById(1L)).willReturn(product);

        given(productService.findById(100L)).willThrow(new ProductNotFoundException(100L));

        given(productService.save(any(Product.class))).will(invocation -> {
            final Product args = invocation.getArgument(0);

            if (!args.equals(product)) {
                throw new ProductInvalidFieldException();
            }

            ReflectionTestUtils.setField(args, "id", 1L);
            return args;
        });

        given(productService.updateProduct(eq(1L), any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(1, Product.class);
            if (!product.equals(otherProduct)) {
                throw new ProductInvalidFieldException();
            }
            return otherProduct;
        });

        given(productService.updateProduct(eq(100L), any(Product.class)))
                .willThrow(new ProductNotFoundException(100L));
    }

    @DisplayName("상품 정보가 없더라도 전체 목록을 조회할 수 있습니다.")
    @Test
    void findAllWithoutProduct() {
        assertThat(controller.findAll()).isEmpty();

        verify(productService).findAll();
    }

    @DisplayName("상품 정보가 있을 경우에도 전체 목록을 조회할 수 있습니다.")
    @Test
    void findAllWithProduct() {
        assertThat(controller.findAll()).isEmpty();

        controller.create(product);
        verify(productService).save(product);

        assertThat(controller.findAll()).hasSize(1);
        verify(productService, atLeast(2)).findAll();
    }

    @DisplayName("존재하는 식별자를 이용해 상품 상세정보를 조회할 수 있습니다.")
    @Test
    void findByExistsId() {
        final Product foundProduct = controller.findById(1L);

        assertThat(foundProduct).isEqualTo(product);
        assertThat(foundProduct.getName()).isEqualTo(product.getName());

        verify(productService).findById(1L);
    }

    @DisplayName("존재하지 않는 식별자를 이용해 상품 상세정보를 조회할 경우 예외가 발생합니다.")
    @Test
    void findByNotExistsId() {
        assertThatThrownBy(() -> controller.findById(100L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("새로운 상품을 생성할 수 있습니다.")
    @Test
    void createWithProduct() {
        final Product savedProduct = controller.create(product);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct).isEqualTo(product);
    }

    @DisplayName("유효하지 못한 상품 정보를 생성하려 하면 예외가 발생합니다.")
    @ParameterizedTest
    @ArgumentsSource(ProvideInvalidProductArguments.class)
    void createInvalidProduct(List<Product> products) {
        for (Product product : products) {
            assertThatThrownBy(() -> controller.create(product))
                    .isInstanceOf(ProductInvalidFieldException.class);
        }
    }

    @DisplayName("존재하는 식별자의 상품 정보를 수정할 수 있습니다.")
    @Test
    void updateExistsIdWithValidProduct() {
        final Product foundProduct = controller.findById(1L);

        final Product updatedProduct = controller.updateProduct(foundProduct.getId(),
                Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL));

        assertThat(updatedProduct.getName()).isEqualTo(OTHER_NAME);
        assertThat(updatedProduct.getMaker()).isEqualTo(OTHER_MAKER);
        assertThat(updatedProduct.getPrice()).isEqualTo(OTHER_PRICE);
        assertThat(updatedProduct.getImageUrl()).isEqualTo(OTHER_IMAGE_URL);


    }

    @DisplayName("존재하는 식별자의 상품 정보를 잘 못된 정보로 수정하려 하면 예외가 발생합니다.")
    @ParameterizedTest
    @ArgumentsSource(ProvideInvalidProductArguments.class)
    void updateExistsIdWithInvalidProduct(List<Product> products) {
        final Product foundProduct = controller.findById(1L);

        for (Product product : products) {
            assertThatThrownBy(() -> controller.updateProduct(foundProduct.getId(), product))
                    .isInstanceOf(ProductInvalidFieldException.class);
        }

    }

    @DisplayName("존재하지 않는 식별자의 상품 정보를 수정할 수 없습니다.")
    @Test
    void updateNotExistsId() {
        assertThatThrownBy(() -> controller.updateProduct(100L, otherProduct))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("존재하는 식별자의 상품 정보를 삭제할 수 있습니다.")
    @Test
    void deleteExistsId() {
        controller.deleteProduct(1L);

        verify(productService).findById(1L);
        verify(productService).deleteProduct(product);
    }

    @DisplayName("존재하지 않는 식별자의 상품 정보를 삭제할 수 없습니다.")
    @Test
    void deleteNotExistsId() {
        assertThatThrownBy(()-> controller.deleteProduct(100L))
                .isInstanceOf(ProductNotFoundException.class);
    }

}
