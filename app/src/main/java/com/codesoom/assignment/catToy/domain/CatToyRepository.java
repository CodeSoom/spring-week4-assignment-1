package com.codesoom.assignment.catToy.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CatToyRepository implements CrudRepository<CatToy, Long> {
    private final List<CatToy> catToys = new ArrayList<>();
    private Long newId = 0L;

    @Override
    public List<CatToy> findAll() {
        return catToys;
    }

    @Override
    public Iterable<CatToy> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public <S extends CatToy> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<CatToy> findById(Long id) {
        return catToys.stream()
                .filter(toy -> toy.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
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

    @Override
    public void deleteAll(Iterable<? extends CatToy> entities) {

    }

    @Override
    public void deleteAll() {

    }

    private Long generateId() {
        newId += 1;
        return newId;
    }
}
