package com.codesoom.assignment.application;

import com.codesoom.assignment.common.exceptions.ProductNotFoundException;
import com.codesoom.assignment.models.domain.Product;
import com.codesoom.assignment.models.domain.ProductRepository;
import com.codesoom.assignment.models.dto.ProductDto;
import com.codesoom.assignment.models.dto.ProductInfoDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback
@DisplayName("ProductService에 대한 단위테스트")
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private final Long VALID_ID = 1L;
    private final Long INVALID_ID = 999L;
    private final String UPDATE_SUFIX = "UPDATED";
    private final String IMAGE_URL = "http://localhost:8080/snake";

    @BeforeEach
    void setUp() {
        Product toy1 = new Product(1L, "Test Toy1", "Test Maker1", 1000, null);
        Product toy2 = new Product(2L, "Test Toy2", "Test Maker2", 2000, null);
        productRepository.save(toy1);
        productRepository.save(toy2);
    }

    @Nested
    @DisplayName("getProductList 메소드는")
    class Describe_getProductList {

        @Nested
        @DisplayName("만약 두개의 상품이 등록되어있다면")
        class Context_contain_two_product {

            @Test
            @DisplayName("두개의 상품이 들어있는 목록을 반환합니다.")
            void it_return_list_contain_two_product() {

                List<ProductInfoDto> productList = productService.getProductList();
                Assertions.assertThat(productList).hasSize(2);

            }

        }

    }

    @Nested
    @DisplayName("getProductOne 메소드는")
    class Describe_getProductOne {

        private Long foundId; // 조회 할 상품 ID

        @Nested
        @DisplayName("목록에 없는 상품을 조회할 경우")
        class Context_invalid_id {

            @BeforeEach
            void setUp() {
                foundId = INVALID_ID;
            }

            @Test
            @DisplayName("ProductNotFound 예외를 던집니다.")
            void it_throw_product_not_found_exception() {

                Assertions.assertThatThrownBy(() -> productService.getProductOne(foundId))
                        .isInstanceOf(ProductNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("목록에 있는 상품을 조회할 경우")
        class Context_valid_id {

            @BeforeEach
            void setUp() {
                foundId = VALID_ID;
            }

            @Test
            @DisplayName("조회한 상품을 반환합니다.")
            void it_return_found_product() {

                ProductInfoDto foundProduct = productService.getProductOne(foundId);
                Assertions.assertThat(foundProduct.getName()).isEqualTo("Test Toy1");
                Assertions.assertThat(foundProduct.getMaker()).isEqualTo("Test Maker1");
                Assertions.assertThat(foundProduct.getPrice()).isEqualTo(1000);

            }

        }

    }

    @Nested
    @DisplayName("saveProduct 메소드는")
    class Describe_saveProduct {

        @Nested
        @DisplayName("만약 새로운 상품을 등록한다면")
        class Context_save_new_product{

            private ProductInfoDto saveParam;

            private Long createdId; // 생성된 상품 ID

            @BeforeEach
            void setUp() {
                createdId = 3L;

                saveParam = new ProductInfoDto();
                saveParam.setName("New Test Toy");
                saveParam.setMaker("New Test Toy Maker");
                saveParam.setPrice(4000);
                saveParam.setImageUrl(IMAGE_URL);
            }

            @Test
            @DisplayName("생성된 상품을 반환합니다.")
            void it_return_created_product() {
                ProductDto createdProduct = productService.saveProduct(saveParam);

                Assertions.assertThat(createdProduct.getName()).isEqualTo(saveParam.getName());
                Assertions.assertThat(createdProduct.getMaker()).isEqualTo(saveParam.getMaker());
                Assertions.assertThat(createdProduct.getPrice()).isEqualTo(saveParam.getPrice());
                Assertions.assertThat(createdProduct.getImageUrl()).isEqualTo(saveParam.getImageUrl());

                // 새로운 상품이 생성되었음을 확인
                Assertions.assertThat(productService.getProductOne(createdId)).isNotNull();
            }

        }

    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {

        private Long updateId; // 수정 할 상품 ID

        @Nested
        @DisplayName("만약 상품 목록에 없는 상품을 수정한다면")
        class Context_invalid_id {

            private ProductInfoDto updateParam;

            @BeforeEach
            void setUp() {

                updateId = INVALID_ID;

                updateParam = new ProductInfoDto();
                updateParam.setName("Test Name " + UPDATE_SUFIX);
                updateParam.setMaker("Test Maker" + UPDATE_SUFIX);
                updateParam.setPrice(5000);
                updateParam.setImageUrl(IMAGE_URL);

            }

            @Test
            @DisplayName("ProductNotFound 예외를 던집니다.")
            void it_throw_product_not_found_exception() {

                Assertions.assertThatThrownBy(() -> productService.updateProduct(updateId, updateParam))
                        .isInstanceOf(ProductNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("만약 상품 목록에 있는 상품을 수정한다면")
        class Context_valid_id {

            private ProductInfoDto updateParam;

            @BeforeEach
            void setUp() {

                updateId = VALID_ID;

                updateParam = new ProductInfoDto();
                updateParam.setName("Test Name " + UPDATE_SUFIX);
                updateParam.setMaker("Test Maker" + UPDATE_SUFIX);
                updateParam.setPrice(5000);
                updateParam.setImageUrl(IMAGE_URL);

            }

            @Test
            @DisplayName("수정된 상품을 반환합니다.")
            void it_return_updated_product() {
                ProductInfoDto updatedProduct = productService.updateProduct(updateId, updateParam);

                Assertions.assertThat(updatedProduct.getName()).isEqualTo(updateParam.getName());
                Assertions.assertThat(updatedProduct.getMaker()).isEqualTo(updateParam.getMaker());
                Assertions.assertThat(updatedProduct.getPrice()).isEqualTo(updateParam.getPrice());
                Assertions.assertThat(updatedProduct.getImageUrl()).isEqualTo(updateParam.getImageUrl());

                // 해당 상품이 수정되었음을 확인
                ProductInfoDto foundProduct = productService.getProductOne(updateId);
                Assertions.assertThat(foundProduct.getName()).isEqualTo(updatedProduct.getName());
                Assertions.assertThat(foundProduct.getMaker()).isEqualTo(updatedProduct.getMaker());
                Assertions.assertThat(foundProduct.getPrice()).isEqualTo(updatedProduct.getPrice());
                Assertions.assertThat(foundProduct.getImageUrl()).isEqualTo(updatedProduct.getImageUrl());

            }

        }

    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct{

        private Long deleteId; // 삭제할 상품 ID

        @Nested
        @DisplayName("만약 상품 목록에 없는 상품을 삭제한다면")
        class Context_invalid_id {

            @BeforeEach
            void setUp() {
                deleteId = INVALID_ID;
            }

            @Test
            @DisplayName("ProductNotFound 예외를 던집니다.")
            void it_throw_product_not_found_exception() {

                Assertions.assertThatThrownBy(() -> productService.deleteProduct(deleteId))
                        .isInstanceOf(ProductNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("만약 상품 목록에 있는 상품을 삭제한다면")
        class Context_valid_id {

            @BeforeEach
            void setUp() {
                deleteId = VALID_ID;
            }

            @Test
            @DisplayName("해당 상품을 삭제하고 아무것도 반환하지 않습니다.")
            void it_delete_product_return_void() {
                productService.deleteProduct(deleteId);

                // 상품이 삭제됨을 확인
                Assertions.assertThatThrownBy( () -> productService.getProductOne(deleteId))
                        .isInstanceOf(ProductNotFoundException.class);
            }

        }

    }

}
