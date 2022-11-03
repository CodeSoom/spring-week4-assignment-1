package com.codesoom.assignment.products.controllers;


import com.codesoom.assignment.exception.products.ProductNotFoundException;
import com.codesoom.assignment.products.application.ProductService;
import com.codesoom.assignment.products.domain.FakeProductRepository;
import com.codesoom.assignment.products.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.codesoom.assignment.support.ProductFieldFixture.TEST_NOT_EXIST;
import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("ProductController 유닛 테스트")
class ProductControllerTest {
    private final ProductController productController;
    private final ProductService productService;
    private final FakeProductRepository fakeProductRepository;

    @Autowired
    ProductControllerTest(ProductController productController, ProductService productService, FakeProductRepository fakeProductRepository) {
        this.productController = productController;
        this.productService = productService;
        this.fakeProductRepository = fakeProductRepository;
    }

    // Service의 delete 기능이 개발되기 전까지 대체합니다.
    @BeforeEach
    void setUpDeleteFixture() {
        fakeProductRepository.deleteAllInBatch();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class list_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_없을_때 {
            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_empty_list() {
                List<Product> products = productController.list();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_있을_때 {
            @Test
            @DisplayName("비어있지 않은 리스트를 리턴한다")
            void it_returns_list() {
                productService.createProduct(TOY_1.요청_데이터_생성());

                assertThat(productController.list()).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class detail_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> productController.detail(TEST_NOT_EXIST.ID()))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @Test
            @DisplayName("해당 id의 장난감 정보를 리턴한다")
            void it_returns_product() {
                Product productSource = productService.createProduct(TOY_1.요청_데이터_생성());

                Product product = productController.detail(productSource.getId());

                assertThat(product).isNotNull();
                assertThat(product.getName()).isEqualTo(TOY_1.NAME());
                assertThat(product.getMaker()).isEqualTo(TOY_1.MAKER());
                assertThat(product.getPrice()).isEqualTo(TOY_1.PRICE());
                assertThat(product.getImgUrl()).isEqualTo(TOY_1.IMAGE());
            }
        }
    }
}
