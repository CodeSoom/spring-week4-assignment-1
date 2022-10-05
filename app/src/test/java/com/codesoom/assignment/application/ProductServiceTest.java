package com.codesoom.assignment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

  private Product source;
  private Product saved;

  @BeforeEach
  void setUp() {
    productService = new ProductService(productRepository);
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
        productService.create(saved);
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

      void removeAll() {
        productRepository.deleteAll();
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
          saved = productService.create(source);
        }

        @Test
        @DisplayName("해당 id를 가진 Product객체를 리턴한다")
        void it_returns_product() {
          assertThat(productService.findById(1L)).isEqualTo(saved);

        }
      }

      @Nested
      @DisplayName("id가 존재하지 않는다면")
      class Context_non_existent_id {

        @Test
        @DisplayName("ProductNotFoundException을 발생시킨다")
        void it_returns_ProductNotFoundException() {
          assertThatThrownBy(() -> productService.findById(-1L)).isInstanceOf(
              ProductNotFoundException.class);
        }
      }
    }

    @Nested
    @DisplayName("remove 메소드는")
    class Describe_remove {

      @Nested
      @DisplayName("id가 존재한다면")
      class Context_existent_id {

        @BeforeEach
        void prepare() {
          source = new Product(1L, "테스트", 2000, "");
          saved = productService.create(source);
        }

        @Test
        @DisplayName("해당 id를 가진 객체를 DB에서 삭제후 리턴한다")
        void it_returns_remove_product() {
          assertThat(productService.remove(1L)).isEqualTo(saved);

        }
      }
      @Nested
      @DisplayName("id가 존재하지 않는다면")
      class Context_non_existent_id {

        @Test
        @DisplayName("ProductNotFoundException을 발생시킨다")
        void it_returns_ProductNotFoundException() {
          assertThatThrownBy(() -> productService.findById(-1L)).isInstanceOf(
              ProductNotFoundException.class);

        }
      }
    }
  }

