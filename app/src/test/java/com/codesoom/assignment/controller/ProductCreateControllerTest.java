package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductCreateServiceImpl;
import com.codesoom.assignment.domain.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class ProductCreateControllerTest {

    @InjectMocks
    private ProductCreateController controller;

    @Mock
    private ProductCreateServiceImpl service;

    @DisplayName("상품을 성공적으로 등록한다.")
    @Test
    void createTest() {
        final ProductDto productDto
                = new ProductDto("어쩌구", "어쩌구컴퍼니", BigDecimal.valueOf(2000), "url");

        controller.createProduct(productDto);

        verify(service).create(any(ProductDto.class));
    }

}
