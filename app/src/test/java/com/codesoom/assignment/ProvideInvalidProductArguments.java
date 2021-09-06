package com.codesoom.assignment;

import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.codesoom.assignment.Constant.OTHER_IMAGE_URL;
import static com.codesoom.assignment.Constant.OTHER_MAKER;
import static com.codesoom.assignment.Constant.OTHER_NAME;
import static com.codesoom.assignment.Constant.OTHER_PRICE;

/**
 * 필드에 공백(or null)이 포함된 상품 객체 매개변수를 전달한다.
 */
public class ProvideInvalidProductArguments implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        String[][] params = {{"name", ""}, {"name", null}, {"maker", ""}, {"maker", null}};

        final List<Product> invalidProducts = IntStream.range(0, 4)
                .mapToObj(index -> {
                    final Product product = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
                    ReflectionTestUtils.setField(product, params[index][0], params[index][1]);

                    return product;
                }).collect(Collectors.toList());

        return Stream.of(Arguments.of(invalidProducts));
    }
}
