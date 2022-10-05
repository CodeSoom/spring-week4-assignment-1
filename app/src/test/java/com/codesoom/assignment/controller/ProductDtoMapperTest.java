package com.codesoom.assignment.controller;

import com.codesoom.assignment.common.ProductFactory;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
@DisplayName("ProductDtoMapper 클래스")
class ProductDtoMapperTest {

    private ProductDtoMapper productDtoMapper;

    @BeforeEach
    void setUp() {
        productDtoMapper = new ProductDtoMapper();
    }

    @Nested
    @DisplayName("of(RequestParam) 메소드는")
    class Describe_of_request_param {
        @Nested
        @DisplayName("유효한 요청 파라미터가 주어지면")
        class Context_with_valid_request_parameter {
            @Test
            @DisplayName("Register 객체를 리턴한다")
            void it_returns_register() {
                final Product product = ProductFactory.createProduct(1L);

                final ProductCommand.Register actual = ProductFactory.of(product);

                assertThat(actual).isInstanceOf(ProductCommand.Register.class);
            }
        }

        @Nested
        @DisplayName("빈 요청 파라미터가 주어지면")
        class Context_with_invalid_request_parameter {
            @Test
            @DisplayName("Null을 리턴한다")
            void it_returns_register() {
                final ProductDto.RequestParam request = null;

                final ProductCommand.Register actual = productDtoMapper.of(request);

                assertThat(actual).isNull();
            }
        }

    }

    @Nested
    @DisplayName("of(id, RequestParam) 메소드는")
    class Describe_of_id_and_request_param {
        @Nested
        @DisplayName("유효한 요청 파라미터가 주어지면")
        class Context_with_valid_request_parameter {
            @Test
            @DisplayName("Register 객체를 리턴한다")
            void it_returns_register() {
                final Long id = 1L;

                final ProductCommand.Register actual = productDtoMapper.of(id, ProductFactory.createRequestParam());

                assertThat(actual).isInstanceOf(ProductCommand.Register.class);
            }
        }

        @Nested
        @DisplayName("모든 파라미터가 빈 값이 주어지면")
        class Context_with_invalid_request_parameter {
            @Test
            @DisplayName("Null을 리턴한다")
            void it_returns_null() {
                final Long id = null;
                final ProductDto.RequestParam request = null;

                final ProductCommand.Register actual = productDtoMapper.of(id, request);

                assertThat(actual).isNull();
            }
        }

        @Nested
        @DisplayName("ID만 빈 값으로 주어지면")
        class Context_with_id_null {
            @Test
            @DisplayName("Null을 리턴한다")
            void it_returns_null() {
                final Long id = null;

                final ProductCommand.Register actual = productDtoMapper.of(id, ProductFactory.createRequestParam());

                assertThat(actual).isNull();
            }
        }

        @Nested
        @DisplayName("RequestParam만 빈 값으로 주어지면")
        class Context_with_requestparam_null {
            @Test
            @DisplayName("Null을 리턴한다")
            void it_returns_null() {
                final Long id = 1L;
                final ProductDto.RequestParam request = null;

                final ProductCommand.Register actual = productDtoMapper.of(id, request);

                assertThat(actual).isNull();
            }
        }
    }

}