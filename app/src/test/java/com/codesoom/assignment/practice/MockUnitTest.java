package com.codesoom.assignment.practice;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.dto.RequstIdDto;
import com.codesoom.assignment.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MockUnitTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    public void getProductsValid() throws Exception{
        //given
        given(productRepository.findAll()).willReturn(List.of(
                new Product(1L, "name", "maker", 1000, "img1")
        ));
        //when
        List<ProductDto> products = productService.getProducts();
        //Then
        assertNotNull(products);
    }

//    @Test
//    public void getProductByIdValid() throws Exception {
//       //given
//        given(productRepository.findById(1L)).willReturn(Optional.of(new Product(anyLong(),"name","maker",1000,"img")));
//
//        //when
//        List<ProductDto> productById = productService.getProductById(RequstIdDto.builder()
//                .id(1L).build());
//        //then
//        assertNotNull(productById);
//    }

    @Transactional
    @Test
    public void basicServiceTest() throws Exception {
        Product product = new Product();
        product.setName("name");
        product.setMaker("maker");
        product.setPrice(1000);
        product.setImg("img");
        productRepository.save(product);
    }

}
