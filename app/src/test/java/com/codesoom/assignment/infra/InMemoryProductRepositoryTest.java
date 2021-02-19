package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("InMemoryProductRepository 클래스")
class InMemoryProductRepositoryTest {

    @Autowired
    private InMemoryProductRepository inMemoryProductRepository = new InMemoryProductRepository();

    private Product firstProduct;
    private Product secondProduct;

    private final String FIRST_PRODUCT_NAME = "name1";
    private final String SECOND_PRODUCT_NAME = "name2";
    private final Long FIRST_PRODUCT_ID = 1L;
    private final Long NOT_EXISTING_ID = 999L;
    private int existingProductSize;

    @BeforeEach
    void setUp() {
        firstProduct = new Product(FIRST_PRODUCT_ID, FIRST_PRODUCT_NAME, "maker1", 1000, "imageUrl1");
        secondProduct = new Product(2L, SECOND_PRODUCT_NAME, "maker2", 1000, "imageUrl2");

        inMemoryProductRepository.save(firstProduct);
        inMemoryProductRepository.save(secondProduct);

        existingProductSize = 2;
    }

    @AfterEach
    void clear() {
        inMemoryProductRepository.findAll().clear();
    }

    @Nested
    @DisplayName("findAll()은")
    class Describe_findAll {

        @Nested
        @DisplayName("저장된 상품이 있다면")
        class Context_with_products {
        }

        @Test
        @DisplayName("모든 상품을 리턴한다.")
        void it_return_all_products() throws Exception {
            List<Product> products = inMemoryProductRepository.findAll();

            assertThat(products).hasSize(2);
        }

        @Nested
        @DisplayName("저장된 상품이 없다면")
        class Context_with_no_product {

            @BeforeEach
            void clearProducts() {
                inMemoryProductRepository.findAll().clear();
            }

            @Test
            @DisplayName("비어있는 목록을 리턴한다.")
            void it_returns_empty_list() {
                List<Product> products = inMemoryProductRepository.findAll();

                assertThat(products).hasSize(0);
            }
        }
    }

    @Nested
    @DisplayName("findById()는")
    class Describe_findById {

        @Nested
        @DisplayName("상품목록에 존재하는 id로 조회하면")
        class Context_contains_target_id {

            @Test
            @DisplayName("id에 해당하는 상품을 리턴한다.")
            void it_returns_product() throws JsonProcessingException {
                Optional<Product> foundProduct = inMemoryProductRepository.findById(firstProduct.getId());

                assertThat(foundProduct.get().getName()).isEqualTo(FIRST_PRODUCT_NAME);
            }
        }

        @Nested
        @DisplayName("상품목록에 존재하지 않는 id로 조회하면")
        class Context_not_contains_target_id {

            @Test
            @DisplayName("비어있는 optional을 리턴한다.")
            void it_returns_empty_optional() {
                Optional<Product> foundProduct = inMemoryProductRepository.findById(NOT_EXISTING_ID);

                assertThat(foundProduct.isEmpty());
            }
        }
    }

    @Nested
    @DisplayName("save()는")
    class Describe_save {

        @Test
        @DisplayName("상품을 저장하고 그 상품을 리턴한다.")
        void it_save_product_and_return () {
            Product product = new Product(3L, "name3", "maker3", 1000, "imageUrl3");

            Product savedProduct = inMemoryProductRepository.save(product);

            assertThat(savedProduct).isEqualTo(product);
        }
    }

    @Nested
    @DisplayName("delete()는")
    class Dedsicribe_delete {
        @Nested
        @DisplayName("상품목록에 존재하지 않는 id로 조회하면")
        class Context_contains_target_id {

            @Test
            @DisplayName("상품을 삭제한다.")
            void it_deletes_target_project () {
                inMemoryProductRepository.delete(firstProduct);

                assertThat(inMemoryProductRepository.findById(FIRST_PRODUCT_ID)).isEmpty();
            }
        }

        @Nested
        @DisplayName("상품목록에 존재하지 않는 id로 조회하면")
        class Context_not_contains_target_id {

            @Test
            @DisplayName("상품을 삭제하지 않는다.")
            void it_delete_nothing () {
                Product notExisitingProduct = new Product(NOT_EXISTING_ID,"noName","noMaker",0,"noImageUrl");

                inMemoryProductRepository.delete(notExisitingProduct);

                assertThat(inMemoryProductRepository.findAll()).hasSize(existingProductSize);
            }
        }
    }
}
