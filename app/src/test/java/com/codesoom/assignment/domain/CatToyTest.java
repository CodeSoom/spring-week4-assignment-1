package com.codesoom.assignment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CatToyTest {
    @Test
    void creation() {
        CatToy catToy = new CatToy("장난감");

        assertThat(catToy.getCatToyName()).isEqualTo("장난감");
    }

}
