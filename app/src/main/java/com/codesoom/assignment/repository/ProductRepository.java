package com.codesoom.assignment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.codesoom.assignment.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    Product save(Product product);

    @Override
    List<Product> findAll();

    @Override
    Optional<Product> findById(Long id);

    @Override
    void delete(Product product);
}
