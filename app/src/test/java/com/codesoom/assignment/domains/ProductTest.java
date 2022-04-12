package com.codesoom.assignment.domains;

import com.codesoom.assignment.contexts.ContextProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest extends ContextProduct {

    @Test
    @DisplayName("고양이 장난감 생성하기")
    void createProduct() {
        Product product = new Product(PRODUCT_ID, CATEGORY, PRODUCT_NAME, MAKER, PRICE, IMAGE);

        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isEqualTo(PRODUCT_ID);
    }

}
