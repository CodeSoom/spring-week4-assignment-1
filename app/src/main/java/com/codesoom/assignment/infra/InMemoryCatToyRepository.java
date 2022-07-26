package com.codesoom.assignment.infra;

import com.codesoom.assignment.CatToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryCatToyRepository extends CatToyRepository {
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
    public CatToy find(Long id){
        return catToys.stream()
                .filter(catToy -> catToy.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new CatToyNotFoundException(id));
    }
    @Override
    public CatToy save(CatToy toy){
        toy.setId(generateId());
        catToys.add(toy);
        return toy;
    }

    @Override
    public CatToy delete(CatToy toy) {
        return null;
    }
}
