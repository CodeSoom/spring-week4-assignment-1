package com.codesoom.assignment;

import com.codesoom.assignment.domain.CatToy;
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
public class ProvideInvalidCatToyArguments implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        CatToy catToy1 = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(catToy1, "name", "");

        CatToy catToy2 = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(catToy2, "name", null);

        CatToy catToy3 = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(catToy3, "maker", "");

        CatToy catToy4 = CatToy.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        ReflectionTestUtils.setField(catToy4, "maker", null);



        return Stream.of(
                Arguments.of(catToy1),
                Arguments.of(catToy2),
                Arguments.of(catToy3),
                Arguments.of(catToy4)
        );
    }
}
