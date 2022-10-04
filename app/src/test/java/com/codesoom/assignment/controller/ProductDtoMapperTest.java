package com.codesoom.assignment.controller;

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
                final ProductDto.RequestParam request = new ProductDto.RequestParam();
                request.setId(1L);
                request.setName("테스트");
                request.setMaker("테스트");
                request.setPrice(1000L);
                request.setImageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png");

                ProductCommand.Register actual = productDtoMapper.of(request);

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

                ProductCommand.Register actual = productDtoMapper.of(request);

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
                final ProductDto.RequestParam request = new ProductDto.RequestParam();
                request.setName("테스트");
                request.setMaker("테스트");
                request.setPrice(1000L);
                request.setImageUrl("https://user-images.githubusercontent.com/47380072/83365762-9d4b0880-a3e5-11ea-856e-d71c97ab691e.png");

                ProductCommand.Register actual = productDtoMapper.of(id, request);

                assertThat(actual).isInstanceOf(ProductCommand.Register.class);
            }
        }

        @Nested
        @DisplayName("빈 요청 파라미터가 주어지면")
        class Context_with_invalid_request_parameter {
            @Test
            @DisplayName("Null을 리턴한다")
            void it_returns_register() {
                final Long id = null;
                final ProductDto.RequestParam request = null;

                ProductCommand.Register actual = productDtoMapper.of(id, request);

                assertThat(actual).isNull();
            }
        }

    }

}