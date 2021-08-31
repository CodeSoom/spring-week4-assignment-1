package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.CatToyNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CatToyRepository {
    private HashMap<Long, CatToy> catToys;
    private Long nextId;

    private final Object nextIdLock = new Object();

    public CatToyRepository() {
        this.catToys = new HashMap<Long, CatToy>();
        this.nextId = 1L;
    }

    public void create(CatToy catToy) {
        Long newId;
        synchronized (nextIdLock) {
            newId = generateId();
        }

        catToy.setId(newId);
        this.catToys.put(newId, catToy);
    }

    public List<CatToy> getAll() {
        return new ArrayList<CatToy>(this.catToys.values());
    }

    public CatToy get(Long id) {
        if(!exist(id)) {
            String message = "id " + id + "를 가지는 CatToy가 없습니다.";
            throw new CatToyNotFoundException(message);
        }

        return this.catToys.get(id);
    }

    public void update(Long id, CatToy catToy) {
        if(!exist(id)) {
            String message = "id " + id + "를 가지는 CatToy가 없습니다.";
            throw new CatToyNotFoundException(message);
        }

        CatToy updatedCatToy = get(id);
        updatedCatToy.setName(catToy.getName());
        updatedCatToy.setMaker(catToy.getMaker());
        updatedCatToy.setPrice(catToy.getPrice());
        updatedCatToy.setImageURI(catToy.getImageURI());
    }

    public void delete(Long id) {
        if(!exist(id)) {
            String message = "id " + id + "를 가지는 CatToy가 없습니다.";
            throw new CatToyNotFoundException(message);
        }

        this.catToys.remove(id);
    }

    private Long generateId() {
        return nextId++;
    }

    private boolean exist(Long id) {
        return this.catToys.containsKey(id);
    }
}
