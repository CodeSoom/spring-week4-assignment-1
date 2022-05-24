package com.codesoom.assignment.application;

import com.codesoom.assignment.interfaces.CrudService;
import com.codesoom.assignment.interfaces.Product;
import com.codesoom.assignment.interfaces.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToyCrudService implements CrudService {
    private final ProductRepository repository;
    public ToyCrudService(ProductRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Product> showAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> showById(Long id) {
        return repository.findById(id);
    }
}
