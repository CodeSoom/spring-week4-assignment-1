package com.codesoom.assignment.domain;

import java.util.Collection;
import java.util.Optional;

public interface ProductRepository {

    Collection<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product toy);
    void delete(Product toy);
}
