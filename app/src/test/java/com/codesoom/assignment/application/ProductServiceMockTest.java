package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@DisplayName("ProductService Mock 테스트")
@ExtendWith(MockitoExtension.class)
public class ProductServiceMockTest {

    @DisplayName("ProductCreateServiceImpl 클래스")
    @Nested
    class ProductCreateServiceTest {


        @InjectMocks
        private ProductCreateServiceImpl service;

        @Mock
        private ProductRepository repository;

        @DisplayName("장난감을 성공적으로 등록한다.")
        @Test
        void createToyTest() {
            final ProductDto productDto
                    = new ProductDto("name", "maker", BigDecimal.valueOf(2000), "image");

            service.create(productDto);

            verify(repository).save(any(Product.class));
        }
    }

}
