package com.codesoom.assignment.application;

import com.codesoom.assignment.application.command.ProductCommandService;
import com.codesoom.assignment.application.command.ProductCommandServiceImpl;
import com.codesoom.assignment.common.ProductFactory;
import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductCommand;
import com.codesoom.assignment.domain.ProductInfo;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("ProductCommandService 클래스")
class ProductCommandServiceTest {
    @DataJpaTest
    class JpaTest {
        @Autowired
        ProductRepository repository;
        ProductCommandService service;

        public ProductRepository getProductRepository() {
            return repository;
        }

        public ProductCommandService getProductService() {
            if (service == null) {
                service = new ProductCommandServiceImpl(repository);
            }
            return service;
        }
    }

    @Nested
    @DisplayName("createProduct 메소드는")
    class Describe_createProduct {
        @Nested
        @DisplayName("새로운 상품이 주어지면")
        class Context_with_new_product extends JpaTest {
            private final Product givenProduct = ProductFactory.createProduct();

            @Test
            @DisplayName("DB에 등록하고 등록된 상품을 리턴한다")
            void it_returns_registered_product() {
                final ProductCommand.Register command = ProductFactory.of(givenProduct);

                final ProductInfo actualProduct = getProductService().createProduct(command);

                assertThat(actualProduct.getName()).isEqualTo(givenProduct.getName());
                assertThat(actualProduct.getMaker()).isEqualTo(givenProduct.getMaker());
                assertThat(actualProduct.getPrice()).isEqualTo(givenProduct.getPrice());
            }
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class Describe_updateProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id extends JpaTest {
            private Product givenProduct;

            @BeforeEach
            void prepare() {
                givenProduct = getProductRepository().save(ProductFactory.createProduct());
            }

            @Test
            @DisplayName("상품을 수정하고 수정된 상품을 리턴한다")
            void it_returns_modified_product() {

                final ProductCommand.UpdateReq.UpdateReqBuilder updateReqBuilder = ProductCommand.UpdateReq.builder();
                System.out.println(updateReqBuilder.toString()); // jacoco테스트에서 UpdateReqBuilder toString가 계속 0%로 나와서 추가...

                final ProductCommand.UpdateReq command = updateReqBuilder
                        .id(givenProduct.getId())
                        .name("수정_" + givenProduct.getName())
                        .maker("수정_" + givenProduct.getMaker())
                        .price(2000 + givenProduct.getPrice())
                        .imageUrl("modified_" + givenProduct.getImageUrl())
                        .build();

                final ProductInfo actualProduct = getProductService().updateProduct(command);

                assertThat(actualProduct.getPrice()).isEqualTo(command.getPrice());
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id extends JpaTest {
            private final Long PRODUCT_ID = 100L;
            private final Product givenProduct = ProductFactory.createProduct(PRODUCT_ID);

            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                final ProductCommand.UpdateReq command = ProductFactory.of(PRODUCT_ID, givenProduct);

                assertThatThrownBy(() -> getProductService().updateProduct(command)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id extends JpaTest {
            private Product givenProduct;

            @BeforeEach
            void prepare() {
                givenProduct = getProductRepository().save(ProductFactory.createProduct());
            }

            @Test
            @DisplayName("해당 상품을 삭제한다")
            void it_returns_nothing() {
                getProductService().deleteProduct(givenProduct.getId());
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id extends JpaTest {
            @Test
            @DisplayName("예외를 던진다")
            void it_throws_exception() {
                assertThatThrownBy(() -> getProductService().deleteProduct(100L)).isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}

