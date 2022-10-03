package com.codesoom.assignment.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductTest {
    private final String NAME = "name";

    private final String MAKER = "maker";

    private final Long PRICE = 1000L;


    private final String IMAGEURL = "imageurl";
    @DisplayName("Product entity class")
    @Nested
    class Describe_product {

        @DisplayName("if one product is given")
        @Nested
        class Context_one_product {
            private Product product = Product.builder().name(NAME).maker(MAKER).price(PRICE).imageUrl(IMAGEURL).build();
            @DisplayName("returns a name")
            @Test
            void it_returns_name() {
                assertThat(NAME).isEqualTo(product.getName());
            }
        }
    }

}
