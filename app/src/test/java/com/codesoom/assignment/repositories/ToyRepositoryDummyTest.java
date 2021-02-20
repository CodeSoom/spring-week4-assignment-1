package com.codesoom.assignment.repositories;

import com.codesoom.assignment.models.CatToy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ToyRepositoryDummyTest {
    private final ToyRepository toyRepository = new ToyRepository.Fake();

    @Test
    void findAllById() {
        toyRepository.findAllById(new ArrayList<>());
    }

    @Test
    void existsById() {
        toyRepository.existsById(0L);
    }

    @Test
    void count() {
        toyRepository.count();
    }

    @Test
    void saveAll() {
        toyRepository.saveAll(new ArrayList<>());
    }

    @Test
    void delete() {
        toyRepository.delete(new CatToy(0L, "", "", 0D, ""));
    }

    @Test
    void deleteAllWithoutEntities() {
        toyRepository.deleteAll();
    }

    @Test
    void deleteAll() {
        toyRepository.deleteAll(new ArrayList<>());
    }
}
