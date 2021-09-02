package com.codesoom.assignment.domain;

import static com.codesoom.assignment.domain.ProductConstant.TITLE;

import java.lang.reflect.Field;
import java.util.Arrays;

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
            final Product product = new Product(TITLE);
            final Product source = new Product("updated" + TITLE);

            final Product updatedProduct = product.update(source);

            Arrays.stream(updatedProduct.getClass().getDeclaredFields())
            .filter(field -> field.getName() == "id" && assertThat(field.get(updatedProduct)).isNull())
            .forEach(field -> assertThat(field.get(updatedProduct)).isEqualTo(field.get(source)));
        }
    }
}
