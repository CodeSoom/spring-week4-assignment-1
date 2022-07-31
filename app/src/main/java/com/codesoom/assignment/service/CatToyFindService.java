package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatToyFindService {
    private final CatToyRepository repository;

    public CatToyFindService(CatToyRepository repository) {
        this.repository = repository;
    }

    public List<CatToy> findAll() {
        return repository.findAll();
    }

    public Optional<CatToy> findById(Long catToyId) {
        return repository.findById(catToyId);
    }
}
