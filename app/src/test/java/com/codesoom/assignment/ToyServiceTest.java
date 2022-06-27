package com.codesoom.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ToyServiceTest {
    private ToyService toyService;

    @BeforeEach
    void setUp() {
        toyService = new ToyService();
    }

    @Test
    void getToyById() {
        // GIVEN
        Long ID_NOT_EXISTS = Long.MAX_VALUE;

        // WHEN
        Toy toy = toyService.getToyById(ID_NOT_EXISTS);

        // THEN
        assertThat(toy).isNull();
    }
}
