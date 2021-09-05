package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductModel;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.codesoom.assignment.constant.ProductTestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class ProductServiceTest {

    private ProductService productService;
    private ProductModel productModel;
    private ProductModel changeProductModel;
    private Long saveProductId;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productService = new ProductService(productRepository);
        productModel = new ProductModel(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL);
        saveProductId = saveProduct();
        changeProductModel = new ProductModel(saveProductId, CHANGE_NAME, CHANGE_MAKER, CHANGE_PRICE, CHANGE_IMAGE_URL);
    }

    private Long saveProduct() {
        return productRepository.save(new Product(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL)).id();
    }

    @Test
    @DisplayName("고양이 장난감을 생성 하여 반환한다.")
    void createCatToy() {
        // when
        ProductModel product = productService.createProduct(productModel);

        // then
        assertThat(product.name()).isEqualTo(TOY_NAME);
        assertThat(product.maker()).isEqualTo(TOY_MAKER);

    }

    @Test
    @DisplayName("고양이 장난감 정보를 가져온다")
    void findCatToy() {
        // given
        ProductModel product = productService.createProduct(productModel);

        // when
        ProductModel selectToy = productService.selectProduct(product.id());

        // then
        assertThat(selectToy.name()).isEqualTo(TOY_NAME);
        assertThat(selectToy.maker()).isEqualTo(TOY_MAKER);

    }

    @Test
    @DisplayName("고양이 장난감을 검색 - 검색 실패")
    void selectCatToyNotExists() {
        // then
        assertThatThrownBy(() -> productService.selectProduct(NOT_EXISTS_ID))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("고양이 장난감을 수정")
    void modifyCatToy() {
        // when
        ProductModel modifyCatToy = productService.modifyProduct(changeProductModel);

        // then
        assertThat(modifyCatToy.name()).isEqualTo(CHANGE_NAME);
        assertThat(modifyCatToy.maker()).isEqualTo(CHANGE_MAKER);
    }

    @Test
    @DisplayName("고양이 장난감을 삭제")
    void deleteCatToy() {
        // when
        productService.deleteCatToy(saveProductId);

        // then
        assertThatThrownBy(() -> productService.selectProduct(saveProductId))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
