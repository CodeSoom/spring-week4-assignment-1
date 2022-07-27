package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatToyService {
    private final CatToyRepository repository;

    public CatToyService(CatToyRepository repository) {
        this.repository = repository;
    }

    public List<CatToy> getList() {
        return repository.findAll();
    }

    public CatToy save(CatToy newToy) {
        return repository.save(newToy);
    }
}
