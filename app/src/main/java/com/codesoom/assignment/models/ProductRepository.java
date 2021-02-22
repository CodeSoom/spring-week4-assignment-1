package com.codesoom.assignment.models;

import java.util.List;
import java.util.Optional;


public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);
}
