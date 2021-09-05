package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.codesoom.assignment.constant.ProductTestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product catToy;
    private ProductModel changeProductModel;
    private Long id;

    @BeforeEach
    void setup(){
        catToy = new Product(TOY_NAME, TOY_MAKER, PRICE, IMAGE_URL);
        changeProductModel = new ProductModel(id, CHANGE_NAME, CHANGE_MAKER, CHANGE_PRICE, CHANGE_IMAGE_URL);
        id = saveProductId();
    }

    private Long saveProductId() {
        return productRepository.save(catToy).id();
    }

    @Test
    @DisplayName("고양이 장난감을 생성 하여 반환한다.")
    void createCatToy() {
        // when
        Product saveCatToy = productRepository.save(catToy);

        // then
        assertThat(productRepository.findById(saveCatToy.id()).isPresent()).isTrue();

    }

    @Test
    @DisplayName("고양이 장난감 리스트를 검색하여 반환한다.")
    void selectCatToyList() {
        // when
        List<Product> catToys = productRepository.findAll();

        // then
        assertThat(catToys).isNotEmpty();
    }

    @Test
    @DisplayName("고양이 장난감을 검색하여 반환한다.")
    void selectCatToy() {
        // when
        Product selectCatToy = productRepository.findById(id).get();

        // then
        assertThat(selectCatToy).isEqualTo(catToy);
    }

    @Test
    @DisplayName("고양이 장난감을 수정하여 반환한다.")
    void modifyCatToy() {
        // given
        Product selectCatToy = productRepository.findById(id).get();

        // when
        selectCatToy.changeProduct(changeProductModel);
        productRepository.save(selectCatToy);

        // then
        assertThat(selectCatToy.name()).isEqualTo(CHANGE_NAME);
        assertThat(selectCatToy.maker()).isEqualTo(CHANGE_MAKER);
    }

    @Test
    @DisplayName("고양이 장난감을 삭제")
    void deleteCatToy() {
        // when
        productRepository.deleteById(id);

        // then
        assertThat(productRepository.findById(id)).isEmpty();
    }
}
