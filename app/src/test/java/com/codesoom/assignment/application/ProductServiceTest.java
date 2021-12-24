package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Product Service")
class ProductServiceTest {
    //1. list   -> getProducts
    //2. detail -> getProduct (with ID)
    //3. create -> createProduct (with source)
    //4. update -> updateProduct (with ID, source)
    //5. delete -> deleteProduct (with ID)

    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp(){

        productService = new ProductService(productRepository);

        product1 =  new Product(0L, "catTower", "samsung", 35900L, "https://thumbnail14.coupangcdn.com/thumbnails/remote/712x712ex/image/retail/images/451976858609946-e5186418-5f5e-4f4c-bccc-a59ac573d029.jpg");
        product2 = new Product(1L, "catBall", "love cat", 8000L, "http://mstatic1.e-himart.co.kr/contents/goods/00/05/96/13/20/0005961320__TB10__M_640_640.jpg");
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts{

        List<Product> testProducts;

        @BeforeEach
        void setUp(){
            testProducts = Arrays.asList(product1, product2);
            for (Product testProduct: testProducts ){
                productService.createProduct(testProduct);
            }
        }
        
        @Test
        @DisplayName("저장되어 있는 제품 리스트를 리턴합니다.")
        void it_returns_products(){

            List<Product> products = productService.getProducts();

            verify(productRepository).findAll();

            assertThat(products).hasSize(testProducts.size());

            for (int i = 0; i < products.size(); i++){
                Product product = products.get(i);
                assertThat(product.equals(testProducts.get(i))).isEqualTo(true);
            }
        }
    }
}
