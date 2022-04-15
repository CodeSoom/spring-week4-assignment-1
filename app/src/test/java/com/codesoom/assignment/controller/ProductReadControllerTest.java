package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductNotFoundException;
import com.codesoom.assignment.application.ProductReadServiceImpl;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    private static final Product SAVED_PRODUCT = Product.builder()
            .name("키위새").maker("유령회사").price(BigDecimal.valueOf(3000)).image("")
            .build();

    @DisplayName("findAll 메서드는")
    @Nested
    class Describe_find_all {
        @BeforeEach
        void setup() {
            final List<Product> products = List.of(SAVED_PRODUCT);
            when(service.findAll()).thenReturn(products);
        }

        @DisplayName("전체 상품을 성공적으로 조회한다.")
        @Test
        void findAllTest() {
            assertThat(controller.getProducts()).isNotEmpty();
        }
    }

    @DisplayName("findById 메서드는")
    @Nested
    class Describe_find_by_id {

        @DisplayName("존재하는 id로 조회 요청이 오면")
        @Nested
        class Context_with_exist_id {

            private final Long EXIST_ID = 1L;

            @BeforeEach
            void setup() {
                when(service.findById(EXIST_ID)).thenReturn(SAVED_PRODUCT);
            }

            @DisplayName("해당 상품을 반환한다.")
            @Test
            void will_return_found_product() {
                assertThat(controller.getProductDetail(EXIST_ID)).isSameAs(SAVED_PRODUCT);
            }
        }

        @DisplayName("존재하지 않는 id로 조회 요청이 오면")
        @Nested
        class Context_with_not_exist_id {

            private final Long NOT_EXIST_ID = 100L;

            @BeforeEach
            void setup() {
                when(service.findById(eq(NOT_EXIST_ID))).thenThrow(ProductNotFoundException.class);
            }

            @DisplayName("예외를 던진다.")
            @Test
            void will_throw_not_found_exception() {
                assertThatThrownBy(() -> controller.getProductDetail(NOT_EXIST_ID))
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }

}
