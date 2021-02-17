package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }
}
