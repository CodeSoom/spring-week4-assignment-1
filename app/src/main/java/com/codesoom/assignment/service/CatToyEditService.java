package com.codesoom.assignment.service;

import com.codesoom.assignment.ToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Service;

@Service
public class CatToyEditService {
    private final CatToyRepository repository;

    public CatToyEditService(CatToyRepository repository) {
        this.repository = repository;
    }

    public CatToy save(CatToy newToy) {
        return repository.save(newToy);
    }

    public CatToy update(Long toyId, CatToy newToy) {
        return repository.update(toyId, newToy);
    }

    public void deleteById(Long toyId) {
        if (repository.findById(toyId).isEmpty()) {
            throw new ToyNotFoundException(toyId);
        }

        repository.deleteById(toyId);
    }
}
