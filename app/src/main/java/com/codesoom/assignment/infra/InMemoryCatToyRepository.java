package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCatToyRepository implements CatToyRepository {
    private final ArrayList<CatToy> catToys = new ArrayList<CatToy>();
    private AtomicLong newId = new AtomicLong(0L);

    @Override
    public List<CatToy> findAll() {
        return catToys;
    }

    @Override
    public Optional<CatToy> findById(Long catToyId) {
        return catToys.stream()
                .filter(catToy -> {
                    return catToy.getId().equals(catToyId);
                })
                .findFirst();
    }

    @Override
    public void deleteAll() {
        catToys.clear();
    }

    @Override
    public CatToy save(CatToy catToy) {
        catToy.setId(newId.incrementAndGet());
        catToys.add(catToy);
        return catToy;
    }
}
