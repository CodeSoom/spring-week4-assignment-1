package com.codesoom.assignment.application;

import com.codesoom.assignment.application.interfaces.ProductCreateService;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.interfaces.ToyRepository;
import org.springframework.stereotype.Service;

@Service
public class ToyCreateService implements ProductCreateService {
    private final ToyRepository repository;

    public ToyCreateService(ToyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Toy create(Toy toy) {
        return repository.save(toy);
    }
}
