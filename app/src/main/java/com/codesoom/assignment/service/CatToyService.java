package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;

import java.util.List;

public class CatToyService {
    private final CatToyRepository repository;

    public CatToyService(CatToyRepository repository) {
        this.repository = repository;
    }

    public List<CatToy> getList() {
        return repository.getList();
    }
}
