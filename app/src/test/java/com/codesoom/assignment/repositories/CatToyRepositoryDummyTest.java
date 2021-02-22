package com.codesoom.assignment.repositories;

import com.codesoom.assignment.models.CatToy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class CatToyRepositoryDummyTest {
    private final CatToyRepository catToyRepository = new CatToyRepository.Fake();

    @Test
    void findAllById() {
        catToyRepository.findAllById(new ArrayList<>());
    }

    @Test
    void existsById() {
        catToyRepository.existsById(0L);
    }

    @Test
    void count() {
        catToyRepository.count();
    }

    @Test
    void saveAll() {
        catToyRepository.saveAll(new ArrayList<>());
    }

    @Test
    void delete() {
        catToyRepository.delete(new CatToy(0L, "", "", 0D, ""));
    }

    @Test
    void deleteAllWithoutEntities() {
        catToyRepository.deleteAll();
    }

    @Test
    void deleteAll() {
        catToyRepository.deleteAll(new ArrayList<>());
    }
}
