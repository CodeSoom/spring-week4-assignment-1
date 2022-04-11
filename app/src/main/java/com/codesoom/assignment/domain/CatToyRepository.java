package com.codesoom.assignment.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CatToyRepository {
    private final List<CatToy> tasks = new ArrayList<>();
    private Long newId = 0L;

    public List<CatToy> findAll() {
        return tasks;
    }

    public Optional<CatToy> findById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public CatToy save(CatToy catToy) {
        catToy.setId(generateId());

        tasks.add(catToy);

        return catToy;
    }

    public void delete(CatToy catToy) {
        tasks.remove(catToy);
    }

    private Long generateId() {
        newId += 1;
        return newId;
    }
}
