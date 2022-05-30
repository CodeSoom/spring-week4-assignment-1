package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.error.NotFoundException;
import com.codesoom.assignment.interfaces.ProductRepository;
import com.codesoom.assignment.interfaces.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyService implements ProductService {
    private final ProductRepository repository;

    public ToyService(ProductRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product update(Long id, @NotNull Product newProduct) {
        repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        return repository.update(id, newProduct);
    }

    @Override
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        repository.delete(id);
    }
}
