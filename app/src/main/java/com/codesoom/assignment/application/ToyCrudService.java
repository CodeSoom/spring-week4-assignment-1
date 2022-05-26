package com.codesoom.assignment.application;

import com.codesoom.assignment.application.exceptions.ProductNotFoundException;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.interfaces.ProductCrudService;
import com.codesoom.assignment.interfaces.ToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyCrudService implements ProductCrudService {
    private final ToyRepository repository;

    public ToyCrudService(ToyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Toy> showAll() {
        return repository.findAll();
    }

    @Override
    public Toy showById(Long id) {
        return repository.findById(id).stream()
            .findFirst()
            .orElseThrow(ProductNotFoundException::new);
    }
}
