package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductNotFoundException;
import com.codesoom.assignment.application.ProductReadServiceImpl;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductReadControllerTest {

    @InjectMocks
    private ProductReadController controller;

    @Mock
    private ProductReadServiceImpl service;

    private static final Long EXIST_ID = 1L;
    private static final Long NOT_EXIST_ID = 100L;
    private static final Product SAVED_PRODUCT = Product.builder()
            .name("키위새").maker("유령회사").price(BigDecimal.valueOf(3000)).image("")
            .build();

    @DisplayName("전체 상품을 성공적으로 조회한다.")
    @Test
    void findAllTest() {
        final List<Product> products = List.of(SAVED_PRODUCT);
        when(service.findAll()).thenReturn(products);

        assertThat(controller.getProducts()).isNotEmpty();
    }

    @DisplayName("존재하는 id로 요청하면 해당 상품을 반환한다.")
    @Test
    void findByIdTest() {
        when(service.findById(EXIST_ID)).thenReturn(SAVED_PRODUCT);

        assertThat(controller.getProductDetail(EXIST_ID)).isSameAs(SAVED_PRODUCT);
    }

    @DisplayName("존재하지 않는 id로 요청하면 예외를 던진다.")
    @Test
    void findByNotExistIdTest() {
        when(service.findById(eq(NOT_EXIST_ID))).thenThrow(ProductNotFoundException.class);

        assertThatThrownBy(() -> controller.getProductDetail(NOT_EXIST_ID))
                .isInstanceOf(ProductNotFoundException.class);
    }

}
