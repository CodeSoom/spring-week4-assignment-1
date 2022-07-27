package com.codesoom.assignment.infra;

import com.codesoom.assignment.CatToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryCatToyRepository implements CatToyRepository{
    private final List<CatToy> catToys = new ArrayList<>();
    private Long newId = 0L;

    private Long generateId(){
        newId += 1;
        return newId;
    }
    @Override
    public List<CatToy> findAll(){
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
    public Optional<CatToy> findById(Long id){
        return catToys.stream()
                .filter(catToy -> catToy.getId().equals(id))
                .findFirst();
    }

    @Override
    public CatToy save(CatToy toy){
        toy.setId(generateId());
        catToys.add(toy);
        return toy;
    }

    @Override
    public void delete(CatToy toy) {
        catToys.remove(toy);
    }
}
