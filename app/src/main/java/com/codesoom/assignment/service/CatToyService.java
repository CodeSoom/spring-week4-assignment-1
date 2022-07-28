package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatToyService {
    private final CatToyRepository repository;

    public CatToyService(CatToyRepository repository) {
        this.repository = repository;
    }

    public List<CatToy> getList() {
        return repository.findAll();
    }

    public Optional<CatToy> findById(Long catToyId) {
        return repository.findById(catToyId);
    }

    public CatToy save(CatToy newToy) {
        return repository.save(newToy);
    }

    public CatToy update(Long toyId, CatToy newToy) {
        return repository.update(toyId, newToy);
    }
}
