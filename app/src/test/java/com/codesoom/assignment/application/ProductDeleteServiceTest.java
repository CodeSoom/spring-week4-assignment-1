package com.codesoom.assignment.application;


import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class ProductDeleteServiceTest {

    @InjectMocks
    private ProductDeleteServiceImpl service;

    @Mock
    private ProductRepository repository;

    private static final Long EXIST_ID = 1L;
    private static final Long NOT_EXIST_ID = 100L;

    private static final Product PRODUCT
            = Product.builder().name("쥐돌이").maker("어쩌구컴퍼니").price(BigDecimal.valueOf(3000)).build();

    @DisplayName("delete는 id에 해당하는 상품을 삭제한다.")
    @Test
    void deleteTest() {
        given(repository.findById(eq(EXIST_ID))).willReturn(Optional.of(PRODUCT));

        service.deleteById(EXIST_ID);
        verify(repository).delete(eq(PRODUCT));
    }

    @DisplayName("delete는 존재하지 않는 상품을 삭제할 경우 예외를 던진다.")
    @Test
    void deleteWithNotExistId() {
        given(repository.findById(eq(NOT_EXIST_ID))).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteById(NOT_EXIST_ID))
                .isInstanceOf(ProductNotFoundException.class);
    }

}
