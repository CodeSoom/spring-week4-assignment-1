package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.dto.ProductSaveRequestDto;
import com.codesoom.assignment.dto.RequstIdDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DataJpaTest
public class MockandSpyTest {

    @Mock
    ProductRepository productRepository;

    @SpyBean
    ProductService productService;


    @Test
    public void basicTest() throws Exception {
        Product product = Product.builder()
                .id(1L)
                .name("name")
                .maker("maker")
                .price(1000)
                .img("img")
                .build();
        System.out.println("product = " + product.getName());

        productRepository.save(product);

        assertEquals("name", product.getName());
    }

    @Test
    public void givenTest() throws Exception {
        ProductSaveRequestDto dto = ProductSaveRequestDto.builder()
                .name("name")
                .maker("maker")
                .price(1000)
                .img("img")
                .build();

        given(productService.registerProduct(dto)).willReturn(dto.getId());

        System.out.println("productRepository = " + productService.getProducts());

        assertThat(productService.getProducts()).isNotNull();
    }

    @Test
    public void whenTest() throws Exception{
        //given
        ProductSaveRequestDto dto = ProductSaveRequestDto.builder()
                .name("name")
                .maker("maker")
                .price(1000)
                .img("img")
                .build();
        when(productService.registerProduct(dto)).thenReturn(dto.getId());
        System.out.println("productService.getClass() = " + productService.getClass());//ProductService$MockitoMock$1133643204$$EnhancerBySpringCGLIB$$2840384d
        System.out.println("productRepository.getClass()"+productRepository.getClass());//ProductRepository$MockitoMock$547208436
        assertThat(productService.getProducts().get(0).getMaker()).isEqualTo("maker");
    }
}
