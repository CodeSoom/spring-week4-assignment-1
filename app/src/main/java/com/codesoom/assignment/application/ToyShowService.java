package com.codesoom.assignment.application;

import com.codesoom.assignment.application.exceptions.ProductNotFoundException;
import com.codesoom.assignment.application.interfaces.ProductShowService;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.interfaces.ToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyShowService implements ProductShowService {
    private final ToyRepository repository;

    public ToyShowService(ToyRepository repository) {
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
