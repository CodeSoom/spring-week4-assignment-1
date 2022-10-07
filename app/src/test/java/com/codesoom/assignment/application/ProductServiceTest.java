package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.codesoom.assignment.ProductDeleteService;
import com.codesoom.assignment.entity.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("ProductService")
public class ProductServiceTest {

  @Autowired
  private ProductRepository productRepository;
  private ProductService productService;
  private ProductDeleteService productDeleteService;

  private Product source;
  private Product createdProduct;

  @BeforeEach
  void setUp() {
    productService = new ProductService(productRepository);
    productDeleteService = new ProductDeleteService(productRepository);
  }

  @Nested
  @DisplayName("getList 메소드는")
  class Describe_get_list {

    @Nested
    @DisplayName("저장된 product가 있다면")
    class Context_existent_product {

      @BeforeEach
      void prepare() {
        source = new Product(1L, "테스트", 2000, "");
        productService.create(source);
      }

      @Test
      @DisplayName("ProductList를 리턴한다")
      void it_returns_productList() {
        assertThat(productService.getList()).hasSize(1);
      }
    }

    @Nested
    @DisplayName("저장된 product가 없다면")
    class Context_non_existent_product {

      @BeforeEach
      void removeAll() {
        productDeleteService.deleteAll();
      }

      @Test
      @DisplayName("빈 productList를 리턴한다")
      void it_returns_emptyList() {
        removeAll();
        assertThat(productService.getList()).hasSize(0);

      }
    }
  }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_find_by_id {

      @Nested
      @DisplayName("id가 존재한다면")
      class Context_existent_id {

        @BeforeEach
        void prepare() {
          source = new Product(1L, "테스트", 2000, "");
          createdProduct = productService.create(source);
        }

        @Test
        @DisplayName("해당 id를 가진 Product객체를 리턴한다")
        void it_returns_product() {
          Product foundProduct = productService.findById(createdProduct.getId());
          assertThat(foundProduct).isEqualTo(createdProduct);
        }
      }

      @Nested
      @DisplayName("id가 존재하지 않는다면")
      class Context_non_existent_id {

        @BeforeEach
        void prepare() {
          productDeleteService.deleteAll();
          assertThat(productService.getList()).hasSize(0);
        }

        @Test
        @DisplayName("ProductNotFoundException을 발생시킨다")
        void it_returns_ProductNotFoundException() {
          assertThatThrownBy(() -> productService.findById(1L)).isInstanceOf(
              ProductNotFoundException.class);
        }
      }
    }

    @Nested
    @DisplayName("remove 메소드는")
    class Describe_remove {

      @Nested
      @DisplayName("삭제할 수 있는 product의 id가 존재한다면")
      class Context_existent_id {

        @BeforeEach
        void prepare() {
          productDeleteService.deleteAll();
          createdProduct = productService.create(new Product(1L, "테스트", 2000, ""));
          assertThat(productService.getList()).hasSize(1);
        }

        @Test
        @DisplayName("해당 id를 가진 객체를 DB에서 삭제후 리턴한다")
        void it_returns_remove_product() {
          assertThat(productService.remove(createdProduct.getId())).isEqualTo(createdProduct);
        }
      }

      @Nested
      @DisplayName("삭제할 수 있는 product의 id가 존재하지 않는다면")
      class Context_non_existent_id {

        @BeforeEach
        void prepare() {
          productDeleteService.deleteAll();
          assertThat(productService.getList()).hasSize(0);
        }

        @Test
        @DisplayName("ProductNotFoundException을 던진다")
        void it_returns_ProductNotFoundException() {
          assertThatThrownBy(() -> productService.remove(1L)).isInstanceOf(
              ProductNotFoundException.class);
        }
      }
    }

    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {

      @Nested
      @DisplayName("업데이트 할 product의 id와 변경할 정보가 담겨있는 product객체가 존재한다면")
      class Context_existent_id {

        Product update = new Product(null, "업데이트", 1000, "");

        @BeforeEach
        void prepare() {
          source = new Product(1L, "테스트", 2000, "");
          createdProduct = productService.create(source);
        }

        @Test
        @DisplayName("해당 id를 가진 Product객체의 정보를 update한후 리턴한다")
        void it_returns_updated_product() {
          Product updated = productService.update(createdProduct.getId(), update);
          assertThat(updated.getBrand()).isEqualTo(update.getBrand());
        }
      }

      @Nested
      @DisplayName("업데이트 할 product의 id가 존재하지 않는다면")
      class Context_non_existent_id {

        @BeforeEach
        void prepare() {
          productDeleteService.deleteAll();
          assertThat(productService.getList()).hasSize(0);
        }

        @Test
        @DisplayName("ProductNotFoundException을 던진다")
        void it_returns_ProductNotFoundException() {
          assertThatThrownBy(() -> productService.remove(-1L)).isInstanceOf(
              ProductNotFoundException.class);
        }
      }

    }
  }

