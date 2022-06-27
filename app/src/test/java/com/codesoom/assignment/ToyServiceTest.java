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
    void getToyById_not_exist() {
        // GIVEN
        Long ID_NOT_EXISTS = Long.MAX_VALUE;

        // WHEN
        Toy toy = toyService.getToyById(ID_NOT_EXISTS);

        // THEN
        assertThat(toy).isNull();
    }

    @Test
    void getToyById_exist() {
        // GIVEN
        Toy registered = toyService.register("Toy 1", "maker 1", 1000, "url 1");

        // WHEN
        Toy toy = toyService.getToyById(registered.getId());

        // THEN
        assertThat(toy).isNotNull();
        assertThat(toy.getName()).isEqualTo(registered.getName());
        assertThat(toy.getMaker()).isEqualTo(registered.getMaker());
        assertThat(toy.getPrice()).isEqualTo(registered.getPrice());
        assertThat(toy.getImageUrl()).isEqualTo(registered.getImageUrl());
    }
}
