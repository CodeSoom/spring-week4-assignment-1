package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CatToyRepository implements CrudRepository<CatToy, Long> {
    private final List<CatToy> catToys = new ArrayList<>();
    private Long newId = 0L;

    @Override
    public List<CatToy> findAll() {
        return catToys;
    }

    @Override
    public Optional<CatToy> findById(Long id) {
        return catToys.stream()
                .filter(toy -> toy.getId().equals(id))
                .findFirst();
    }

    @Override
    public CatToy save(CatToy toy) {
        toy.setId(generateId());
        catToys.add(toy);
        return toy;
    }

    public CatToy updateToy(CatToy source, CatToy toy) {
        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImageUrl(source.getImageUrl());
        return toy;
    }

    @Override
    public void delete(CatToy toy) {
        catToys.remove(toy);
    }

    private Long generateId() {
        newId += 1;
        return newId;
    }
}
