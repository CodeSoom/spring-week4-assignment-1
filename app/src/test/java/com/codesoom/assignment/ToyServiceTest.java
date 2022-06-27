package com.codesoom.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void getToys_not_exists() {
        // GIVEN

        // WHEN
        List<Toy> toys = toyService.getToys();

        // THEN
        assertThat(toys).isEmpty();
    }

    @Test
    void getToys_exists() {
        // GIVEN
        Toy registered = toyService.register("Toy 1", "maker 1", 1000, "url 1");
        Toy registered2 = toyService.register("Toy 2", "maker 2", 2000, "url 2");

        // WHEN
        List<Toy> toys = toyService.getToys();

        // THEN
        assertThat(toys).hasSize(2);
        assertThat(toys.get(0).getName()).isEqualTo(registered.getName());
        assertThat(toys.get(0).getMaker()).isEqualTo(registered.getMaker());
        assertThat(toys.get(0).getPrice()).isEqualTo(registered.getPrice());
        assertThat(toys.get(0).getImageUrl()).isEqualTo(registered.getImageUrl());
        assertThat(toys.get(1).getName()).isEqualTo(registered2.getName());
        assertThat(toys.get(1).getMaker()).isEqualTo(registered2.getMaker());
        assertThat(toys.get(1).getPrice()).isEqualTo(registered2.getPrice());
        assertThat(toys.get(1).getImageUrl()).isEqualTo(registered2.getImageUrl());
    }
}
