package com.codesoom.assignment;

import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.stream.Stream;

import static com.codesoom.assignment.Constant.OTHER_IMAGE_URL;
import static com.codesoom.assignment.Constant.OTHER_MAKER;
import static com.codesoom.assignment.Constant.OTHER_NAME;
import static com.codesoom.assignment.Constant.OTHER_PRICE;

/**
 * 필드에 공백(or null)이 포함된 고양이 장난감 객체 매개변수를 전달한다.
 */
public class ProvideInvalidProductArguments implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        Product product1 = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(product1, "name", "");

        Product product2 = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(product2, "name", null);

        Product product3 = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(product3, "maker", "");

        Product product4 = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(product4, "maker", null);



        return Stream.of(
                Arguments.of(product1),
                Arguments.of(product2),
                Arguments.of(product3),
                Arguments.of(product4)
        );
    }
}
