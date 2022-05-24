package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    void delete(Product product);

    Product save(Product product);
}

