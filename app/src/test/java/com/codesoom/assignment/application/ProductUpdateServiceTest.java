package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class ProductUpdateServiceTest {

    @InjectMocks
    private ProductUpdateServiceImpl service;

    @Mock
    private ProductRepository repository;

    private static final Long EXIST_ID = 1L;
    private static final Long NOT_EXIST_ID = 100L;

    private static final Product OLD_PRODUCT_ENTITY
            = Product.builder().name("쥐돌이").maker("어쩌구컴퍼니").price(BigDecimal.valueOf(3000)).build();
    private static final String UPDATE_NAME = "꿈돌이";

    @DisplayName("장난감을 성공적으로 수정한다.")
    @Test
    void updateTest() {
        given(repository.findById(eq(EXIST_ID))).willReturn(Optional.of(OLD_PRODUCT_ENTITY));
        final ProductDto productDto = new ProductDto(UPDATE_NAME, "", BigDecimal.valueOf(3000), "");

        final Product product = service.update(EXIST_ID, productDto);
        assertThat(product).isInstanceOf(Product.class);
        assertThat(product.getName()).isEqualTo(UPDATE_NAME);
    }

    @DisplayName("존재하지 않는 장난감을 수정할 경우 예외를 던진다..")
    @Test
    void updateWithNotExistIdTest() {
        given(repository.findById(eq(NOT_EXIST_ID))).willReturn(Optional.empty());

        final ProductDto productDto = new ProductDto("", "", BigDecimal.valueOf(3000), "");
        assertThatThrownBy(()->service.update(NOT_EXIST_ID, productDto))
                .isInstanceOf(ProductNotFoundException.class);
    }

}
