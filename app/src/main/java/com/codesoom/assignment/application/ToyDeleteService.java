package com.codesoom.assignment.application;

import com.codesoom.assignment.application.interfaces.ProductDeleteService;
import com.codesoom.assignment.domain.interfaces.ToyRepository;
import org.springframework.stereotype.Service;

@Service
public class ToyDeleteService implements ProductDeleteService {
    private final ToyRepository repository;

    public ToyDeleteService(ToyRepository repository) {
        this.repository = repository;
    }


    @Override
    public void deleteBy(Long id) {
        repository.deleteById(id);
    }
}
