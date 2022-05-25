package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.interfaces.CrudService;
import com.codesoom.assignment.interfaces.ToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToyCrudService implements CrudService {
    private final ToyRepository repository;

    public ToyCrudService(ToyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Toy> showAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Toy> showById(Long id) {
        return repository.findById(id);
    }
}
