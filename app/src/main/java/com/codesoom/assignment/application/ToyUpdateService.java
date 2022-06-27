package com.codesoom.assignment.application;

import com.codesoom.assignment.application.exceptions.ProductNotFoundException;
import com.codesoom.assignment.application.interfaces.ProductUpdateService;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.interfaces.ToyRepository;
import org.springframework.stereotype.Service;

@Service
public class ToyUpdateService implements ProductUpdateService {
    private final ToyRepository repository;

    public ToyUpdateService(ToyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Toy update(Long id, Toy toy) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException();
        }

        Toy toyUpdating = new Toy(id, toy.name(), toy.producer(), toy.price());
        return repository.save(toyUpdating);
    }
}
