package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Product Service")
class ProductServiceTest {
    //1. list   -> getProducts
    //2. detail -> getProduct (with ID)
    //3. create -> createProduct (with source)
    //4. update -> updateProduct (with ID, source)
    //5. delete -> deleteProduct (with ID)

    private static final String NAME = "TestName ";
    private static final String MAKER = "TestMaker";
    private static final long PRICE = 1000;
    private static final String IMAGE = "http://gdimg.gmarket.co.kr/1482334965/still/600?ver=1535083811";

    private ProductService productService;


    @BeforeEach
    void setUp(){
        productService = new ProductService();

        Product product = new Product();
        product.setName(NAME);
        product.setMaker(MAKER);
        product.setPrice(PRICE);
        product.setImage(IMAGE);

        productService.createProduct(product);
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts{
        
        @Test
        @DisplayName("저장되어 있는 제품 리스트를 리턴합니다.")
        void it_returns_products(){
            List<Product> products = productService.getProducts();
            assertThat(products).hasSize(1);

            Product product = products.get(0);
            assertThat(product.getName()).isEqualTo(NAME);
            assertThat(product.getMaker()).isEqualTo(MAKER);
            assertThat(product.getPrice()).isEqualTo(PRICE);
            assertThat(product.getImage()).isEqualTo(IMAGE);
        }


    }
}
