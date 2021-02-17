package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {

    @Test
    void createProductId() {
        assertDoesNotThrow(() -> new ProductId(1L));
        assertThrows(
            IllegalArgumentException.class,
            () -> new ProductId(null)
        );
        assertThrows(
            IllegalArgumentException.class,
            () -> new ProductId(-2L)
        );
    }

    @Test
    void testEquals() {
        ProductId id = new ProductId(1L);
        ProductId sameId = new ProductId(1L);
        ProductId otherId = new ProductId(2L);

        assertThat(id)
            .isNotEqualTo(null)
            .isEqualTo(sameId)
            .isNotEqualTo(otherId);
    }
}
