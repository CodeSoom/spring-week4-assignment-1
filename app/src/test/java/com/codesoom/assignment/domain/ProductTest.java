package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNull;
import static com.codesoom.assignment.domain.ProductConstant.NAME;
import static com.codesoom.assignment.domain.ProductConstant.MAKER;
import static com.codesoom.assignment.domain.ProductConstant.IMAGE_URL;
import static com.codesoom.assignment.domain.ProductConstant.PRICE;

import com.codesoom.assignment.dto.ProductDto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Product 클래스")
public class ProductTest {
    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {
        @Test
        @DisplayName("id를 제외한 모든 멤버변수를 업데이트 한다.")
        void it_updates_all_member_variables() {
            final Product product = new Product(
                new ProductDto(null, null, null, null)
            );
            final Product source = new Product(
                new ProductDto(NAME, MAKER, IMAGE_URL, PRICE)
            );

            assertThat(product.update(source))
                .matches(output -> output.getId() == isNull())
                .matches(output -> source.getName().equals(output.getName()))
                .matches(output -> source.getMaker().equals(output.getMaker()))
                .matches(output -> source.getImageUrl().equals(output.getImageUrl()))
                .matches(output -> source.getPrice().equals(output.getPrice()));
        }
    }
}
