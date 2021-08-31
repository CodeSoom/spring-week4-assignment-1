package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.CatToyNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CatToyRegistory {
    private List<CatToy> catToys;
    private Long nextId;

    public CatToyRegistory() {
        this.catToys = new ArrayList<CatToy>();
        this.nextId = 0L;
    }

    public void create(CatToy catToy) {
        catToy.setId(generateId());
        this.catToys.add(catToy);
    }

    public List<CatToy> getAll() {
        return this.catToys;
    }

    public CatToy get(Long id) {
        return this.catToys.stream()
                .filter(catToy -> catToy.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new CatToyNotFoundException("id " + id + "를 가지는 CatToy가 없습니다."));
    }

    public void update(Long id, CatToy catToy) {

        CatToy updatedCatToy = get(id);

        updatedCatToy.setName(catToy.getName());
        updatedCatToy.setMaker(catToy.getMaker());
        updatedCatToy.setPrice(catToy.getPrice());
        updatedCatToy.setImageURI(catToy.getImageURI());
    }

    private Long generateId() {
        return nextId + 1L;
    }
}
