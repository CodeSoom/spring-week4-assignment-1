package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository{

    List<Product> findAll();

    Optional<Product> find(Long id);

    Product save(Product product);

    void deleteById(Long id);
}
