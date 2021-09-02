package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;

import java.util.List;

public class CatToyService {
    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    public List<CatToy> getCatToys() {
        return catToyRepository.findAll();
    }

    public CatToy getCatToy(Long id) {
        return catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException(id));
    }

    public CatToy createCatToy(CatToy source) {
        CatToy catToy = new CatToy();
        catToy.setTitle(catToy.getTitle());

        return catToyRepository.save(catToy);
    }

    public CatToy updateCatToy(Long id, CatToy source) {
        CatToy catToy = catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException(id));
    }
}
