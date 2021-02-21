package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product);
    void remove(Product product);

    Optional<Product> find(Long id);

    List<Product> findAll();

    ProductId nextId();
}