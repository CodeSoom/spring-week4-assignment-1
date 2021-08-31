package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.CatToyNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CatToyRepository {
    private HashMap<Long, CatToy> catToys;
    private Long nextId;

    private final Object nextIdLock = new Object();

    public CatToyRepository() {
        this.catToys = new HashMap<Long, CatToy>();
        this.nextId = 1L;
    }

    public CatToy save(CatToy catToy) {
        Long newId;
        synchronized (nextIdLock) {
            newId = generateId();
        }

        catToy.setId(newId);
        this.catToys.put(newId, catToy);

        return catToy;
    }

    public List<CatToy> findAll() {
        return new ArrayList<CatToy>(this.catToys.values());
    }

    public Optional<CatToy> findById(Long id) {
        return Optional.ofNullable(this.catToys.get(id));
    }

    public void update(Long id, CatToy catToy) {
        CatToy updatedCatToy = findById(id)
                .orElseThrow(() -> new CatToyNotFoundException(id
                q));

        updatedCatToy.setName(catToy.getName());
        updatedCatToy.setMaker(catToy.getMaker());
        updatedCatToy.setPrice(catToy.getPrice());
        updatedCatToy.setImageURI(catToy.getImageURI());
    }

    public void deleteById(Long id) {
        if(!existsById(id)) {
            throw new CatToyNotFoundException(id);
        }

        this.catToys.remove(id);
    }

    private Long generateId() {
        return nextId++;
    }

    private boolean existsById(Long id) {
        return this.catToys.containsKey(id);
    }
}
