package com.codesoom.assignment.domain;

import java.util.ArrayList;
import java.util.List;

public class CatToyRegistory {
    private List<CatToy> catToys;
    private Long nextId;

    public CatToyRegistory() {
        this.catToys = new ArrayList<CatToy>();
        this.nextId = 1L;
    }

    public void create(CatToy catToy) {
        catToy.setId(generateId());
        this.catToys.add(catToy);
    }

    public List<CatToy> getAll() {
        return this.catToys;
    }

    private Long generateId() {
        return nextId + 1L;
    }
}
