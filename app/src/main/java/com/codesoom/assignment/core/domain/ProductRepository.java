package com.codesoom.assignment.core.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Product save(Product product);

    Optional<Product> findById(Long id);
}
