package com.codesoom.assignment.product.controller;

import com.codesoom.assignment.ProvideInvalidProductArguments;
import com.codesoom.assignment.product.application.ProductService;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import com.codesoom.assignment.product.exception.ProductInvalidFieldException;
import com.codesoom.assignment.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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


@DisplayName("ProductController 컨트롤러 테스트")
@DataJpaTest
class ProductControllerJpaTest {

    private ProductController controller;
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    private Product product = Product.of(NAME, MAKER, PRICE, IMAGE_URL);
    private Product otherProduct = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);

        controller = new ProductController(productService);

        setUpFixture();
    }

    private void setUpFixture() {

    }

    @DisplayName("상품 정보가 없더라도 전체 목록을 조회할 수 있습니다.")
    @Test
    void findAllWithoutProduct() {
        assertThat(controller.findAll()).isEmpty();

    }

    @DisplayName("상품 정보가 있을 경우에도 전체 목록을 조회할 수 있습니다.")
    @Test
    void findAllWithProduct() {
        assertThat(controller.findAll()).isEmpty();

        controller.create(product);

        assertThat(controller.findAll()).hasSize(1);
    }

    @DisplayName("존재하는 식별자를 이용해 상품 상세정보를 조회할 수 있습니다.")
    @Test
    void findByExistsId() {
        final Product savedProduct = controller.create(controller.create(product));
        final Product foundProduct = controller.findById(savedProduct.getId());

        assertThat(foundProduct).isEqualTo(savedProduct);
        assertThat(foundProduct.getName()).isEqualTo(savedProduct.getName());

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
        assertThat(savedProduct.getName()).isEqualTo(product.getName());
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
        final Product savedProduct = controller.create(product);
        final Product foundProduct = controller.findById(savedProduct.getId());

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
        final Product savedProduct = controller.create(product);
        final Product foundProduct = controller.findById(savedProduct.getId());

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
        final Product savedProduct = controller.create(product);
        controller.deleteProduct(savedProduct.getId());

        assertThatThrownBy(()-> controller.deleteProduct(savedProduct.getId()))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("존재하지 않는 식별자의 상품 정보를 삭제할 수 없습니다.")
    @Test
    void deleteNotExistsId() {
        assertThatThrownBy(()-> controller.deleteProduct(100L))
                .isInstanceOf(ProductNotFoundException.class);
    }

}
