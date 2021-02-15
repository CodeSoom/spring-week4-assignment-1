package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@DisplayName("ProductService 클래스")
class ProductServiceTest {
    final Long ID = 0L;
    final String NAME = "My Toy";
    final String MAKER = "My Home";
    final Long PRICE = 5000L;
    final String IMAGE_URL = "https://cdn.pixabay.com/photo/2016/10/01/20/54/mouse-1708347_1280.jpg";

    @Autowired
    ProductService productService;
    @MockBean
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Mockito.reset(productRepository);
        setUpCreateProduct();
    }

    void setUpCreateProduct() {
        given(productRepository.save(any(Product.class))).will(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(ID);
            return product;
        });
    }

    Product createProduct() {
        Product product = new Product();
        product.setName(NAME);
        product.setMaker(MAKER);
        product.setPrice(PRICE);
        product.setImageURL(IMAGE_URL);
        return productService.save(product);
    }

    @Nested
    @DisplayName("create()")
    class Describe_create {
        @DisplayName("생성된 product를 반환한다")
        @Test
        void it_returns_created_product() {
            //when
            Product product = createProduct();
            //then
            assertThat(product.getId()).isEqualTo(ID);
            assertThat(product.getName()).isEqualTo(NAME);
            assertThat(product.getMaker()).isEqualTo(MAKER);
            assertThat(product.getPrice()).isEqualTo(PRICE);
            assertThat(product.getImageURL()).isEqualTo(IMAGE_URL);
        }
    }

//    @Nested
//    @DisplayName("findAll()")
//    class Describe_findAll {
//        @Nested
//        @DisplayName("product가 존재한다면")
//        class Context_task_exist {
//            @BeforeEach
//            void setUp() {
//                createProduct();
//            }
//
//            @Test
//            @DisplayName("product 리스트를 반환한다")
//            void it_return_product_list() {
//                //when
//                List<Product> list = productRepository.findAll();
//                //then
//                assertThat(list).isNotEmpty();
//            }
//        }
//
//        @Nested
//        @DisplayName("product가 존재하지 않는다면")
//        class Context_task_not_exist {
//            @Test
//            @DisplayName("빈 리스트를 반환한다")
//            void it_return_task_id() {
//                //when
//                List<Task> tasks = taskService.getTasks();
//                //then
//                assertThat(tasks).isEmpty();
//            }
//        }
//    }
}
