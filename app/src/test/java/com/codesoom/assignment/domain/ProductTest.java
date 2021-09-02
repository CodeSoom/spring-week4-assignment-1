package com.codesoom.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static com.codesoom.assignment.domain.ProductConstant.TITLE;

import java.lang.reflect.Method;
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
            final Method[] methods = Product.class.getMethods();
            final Product product = spy(new Product(TITLE));
            final Product source = new Product("updated" + TITLE);

            product.update(source);

            assertThat(product.getId()).isNull();

            Arrays.stream(methods)
            .filter(method -> method.getName().indexOf("get") != -1)
            .filter(method -> !method.getName().equals("getClass"))
            .filter(method -> !method.getName().equals("getId"))
            .forEach(method -> {
                try {
                    assertThat(method.invoke(product)).isEqualTo(method.invoke(source));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
