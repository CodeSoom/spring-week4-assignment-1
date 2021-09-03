package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import com.codesoom.assignment.product.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.codesoom.assignment.Constant.IMAGE_URL;
import static com.codesoom.assignment.Constant.MAKER;
import static com.codesoom.assignment.Constant.NAME;
import static com.codesoom.assignment.Constant.PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ProductService 서비스 테스트")
@DataJpaTest
class ProductServiceJpaTest {

    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);

        setUpFixtures();
    }

    private void setUpFixtures() {
        product = productRepository.save(Product.of(NAME, MAKER, PRICE, IMAGE_URL));
    }

    @DisplayName("제품들이 존재하는 목록을 조회할 수 있습니다.")
    @Test
    void findAllExistsProduct() {
        List<Product> products = productService.findAll();

        assertThat(products).hasSize(1);
    }


    @DisplayName("식별자를 통해 제품을 찾을 수 있습니다.")
    @Test
    void findProductById() {
        assertThat(productService.findById(product.getId())).isEqualTo(product);

    }

    @DisplayName("존재하지 않는 식별자를 통해 상품을 찾을 경우 예외가 발생합니다.")
    @Test
    void findProductByNotExistsId() {
        assertThatThrownBy(() -> productService.findById(100L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("새로운 상품을 등록할 수 있습니다.")
    @Test
    void saveProduct() {
        final Product product = Product.of("Other", MAKER, PRICE, IMAGE_URL);

        assertThat(product.getId()).isNull();

        Product createdProduct = productService.save(product);

        assertThat(createdProduct.getId()).isNotNull();
        assertThat(createdProduct.getMaker()).isEqualTo(MAKER);
        assertThat(createdProduct.getPrice()).isEqualTo(PRICE);
        assertThat(createdProduct.getImageUrl()).isEqualTo(IMAGE_URL);
    }

    @DisplayName("상품의 정보를 수정할 수 있습니다. ")
    @Test
    void updateProduct() {
        final Product newProduct = Product.of("Other", "OtherMaker", 3000L, "OtherUrl");
        Product updatedProduct = productService.updateProduct(product.getId(), newProduct);

        assertThat(updatedProduct.getName()).isEqualTo(newProduct.getName());
        assertThat(updatedProduct.getMaker()).isEqualTo(newProduct.getMaker());
        assertThat(updatedProduct.getPrice()).isEqualTo(newProduct.getPrice());
        assertThat(updatedProduct.getImageUrl()).isEqualTo(newProduct.getImageUrl());

    }

    @DisplayName("존재하지 않는 식별자의 상품을 수정하려하면 예외가 발생합니다.")
    @Test
    void updateProductNotExistsId() {
        final Product newProduct = Product.of("Other", "OtherMaker", 3000L, "OtherUrl");

        assertThatThrownBy(() -> productService.updateProduct(100L, newProduct))
                .isInstanceOf(ProductNotFoundException.class);

    }

    @DisplayName("상품을 삭제할 수 있습니다.")
    @Test
    void deleteProduct() {
        final Product foundProduct = productService.findById(product.getId());
        productService.deleteProduct(foundProduct);

        assertThatThrownBy(() -> productService.findById(foundProduct.getId()))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage(String.format(ProductNotFoundException.DEFAULT_MESSAGE, foundProduct.getId()));
    }


}
